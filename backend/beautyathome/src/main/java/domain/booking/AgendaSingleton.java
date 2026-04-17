package domain.booking;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import domain.professional.Professional;
import domain.service.ServiceComponent;

/**
 * Singleton that centralizes booking availability checks and lifecycle actions
 * (booking, cancellation, rescheduling) across the system. All appointment
 * operations must go through this instance to guarantee consistency.
 */
public class AgendaSingleton {

    private static AgendaSingleton instance;

    private final List<Booking> bookings = new ArrayList<>();

    private AgendaSingleton() {}

    /**
     * Retrieves the shared agenda instance, creating it lazily if needed.
     *
     * @return singleton agenda instance
     */
    public static synchronized AgendaSingleton getInstance() {
        if (instance == null) {
            instance = new AgendaSingleton();
        }
        return instance;
    }

    /**
     * Verifies whether the provided professional has availability for the
     * requested date/time slice.
     *
     * @param professionalId professional unique identifier
     * @param dateTime requested start date/time
     * @param durationMinutes appointment duration in minutes
     * @return {@code true} when no conflicting bookings exist
     */
    public synchronized boolean availability(String professionalId, LocalDateTime dateTime, int durationMinutes) {
        return bookings.stream().noneMatch(booking ->
                booking.getProfessionalId().equals(professionalId)
                        && overlaps(booking.getDateTime(), dateTime, durationMinutes));
    }

    /**
     * Convenience availability check receiving the professional object instead
     * of its identifier.
     *
     * @param professional professional performing the service
     * @param dateTime requested start date/time
     * @param durationMinutes appointment duration in minutes
     * @return {@code true} when the professional is free
     */
    public boolean availability(Professional professional, LocalDateTime dateTime, int durationMinutes) {
        return availability(professional.getId(), dateTime, durationMinutes);
    }

    /**
     * Persists a booking record in memory and returns it to the caller.
     *
     * @param bookingId booking identifier to store
     * @param clientId client requesting the service
     * @param professionalId professional performing the service
     * @param serviceId service identifier
     * @param dateTime scheduled datetime
     * @return newly stored {@link Booking}
     */
    public synchronized Booking book(String bookingId,
                                     String clientId,
                                     String professionalId,
                                     String serviceId,
                                     LocalDateTime dateTime) {
        Booking booking = new Booking(bookingId, clientId, professionalId, serviceId, dateTime);
        bookings.add(booking);
        return booking;
    }

    /**
     * Books an appointment creating the booking and service identifiers
     * automatically when missing.
     *
     * @param clientId client performing the request
     * @param professionalId professional assigned to the booking
     * @param service service instance tied to the appointment
     * @param dateTime scheduled datetime
     * @return stored {@link Booking}
     */
    public Booking book(String clientId,
                        String professionalId,
                        ServiceComponent service,
                        LocalDateTime dateTime) {
        String bookingId = UUID.randomUUID().toString();
        String serviceId = service == null ? "" : service.getName();
        return book(bookingId, clientId, professionalId, serviceId, dateTime);
    }

    /**
     * Cancels an existing booking and updates its observers.
     *
     * @param bookingId identifier to cancel
     * @return {@code true} if a booking was removed
     */
    public synchronized boolean cancel(String bookingId) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.getId().equals(bookingId)) {
                iterator.remove();
                booking.cancel();
                return true;
            }
        }
        return false;
    }

    /**
     * Updates a booking datetime and notifies observers about the change.
     *
     * @param bookingId booking identifier
     * @param newDate datetime replacement
     * @return {@code true} when a booking was updated
     */
    public synchronized boolean reschedule(String bookingId, LocalDateTime newDate) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                booking.setDateTime(newDate);
                booking.notifyObservers();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a defensive copy of all stored bookings for inspection purposes.
     *
     * @return mutable copy of bookings list
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    /**
     * Determines whether two time intervals overlap for the provided duration.
     *
     * @param existing start datetime of an existing booking
     * @param requested requested start datetime
     * @param durationMinutes appointment duration used by both bookings
     * @return {@code true} when time slices overlap
     */
    private boolean overlaps(LocalDateTime existing, LocalDateTime requested, int durationMinutes) {
        if (existing == null || requested == null) {
            return false;
        }
        LocalDateTime endExisting = existing.plusMinutes(durationMinutes);
        LocalDateTime endRequested = requested.plusMinutes(durationMinutes);
        return !requested.isAfter(endExisting) && !existing.isAfter(endRequested);
    }
}
