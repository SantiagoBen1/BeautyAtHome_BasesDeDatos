package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.review.Review;
import com.beautyathome.repositories.ReviewRepository;
import com.beautyathome.repositories.JpaReviewRepository;
import com.beautyathome.entities.ReviewEntity;
import com.beautyathome.repositories.ReviewRepository;
import com.beautyathome.repositories.JpaReviewRepository;

@Component
public class ReviewPersistenceAdapter implements ReviewRepository {

    private final JpaReviewRepository jpaRepository;

    public ReviewPersistenceAdapter(JpaReviewRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = toEntity(review); 
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Review> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Review> findByProfessionalId(String professionalId) {
        // Necesitarás definir findByProfessionalId en ReviewRepository
        return List.of(); 
    }

    @Override
    public List<Review> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private ReviewEntity toEntity(Review domain) {
        ReviewEntity entity = new ReviewEntity();
        entity.setId(domain.getId());
        return entity;
    }

    private Review toDomain(ReviewEntity entity) {
        // Emplea tu ReviewBuilder aquí 
        return null; 
    }
}