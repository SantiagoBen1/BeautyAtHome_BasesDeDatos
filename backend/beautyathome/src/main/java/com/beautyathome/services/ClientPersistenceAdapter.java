package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.client.Client;
import com.beautyathome.entities.ClientEntity;
import com.beautyathome.repositories.ClientRepository;
import com.beautyathome.repositories.JpaClientRepository;

@Component
public class ClientPersistenceAdapter implements ClientRepository {

    private final JpaClientRepository jpaRepository;

    public ClientPersistenceAdapter(JpaClientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = toEntity(client);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Client> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        try {
            jpaRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {}
    }

    private ClientEntity toEntity(Client domain) {
        ClientEntity entity = new ClientEntity();
        if (domain.getId() != null && !domain.getId().isEmpty()) {
            try {
                entity.setId(Integer.parseInt(domain.getId()));
            } catch (NumberFormatException e) {}
        }
        
        String[] parts = domain.getName() != null ? domain.getName().split(" ", 2) : new String[]{"Unknown"};
        entity.setFirstName(parts[0]);
        entity.setLastName(parts.length > 1 ? parts[1] : "");
        entity.setEmail(domain.getEmail());
        entity.setPhone("0000000000"); // Dummy for SQL
        entity.setPasswordHash("temporary_hash_123"); // Dummy for SQL
        
        return entity;
    }

    private Client toDomain(ClientEntity entity) {
        return new Client(
            String.valueOf(entity.getId()),
            entity.getFirstName() + " " + entity.getLastName(),
            entity.getEmail()
        );
    }
}