package com.beautyathome.services.proxy;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.domain.client.Client;
import com.beautyathome.repositories.ClientRepository;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.repositories.ProfessionalRepository;
import com.beautyathome.domain.review.Review;
import com.beautyathome.repositories.ReviewRepository;
import com.beautyathome.domain.review.rating.RatingValueObject;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final ProfessionalRepository professionalRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         ClientRepository clientRepository,
                         ProfessionalRepository professionalRepository) {
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