package com.beautyathome.infrastructure.proxy;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;
import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.port.out.ReviewRepositoryPort;
import com.beautyathome.domain.review.rating.RatingValueObject;

@Service
public class ReviewService {
    private final ReviewRepositoryPort reviewRepository;
    private final BookingRepositoryPort bookingRepository;
    private final ClientRepositoryPort clientRepository;
    private final ProfessionalRepositoryPort professionalRepository;

    public ReviewService(ReviewRepositoryPort reviewRepository,
                         BookingRepositoryPort bookingRepository,
                         ClientRepositoryPort clientRepository,
                         ProfessionalRepositoryPort professionalRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
    }
    
    public void submitReview(Review review) {
        reviewRepository.save(review);
    }

    public Review createReview(String bookingId, int rating, String text) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new IllegalArgumentException("Booking not found");
        
        Client client = clientRepository.findById(booking.getClientId()).orElse(null);
        if (client == null) throw new IllegalArgumentException("Client not found");
        
        Professional professional = professionalRepository.findById(booking.getProfessionalId()).orElse(null);
        if (professional == null) throw new IllegalArgumentException("Professional not found");
        
        RatingValueObject ratingVo = new RatingValueObject(rating);
        Review review = new Review(null, booking, client, professional, ratingVo, text, LocalDateTime.now());
        submitReview(review);
        return review;
    }
}