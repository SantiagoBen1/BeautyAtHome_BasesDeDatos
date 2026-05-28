package com.beautyathome.controllers;

import com.beautyathome.entities.ClientEntity;
import com.beautyathome.repositories.JpaClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final JpaClientRepository clientRepository;

    public ClientController(JpaClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // 1. GET (Lista) - HTTP 200
    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAllClients() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    // 2. GET (Por ID) - HTTP 200 o HTTP 404
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable Integer id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST (Crear) - HTTP 201 Created
    @PostMapping
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity newClient) {
        ClientEntity savedClient = clientRepository.save(newClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // 4. PUT (Actualizar) - HTTP 200 o HTTP 404
    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> updateClient(@PathVariable Integer id,
            @RequestBody ClientEntity clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setFirstName(clientDetails.getFirstName());
            client.setLastName(clientDetails.getLastName());
            client.setPhone(clientDetails.getPhone());
            return ResponseEntity.ok(clientRepository.save(client));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE (Eliminar) - HTTP 204 No Content o HTTP 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}