package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import com.beautyathome.domain.review.Review;
import infrastructure.persistence.dao.ReviewDAO;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.ReviewEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaReviewRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresReviewDAO implements ReviewDAO {

    private final JpaReviewRepository repository;

    public PostgresReviewDAO(JpaReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review save(Review entity) {
        if (entity == null) throw new IllegalArgumentException("Review cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        ReviewEntity reviewEntity = new ReviewEntity(id, 
                entity.getBooking().getId(), 
                entity.getRating().getValue(), // Asumiendo que se usa RatingValueObject
                entity.getComment());
                
        ReviewEntity saved = repository.save(reviewEntity);
        
        // TODO: Reconstruir usando ReviewBuilder
        return null;
    }

    @Override
    public Review findById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<Review> findAll() {
        return List.of();
    }
}