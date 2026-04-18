package domain.booking.observer;

/**
 * Subject role for the booking notification observer pattern. Allows
 * observers to subscribe to booking events (state changes, reschedules, etc.).
 */
public interface BookingSubject {

    /**
     * Registers a new observer.
     *
     * @param observer observer interested in updates
     */
    void attach(NotificationObserver observer);

    /**
     * Removes a previously registered observer.
     *
     * @param observer observer to remove
     */
    void detach(NotificationObserver observer);

    /**
     * Notifies all registered observers of a change.
     */
    void notifyObservers();
}
