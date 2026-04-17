package api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ServiceCreationRequest;
import application.facade.BeautyAtHomeFacade;
import domain.service.ServiceComponent;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final BeautyAtHomeFacade facade;

    public ServiceController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ServiceComponent createService(@RequestBody ServiceCreationRequest request) {
        return facade.createBasicService(
                request.getProfessionalId(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getDurationMinutes(),
                request.getImageUrls()
        );
    }
}
