package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.ClientEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaClientRepository;

@Component
public class ClientPersistenceAdapter implements ClientRepositoryPort {

    private final JpaClientRepository jpaRepository;

    public ClientPersistenceAdapter(JpaClientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Client save(Client client) {
        // Asegúrate de tener los mapeos correctos
        ClientEntity entity = new ClientEntity(client.getId(), client.getName(), client.getEmail()); 
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Client> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private Client toDomain(ClientEntity entity) {
        return new Client(entity.getId(), entity.getName(), entity.getEmail());
    }
}