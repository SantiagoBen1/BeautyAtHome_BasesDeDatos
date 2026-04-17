package ui.controller;

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

import application.facade.BeautyAtHomeFacade;
import domain.booking.Booking;
import domain.booking.history.ServiceHistory;
import domain.client.Client;
import domain.professional.Brand;
import domain.professional.HairStylist;
import domain.professional.Professional;
import domain.review.Review;
import domain.review.rating.RatingValueObject;
import domain.service.ServiceComponent;
import domain.service.ServiceLeaf;
import domain.service.image.ImageReference;
import domain.service.image.Photo;
import infrastructure.persistence.dao.ReviewDAO;
import ui.viewmodel.ReviewShowcase;

@ExtendWith(MockitoExtension.class)
class ReviewViewControllerTest {

    @Mock
    private BeautyAtHomeFacade facade;

    @Mock
    private ReviewDAO reviewDAO;

    private ReviewViewController controller;

    private Professional professional;
    private Client client;
    private ServiceComponent service;

    @BeforeEach
    void setUp() {
        controller = new ReviewViewController(facade, reviewDAO);
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

        when(reviewDAO.findAll()).thenReturn(List.of(review));
        when(facade.viewProfessionalHistory(professional.getId())).thenReturn(List.of(history));

        Model model = new ConcurrentModel();
        String view = controller.listReviews(model);

        assertEquals("reviews", view);
        @SuppressWarnings("unchecked")
        List<ReviewShowcase> cards = (List<ReviewShowcase>) model.getAttribute("reviewCards");
        assertNotNull(cards);
        assertEquals(1, cards.size());
        assertEquals("https://img/cover.jpg", cards.get(0).getCoverPhoto());
        assertEquals(review, cards.get(0).getReview());
        assertEquals(review, ((List<Review>) model.getAttribute("reviews")).get(0));
        verify(facade).viewProfessionalHistory(professional.getId());
    }
}
