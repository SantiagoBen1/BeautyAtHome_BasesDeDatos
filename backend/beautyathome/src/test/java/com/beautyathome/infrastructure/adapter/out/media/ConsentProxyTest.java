package com.beautyathome.infrastructure.adapter.out.media;

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

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.service.image.Photo;

class ConsentProxyTest {

    private static final String BOOKING_ID = "booking-1";
    private static final String PROFESSIONAL_ID = "pro-1";

    private ConsentProxy consentProxy;
    private FakeBookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        bookingRepository = new FakeBookingRepository();
        bookingRepository.save(new Booking(BOOKING_ID, "client-1", PROFESSIONAL_ID, "service-1", LocalDateTime.now()));
        PhotoGallery gallery = new PhotoGallery(bookingRepository);
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
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
            () -> consentProxy.addPhoto("missing", "https://img/fail.jpg", true));
        assertTrue(thrown.getMessage().contains("Booking not found"), "Debe rechazar booking faltante");
    }

    private static class FakeBookingRepository implements BookingRepositoryPort {

        private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

        @Override
        public Booking save(Booking entity) {
            bookings.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public java.util.Optional<Booking> findById(String id) {
            return java.util.Optional.ofNullable(bookings.get(id));
        }

        @Override
        public List<Booking> findByClientId(String clientId) {
            return bookings.values().stream()
                .filter(booking -> clientId.equals(booking.getClientId()))
                .toList();
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
