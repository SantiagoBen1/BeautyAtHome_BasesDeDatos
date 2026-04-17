package infrastructure.media;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.booking.Booking;
import domain.service.image.Photo;
import infrastructure.persistence.dao.BookingDAO;

class ConsentProxyTest {

    private static final String BOOKING_ID = "booking-1";
    private static final String PROFESSIONAL_ID = "pro-1";

    private ConsentProxy consentProxy;
    private FakeBookingDAO bookingDAO;

    @BeforeEach
    void setUp() {
        bookingDAO = new FakeBookingDAO();
        bookingDAO.save(new Booking(BOOKING_ID, "client-1", PROFESSIONAL_ID, "service-1", LocalDateTime.now()));
        PhotoGallery gallery = new PhotoGallery(new StorageAdapter(), bookingDAO);
        consentProxy = new ConsentProxy(gallery);
    }

    @Test
    void addPhotoMarksVisibilityAccordingToConsent() {
        consentProxy.addPhoto(BOOKING_ID, "https://img/private.jpg", true);
        consentProxy.addConsent(BOOKING_ID);
        consentProxy.addPhoto(BOOKING_ID, "https://img/public.jpg", true);

        List<Photo> photos = consentProxy.listByProfessional(PROFESSIONAL_ID);
        assertEquals(2, photos.size());
        assertFalse(photos.get(0).isPublic(), "Photo uploaded without consent must remain private");
        assertTrue(photos.get(1).isPublic(), "Photo uploaded after consent should be public");
    }

    @Test
    void addPhotoRejectsUnknownBooking() {
        assertThrows(IllegalArgumentException.class,
            () -> consentProxy.addPhoto("missing", "https://img/fail.jpg", true));
    }

    private static class FakeBookingDAO implements BookingDAO {

        private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

        @Override
        public Booking save(Booking entity) {
            bookings.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public Booking findById(String id) {
            return bookings.get(id);
        }

        @Override
        public void delete(String id) {
            bookings.remove(id);
        }

        @Override
        public List<Booking> findByProfessionalId(String professionalId) {
            return bookings.values().stream()
                .filter(booking -> professionalId.equals(booking.getProfessionalId()))
                .toList();
        }

        @Override
        public List<Booking> findAll() {
            return bookings.values().stream().toList();
        }
    }
}
