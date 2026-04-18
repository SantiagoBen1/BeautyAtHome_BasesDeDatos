package ui.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.facade.BeautyAtHomeFacade;
import infrastructure.persistence.dao.ClientDAO;
import ui.viewmodel.ClientForm;

/**
 * MVC controller dedicated to client registration and listing.
 */
@Controller
@RequestMapping("/clients")
public class ClientViewController {

    private final BeautyAtHomeFacade facade;
    private final ClientDAO clientDAO;

    public ClientViewController(BeautyAtHomeFacade facade, ClientDAO clientDAO) {
        this.facade = facade;
        this.clientDAO = clientDAO;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientDAO.findAll());
        model.addAttribute("clientForm", new ClientForm());
        return "clients";
    }

    @PostMapping
    public String registerClient(@ModelAttribute("clientForm") ClientForm form,
                                 RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id", form.getId());
            data.put("name", form.getName());
            data.put("email", form.getEmail());
            facade.registerClient(data);
            redirectAttributes.addFlashAttribute("message", "Cliente registrado correctamente");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/clients";
    }
}
