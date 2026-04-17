package application.booking;

import application.booking.validation.BookingRequestHandler;
import domain.booking.AgendaSingleton;
import domain.booking.Booking;
import domain.booking.BookingBuilder;
import domain.booking.command.BookServiceCommand;
import domain.booking.command.CommandInvoker;

/**
 * Application service that orchestrates validation and booking persistence
 * through {@link AgendaSingleton}.
 */
public class BookingService {

    private final BookingRequestHandler validationChain;
    private final AgendaSingleton agenda;
    private final CommandInvoker commandInvoker;

    /**
     * @param validationChain head of the validation chain of responsibility
     * @param agenda agenda singleton instance used for persistence
     */
    public BookingService(BookingRequestHandler validationChain,
                          AgendaSingleton agenda,
                          CommandInvoker commandInvoker) {
        this.validationChain = validationChain;
        this.agenda = agenda;
        this.commandInvoker = commandInvoker;
    }

    /**
     * Validates the booking request and stores it if successful.
     *
     * @param request booking request data
     * @return stored booking
     */
    public Booking book(BookingRequest request) {
        if (!validationChain.handle(request)) {
            throw new IllegalStateException("Booking validation failed");
        }

        BookingBuilder builder = new BookingBuilder()
            .withClient(request.getClientId())
            .withProfessional(request.getProfessionalId())
            .withService(request.getServiceId())
            .withDate(request.getDateTime());

        BookServiceCommand command = new BookServiceCommand(agenda, builder);
        commandInvoker.setCommand(command);
        commandInvoker.executeCommand();
        return command.getResult();
    }
}

