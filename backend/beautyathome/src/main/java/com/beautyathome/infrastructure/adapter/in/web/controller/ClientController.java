package api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ClientRegistrationRequest;
import application.facade.BeautyAtHomeFacade;
import domain.client.Client;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final BeautyAtHomeFacade facade;

    public ClientController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public Client registerClient(@RequestBody ClientRegistrationRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", request.getId());
        data.put("name", request.getName());
        data.put("email", request.getEmail());
        return facade.registerClient(data);
    }
}
