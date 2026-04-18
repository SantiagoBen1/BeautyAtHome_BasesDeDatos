package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.ClientEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ClientPersistenceAdapter implements ClientRepositoryPort {

    private final JpaClientRepository repository;

    public ClientPersistenceAdapter(JpaClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client entity) {
        if (entity == null) throw new IllegalArgumentException("Client cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        ClientEntity clientEntity = new ClientEntity(id, entity.getName(), entity.getEmail());
        ClientEntity saved = repository.save(clientEntity);
        
        return new Client(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public Client findById(String id) {
        return repository.findById(id)
                .map(e -> new Client(e.getId(), e.getName(), e.getEmail()))
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll().stream()
                .map(e -> new Client(e.getId(), e.getName(), e.getEmail()))
                .collect(Collectors.toList());
    }
}