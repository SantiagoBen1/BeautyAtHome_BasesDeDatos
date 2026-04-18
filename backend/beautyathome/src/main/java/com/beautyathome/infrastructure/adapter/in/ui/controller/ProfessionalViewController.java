package ui.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.facade.BeautyAtHomeFacade;
import domain.professional.Professional;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;
import ui.viewmodel.ProfessionalForm;
import ui.viewmodel.ProfessionalShowcase;

/**
 * MVC controller that lists and registers professionals.
 */
@Controller
@RequestMapping("/professionals")
public class ProfessionalViewController {

    private final BeautyAtHomeFacade facade;
    private final ProfessionalDAO professionalDAO;
    private final ReviewDAO reviewDAO;

    public ProfessionalViewController(BeautyAtHomeFacade facade,
                                      ProfessionalDAO professionalDAO,
                                      ReviewDAO reviewDAO) {
        this.facade = facade;
        this.professionalDAO = professionalDAO;
        this.reviewDAO = reviewDAO;
    }

    @GetMapping
    public String listProfessionals(@RequestParam(required = false) String zone,
                                    @RequestParam(required = false) String category,
                                    Model model) {
        Iterable<Professional> data = (zone == null && category == null)
                ? professionalDAO.findAll()
                : facade.searchProfessionals(zone, category);
        List<Professional> professionals = StreamSupport
                .stream(data.spliterator(), false)
                .collect(Collectors.toList());
        Map<String, Double> ratingByProfessional = professionals.stream()
                .collect(Collectors.toMap(Professional::getId,
                        pro -> facade.getProfessionalAverageRating(pro.getId())));
        double networkAverageRating = ratingByProfessional.values().stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
        List<ProfessionalShowcase> showcases = professionals.stream()
            .map(professional -> new ProfessionalShowcase(
                professional,
                facade.listServices(professional.getId()),
                facade.viewProfessionalHistory(professional.getId()),
                reviewDAO.findByProfessionalId(professional.getId()),
                ratingByProfessional.getOrDefault(professional.getId(), 0.0)))
            .collect(Collectors.toList());
        model.addAttribute("professionals", professionals);
        model.addAttribute("ratingByProfessional", ratingByProfessional);
        model.addAttribute("networkAverageRating", networkAverageRating);
        model.addAttribute("showcases", showcases);
        model.addAttribute("sponsoredShowcases", showcases.stream()
            .filter(showcase -> showcase.getProfessional().getBrand() != null)
            .collect(Collectors.toList()));
        model.addAttribute("selectedZone", zone);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("professionalForm", new ProfessionalForm());
        return "professionals";
    }

    @PostMapping
    public String registerProfessional(@ModelAttribute("professionalForm") ProfessionalForm form,
                                       RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id", form.getId());
            data.put("type", form.getType());
            data.put("name", form.getName());
            data.put("photoUrl", form.getPhotoUrl());
            data.put("experienceSummary", form.getExperienceSummary());
            data.put("coverage", form.coverageAsList());
            data.put("services", Collections.emptyList());
            List<ProfessionalForm.CatalogEntry> catalogEntries = form.catalogEntries();
            if (form.getBrandName() != null && !form.getBrandName().isBlank()) {
                Map<String, Object> brand = new HashMap<>();
                brand.put("name", form.getBrandName());
                brand.put("logo", form.getBrandLogo());
                brand.put("products", form.brandProductsAsList());
                data.put("brand", brand);
            }
            Professional professional = facade.registerProfessional(data);
            int created = publishCatalogServices(professional.getId(), catalogEntries);
            String message = created > 0
                    ? String.format("Profesional registrada. Catálogo signature con %d servicios", created)
                    : "Profesional registrada correctamente";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/professionals";
    }

    private int publishCatalogServices(String professionalId, List<ProfessionalForm.CatalogEntry> catalogEntries) {
        if (catalogEntries == null || catalogEntries.isEmpty()) {
            return 0;
        }
        int created = 0;
        for (ProfessionalForm.CatalogEntry entry : catalogEntries) {
            facade.createBasicService(
                    professionalId,
                    entry.name(),
                    entry.description(),
                    entry.price(),
                    entry.durationMinutes(),
                    entry.imageUrls() == null ? List.of() : entry.imageUrls()
            );
            created++;
        }
        return created;
    }
}
