package com.beautyathome.infrastructure.adapter.in.ui.controller;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.beautyathome.application.facade.BeautyAtHomeFacade;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.history.ServiceHistory;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.professional.Brand;
import com.beautyathome.domain.professional.HairStylist;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.port.out.ReviewRepositoryPort;
import com.beautyathome.domain.review.rating.RatingValueObject;
import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.ServiceLeaf;
import com.beautyathome.domain.service.image.ImageReference;
import com.beautyathome.domain.service.image.Photo;
import com.beautyathome.infrastructure.adapter.in.ui.viewmodel.ReviewShowcase;

@ExtendWith(MockitoExtension.class)
class ReviewViewControllerTest {

    @Mock
    private BeautyAtHomeFacade facade;

    @Mock
    private ReviewRepositoryPort reviewRepositoryPort;

    private ReviewViewController controller;

    private Professional professional;
    private Client client;
    private ServiceComponent service;

    @BeforeEach
    public void setUp() {
        controller = new ReviewViewController(facade, reviewRepositoryPort);
        professional = new HairStylist(
            "pro-1",
            "Eva",
            "https://img/pro.jpg",
            "editoriales",
            List.of(),
            new Brand("Glow", "https://img/logo.png"),
            List.of()
        );
        client = new Client("client-1", "Lina", "lina@mail.com");
        service = new ServiceLeaf(
            "Editorial",
            "look premium",
            280_000,
            90,
            List.of(new ImageReference("https://img/editorial.jpg"))
        );
    }

    @Test
    void listReviewsBuildsShowcaseCardsWithMedia() {
        Booking booking = new Booking("booking-1", client.getId(), professional.getId(), "svc-1", LocalDateTime.now());
        Review review = new Review(
            "review-1",
            booking,
            client,
            professional,
            new RatingValueObject(5),
            "Impecable",
            LocalDateTime.now()
        );
        ServiceHistory history = new ServiceHistory(booking, client, professional, service, LocalDateTime.now());
        history.addPhoto(new Photo(booking.getId(), professional.getId(), "https://img/cover.jpg", true));

        when(reviewRepositoryPort.findAll()).thenReturn(List.of(review));
        when(facade.viewProfessionalHistory(professional.getId())).thenReturn(List.of(history));

        Model model = new ConcurrentModel();
        String view = controller.listReviews(model);

        assertEquals("reviews", view);
        
        List<?> reviewCardsObj = (List<?>) model.getAttribute("reviewCards");
        assertNotNull(reviewCardsObj);
        
        @SuppressWarnings("unchecked")
        List<ReviewShowcase> cards = (List<ReviewShowcase>) reviewCardsObj;
        
        assertEquals(1, cards.size());
        assertEquals("https://img/cover.jpg", cards.get(0).getCoverPhoto());
        assertEquals(review, cards.get(0).getReview());
        
        List<?> reviewsObj = (List<?>) model.getAttribute("reviews");
        assertNotNull(reviewsObj);
        
        @SuppressWarnings("unchecked")
        List<Review> rawReviews = (List<Review>) reviewsObj;
        assertEquals(review, rawReviews.get(0));
        
        verify(facade).viewProfessionalHistory(professional.getId());
    }
}
