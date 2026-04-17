package infrastructure.persistence.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import domain.professional.Professional;
import infrastructure.persistence.dao.ProfessionalDAO;

/**
 * Implementación en memoria del DAO de profesionales.
 */
public class InMemoryProfessionalDAO implements ProfessionalDAO {

    private final Map<String, Professional> store = new ConcurrentHashMap<>();

    /** {@inheritDoc} */
    @Override
    public Professional save(Professional entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Professional cannot be null");
        }
        if (entity.getId() == null || entity.getId().isBlank()) {
            throw new IllegalArgumentException("Professional id is required");
        }
        store.put(entity.getId(), entity);
        return entity;
    }

    /** {@inheritDoc} */
    @Override
    public Professional findById(String id) {
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
    public List<Professional> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }
}
