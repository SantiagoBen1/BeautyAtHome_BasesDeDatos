package com.beautyathome.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.rating.RatingValueObject;
import com.beautyathome.entities.ReviewEntity;
import com.beautyathome.repositories.ReviewRepository;
import com.beautyathome.repositories.JpaReviewRepository;

@Component
@Transactional(readOnly = true)
public class ReviewPersistenceAdapter implements ReviewRepository {

    private final JpaReviewRepository jpaRepository;

    public ReviewPersistenceAdapter(JpaReviewRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Review save(Review review) {
        ReviewEntity entity = new ReviewEntity();
        
        if (review.getBooking() != null && review.getBooking().getId() != null) {
            try {
                com.beautyathome.entities.BookingEntity booking = new com.beautyathome.entities.BookingEntity();
                booking.setId(Integer.parseInt(review.getBooking().getId()));
                entity.setBooking(booking);
            } catch (NumberFormatException e) {}
        }
        
        if (review.getRating() != null) {
            entity.setRating((double) review.getRating().getValue());
        } else {
            entity.setRating(5.0);
        }
        
        entity.setComment(review.getText());
        
        if (review.getCreatedAt() != null) {
            entity.setCreatedDate(review.getCreatedAt().toLocalDate());
        }
        
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Review> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Review> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByProfessionalId(String professionalId) {
        try {
            return jpaRepository.findByBooking_Professional_Id(Integer.parseInt(professionalId)).stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new java.util.ArrayList<>();
        }
    }

    @Override
    public void delete(String id) {
        try {
            jpaRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {}
    }

    private Review toDomain(ReviewEntity entity) {
        com.beautyathome.domain.client.Client domainClient = null;
        if (entity.getBooking() != null && entity.getBooking().getClient() != null) {
            domainClient = new com.beautyathome.domain.client.Client(
                String.valueOf(entity.getBooking().getClient().getId()),
                entity.getBooking().getClient().getFirstName() + " " + entity.getBooking().getClient().getLastName(),
                entity.getBooking().getClient().getEmail()
            );
        }

        return new Review(
            String.valueOf(entity.getId()),
            null, // Booking domain is complex to instantiate here
            domainClient, // Client
            null, // Professional
            new RatingValueObject(entity.getRating().intValue()),
            entity.getComment(),
            entity.getCreatedDate() != null ? entity.getCreatedDate().atStartOfDay() : LocalDateTime.now()
        );
    }
}