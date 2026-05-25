package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beautyathome.domain.client.Client;
import com.beautyathome.entities.ClientEntity;
import com.beautyathome.repositories.ClientRepository;
import com.beautyathome.repositories.JpaClientRepository;

@Component
@Transactional(readOnly = true)
public class ClientPersistenceAdapter implements ClientRepository {

    private final JpaClientRepository jpaRepository;

    public ClientPersistenceAdapter(JpaClientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Client save(Client client) {
        ClientEntity entity = null;
        if (client.getId() != null && !client.getId().isEmpty()) {
            try {
                int id = Integer.parseInt(client.getId());
                entity = jpaRepository.findById(id).orElse(null);
            } catch (NumberFormatException e) {}
        }
        
        if (entity == null) {
            entity = new ClientEntity();
            entity.setPhone("0000000000");
            entity.setPasswordHash("temporary_hash_123");
        }

        String[] parts = client.getName() != null ? client.getName().split(" ", 2) : new String[]{"Unknown"};
        entity.setFirstName(parts[0]);
        entity.setLastName(parts.length > 1 ? parts[1] : "");
        entity.setEmail(client.getEmail());

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

    // toEntity removed since logic is handled in save()

    private Client toDomain(ClientEntity entity) {
        return new Client(
            String.valueOf(entity.getId()),
            entity.getFirstName() + " " + entity.getLastName(),
            entity.getEmail()
        );
    }
}