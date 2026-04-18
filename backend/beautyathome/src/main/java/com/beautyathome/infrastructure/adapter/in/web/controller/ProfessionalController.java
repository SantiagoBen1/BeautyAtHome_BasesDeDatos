package api.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.dto.BrandRequest;
import api.dto.ProfessionalRegistrationRequest;
import application.facade.BeautyAtHomeFacade;
import domain.booking.history.ServiceHistory;
import domain.professional.Professional;
import domain.service.ServiceComponent;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {

    private final BeautyAtHomeFacade facade;

    public ProfessionalController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public Professional registerProfessional(@RequestBody ProfessionalRegistrationRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", request.getId());
        data.put("type", request.getType());
        data.put("name", request.getName());
        data.put("photoUrl", request.getPhotoUrl());
        data.put("experienceSummary", request.getExperienceSummary());
        data.put("coverage", Optional.ofNullable(request.getCoverageAreas()).orElse(Collections.emptyList()));
        data.put("services", Collections.emptyList());
        BrandRequest brand = request.getBrand();
        if (brand != null) {
            Map<String, String> brandData = new HashMap<>();
            brandData.put("name", brand.getName());
            brandData.put("logo", brand.getLogoUrl());
            data.put("brand", brandData);
        }
        return facade.registerProfessional(data);
    }

    @GetMapping
    public List<Professional> searchProfessionals(@RequestParam(required = false) String zone,
                                                  @RequestParam(required = false) String category) {
        return facade.searchProfessionals(zone, category);
    }

    @GetMapping("/{professionalId}/services")
    public List<ServiceComponent> listServices(@PathVariable String professionalId) {
        return facade.listServices(professionalId);
    }

    @GetMapping("/{professionalId}/history")
    public List<ServiceHistory> viewHistory(@PathVariable String professionalId) {
        return facade.viewProfessionalHistory(professionalId);
    }
}
