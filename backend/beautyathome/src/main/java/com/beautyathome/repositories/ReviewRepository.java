package com.beautyathome.repositories;

import java.util.List;
import java.util.Optional;
import com.beautyathome.domain.review.Review;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(String id);
    List<Review> findByProfessionalId(String professionalId);
    List<Review> findAll();
    void delete(String id);
}
