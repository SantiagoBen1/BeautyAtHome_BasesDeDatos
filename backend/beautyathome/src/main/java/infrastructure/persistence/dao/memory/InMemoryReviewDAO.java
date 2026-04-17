package infrastructure.persistence.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import domain.review.Review;
import infrastructure.persistence.dao.ReviewDAO;

/**
 * DAO de reseñas basado en estructuras concurrentes en memoria.
 */
public class InMemoryReviewDAO implements ReviewDAO {

    private final Map<String, Review> store = new ConcurrentHashMap<>();

    /** {@inheritDoc} */
    @Override
    public Review save(Review entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        store.put(entity.getId(), entity);
        return entity;
    }

    /** {@inheritDoc} */
    @Override
    public Review findById(String id) {
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
    public List<Review> findByProfessionalId(String professionalId) {
        if (professionalId == null) {
            return Collections.emptyList();
        }
        return store.values().stream()
                .filter(review -> review.getProfessional() != null)
                .filter(review -> professionalId.equals(review.getProfessional().getId()))
                .collect(Collectors.toList());
    }

    /** {@inherit} */
    @Override
    public List<Review> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }
}
