package ui.viewmodel;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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

class ProfessionalShowcaseTest {

    @Test
    void buildsReviewSpotlightsUsingBookingPhotosAndFallbacks() {
        Professional professional = new HairStylist(
            "pro-1",
            "Aura Vega",
            "https://img/pro.jpg",
            "10 años en fashion weeks",
            List.of(),
            new Brand("Sublime", "https://img/logo.png"),
            List.of()
        );

        ServiceComponent balayage = new ServiceLeaf(
            "Balayage",
            "Color premium",
            350_000,
            120,
            List.of(new ImageReference("https://img/balayage-1.jpg"))
        );

        Client client = new Client("client-1", "Laura", "laura@mail.com");
        Booking matchedBooking = new Booking("booking-1", client.getId(), professional.getId(), "svc-1", LocalDateTime.now());
        Booking fallbackBooking = new Booking("booking-2", client.getId(), professional.getId(), "svc-2", LocalDateTime.now());

        ServiceHistory historyWithMatch = new ServiceHistory(matchedBooking, client, professional, balayage, LocalDateTime.now());
        historyWithMatch.addPhoto(new Photo(matchedBooking.getId(), professional.getId(), "https://img/match.jpg", true));

        ServiceHistory historyFallback = new ServiceHistory(fallbackBooking, client, professional, balayage, LocalDateTime.now());
        historyFallback.addPhoto(new Photo(fallbackBooking.getId(), professional.getId(), "https://img/fallback.jpg", true));

        Review reviewWithPhotos = new Review(
            "review-1",
            matchedBooking,
            client,
            professional,
            new RatingValueObject(5),
            "Resultado espectacular",
            LocalDateTime.now()
        );

        Review reviewWithoutPhotos = new Review(
            "review-2",
            new Booking("booking-3", client.getId(), professional.getId(), "svc-3", LocalDateTime.now()),
            client,
            professional,
            new RatingValueObject(4),
            "Texturas impecables",
            LocalDateTime.now()
        );

        ProfessionalShowcase showcase = new ProfessionalShowcase(
            professional,
            List.of(balayage),
            List.of(historyWithMatch, historyFallback),
            List.of(reviewWithPhotos, reviewWithoutPhotos),
            4.8
        );

        List<ProfessionalShowcase.ServiceCard> serviceCards = showcase.getServiceCards();
        assertEquals(1, serviceCards.size());
        assertTrue(serviceCards.get(0).getPrice().contains("$") || serviceCards.get(0).getPrice().contains("COP"));
        assertEquals(List.of("https://img/balayage-1.jpg"), serviceCards.get(0).getGallery());

        List<ProfessionalShowcase.ReviewSpotlight> reviewSpotlights = showcase.getReviewSpotlights();
        assertEquals("https://img/match.jpg", reviewSpotlights.get(0).getCoverPhoto());
        assertEquals("https://img/match.jpg", reviewSpotlights.get(1).getCoverPhoto(),
            "When a booking has no photos, it should fallback to the first global gallery image");
        assertEquals(2, reviewSpotlights.size());
    }
}
