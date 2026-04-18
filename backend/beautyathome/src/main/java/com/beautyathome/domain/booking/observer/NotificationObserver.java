package domain.booking.observer;

import domain.booking.Booking;

/**
 * Observer contract that reacts to booking changes emitted by a
 * {@link BookingSubject}.
 */
public interface NotificationObserver {

    /**
     * Called when the observed booking changes and a notification must be
     * delivered.
     *
     * @param booking updated booking instance
     */
    void update(Booking booking);
}
