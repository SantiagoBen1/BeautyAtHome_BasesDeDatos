package com.beautyathome.domain.review.port.out;

import java.util.List;
import java.util.Optional;

import com.beautyathome.domain.review.Review;

public interface ReviewRepositoryPort {
    Review save(Review review);
    Optional<Review> findById(String id);
    List<Review> findByProfessionalId(String professionalId); // Específico de Review
    List<Review> findAll();
    void delete(String id);
}