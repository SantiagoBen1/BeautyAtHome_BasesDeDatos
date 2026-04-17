package infrastructure.persistence.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import domain.client.Client;
import infrastructure.persistence.dao.ClientDAO;

/**
 * DAO en memoria para clientes, útil en pruebas unitarias.
 */
public class InMemoryClientDAO implements ClientDAO {

    private final Map<String, Client> store = new ConcurrentHashMap<>();

    /** {@inheritDoc} */
    @Override
    public Client save(Client entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        Client normalized = entity.getId() == null || entity.getId().isBlank()
                ? new Client(UUID.randomUUID().toString(), entity.getName(), entity.getEmail())
                : entity;
        store.put(normalized.getId(), normalized);
        return normalized;
    }

    /** {@inheritDoc} */
    @Override
    public Client findById(String id) {
        if (id == null) {
            return null;
        }
        return store.get(id);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(String id) {
        if (id != null) {
            store.remove(id);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Client> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }
}
