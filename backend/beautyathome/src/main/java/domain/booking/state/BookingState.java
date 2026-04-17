package domain.booking.state;

import domain.booking.Booking;

/**
 * State pattern contract for booking lifecycle transitions.
 */
public interface BookingState {

    /**
     * Handles the transition triggered by a confirmation request.
     *
     * @param booking booking being mutated
     */
    void confirm(Booking booking);

    /**
     * Handles the transition triggered when the service starts.
     *
     * @param booking booking being mutated
     */
    void start(Booking booking);

    /**
     * Handles completion of the service.
     *
     * @param booking booking being mutated
     */
    void complete(Booking booking);

    /**
     * Handles cancellation requests.
     *
     * @param booking booking being mutated
     */
    void cancel(Booking booking);
}
