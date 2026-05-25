package com.beautyathome.repositories;

import java.util.List;
import java.util.Optional;
import com.beautyathome.domain.client.Client;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(String id);
    List<Client> findAll();
    void delete(String id);
}
