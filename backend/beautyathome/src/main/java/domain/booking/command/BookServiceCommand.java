package domain.booking.command;

import domain.booking.AgendaSingleton;
import domain.booking.Booking;
import domain.booking.BookingBuilder;

/**
 * Concrete command that books a service through {@link AgendaSingleton} using
 * the data assembled by a {@link BookingBuilder}.
 */
public class BookServiceCommand implements Command {

    private final AgendaSingleton agenda;
    private final BookingBuilder builder;
    private Booking booking;

    /**
     * @param agenda  receiver responsible for persisting bookings
     * @param builder builder pre-loaded with booking data
     */
    public BookServiceCommand(AgendaSingleton agenda, BookingBuilder builder) {
        this.agenda = agenda;
        this.builder = builder;
    }

    @Override
    public void execute() {
        Booking draft = builder.build();
        booking = agenda.book(
                draft.getId(),
                draft.getClientId(),
                draft.getProfessionalId(),
                draft.getServiceId(),
                draft.getDateTime()
        );
    }

    /**
     * @return booking generated after execution
     */
    public Booking getResult() {
        return booking;
    }
}
