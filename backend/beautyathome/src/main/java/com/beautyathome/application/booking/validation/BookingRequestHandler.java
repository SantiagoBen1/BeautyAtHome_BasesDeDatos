package application.booking.validation;

import application.booking.BookingRequest;

/**
 * Abstract link in the chain of responsibility that validates a booking
 * request before it reaches the agenda.
 */
public abstract class BookingRequestHandler {

    protected BookingRequestHandler next;

    /**
     * Sets the next handler in the chain.
     *
     * @param next handler to invoke when current validation passes
     * @return the same handler to ease builder-style wiring
     */
    public BookingRequestHandler setNext(BookingRequestHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Executes the validation logic and delegates to the next handler when
     * successful.
     *
     * @param request booking request under validation
     * @return {@code true} when the request passes the entire chain
     */
    public boolean handle(BookingRequest request) {
        if (!doHandle(request)) {
            return false;
        }
        if (next == null) {
            return true;
        }
        return next.handle(request);
    }

    /**
     * Template method hook that concrete handlers must implement.
     *
     * @param request booking request under validation
     * @return {@code true} when the handler accepts the request
     */
    protected abstract boolean doHandle(BookingRequest request);
}
