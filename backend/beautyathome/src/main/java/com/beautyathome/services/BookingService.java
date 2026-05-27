package com.beautyathome.services; // Paquete actualizado

import com.beautyathome.dto.BookingRequest;
import com.beautyathome.services.validation.BookingRequestHandler;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.BookingBuilder;
import com.beautyathome.repositories.BookingRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that orchestrates validation and booking persistence
 * through BookingRepository.
 */
@Service // Obligatorio para que Spring inyecte esta clase
public class BookingService {

    private final BookingRequestHandler validationChain;
    private final BookingRepository bookingRepository; // Reemplazamos AgendaSingleton por el Puerto

    public BookingService(java.util.List<BookingRequestHandler> handlers,
                          BookingRepository bookingRepository) {
        if (!handlers.isEmpty()) {
            for (int i = 0; i < handlers.size() - 1; i++) {
                handlers.get(i).setNext(handlers.get(i + 1));
            }
            this.validationChain = handlers.get(0);
        } else {
            this.validationChain = new BookingRequestHandler() {
                @Override
                protected boolean doHandle(BookingRequest request) { return true; }
            };
        }
        this.bookingRepository = bookingRepository;
    }

    public Booking book(BookingRequest request) {
        if (!validationChain.handle(request)) {
            throw new IllegalStateException("Booking validation failed");
        }

        // Construimos la entidad de dominio pura
        Booking booking = new BookingBuilder()
            .withClient(request.getClientId())
            .withProfessional(request.getProfessionalId())
            .withServices(request.getServiceIds())
            .withDate(request.getDateTime())
            .build(); // AsegÃºrate de llamar a build()

        // Delegamos la persistencia al puerto (Postgres o Memoria, al dominio no le importa)
        return bookingRepository.save(booking);
    }
}