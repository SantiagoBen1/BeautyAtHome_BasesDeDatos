package domain.booking.command;

import domain.booking.AgendaSingleton;

/**
 * Concrete command that cancels an existing booking through the
 * {@link AgendaSingleton} receiver.
 */
public class CancelBookingCommand implements Command {

    private final AgendaSingleton agenda;
    private final String bookingId;
    private boolean cancelled;

    /**
     * @param agenda    receiver that owns the booking collection
     * @param bookingId identifier of the booking to cancel
     */
    public CancelBookingCommand(AgendaSingleton agenda, String bookingId) {
        this.agenda = agenda;
        this.bookingId = bookingId;
    }

    @Override
    public void execute() {
        cancelled = agenda.cancel(bookingId);
    }

    /**
     * @return {@code true} if the booking was cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }
}
