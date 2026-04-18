package com.beautyathome.application.booking; // Paquete actualizado

import com.beautyathome.application.booking.validation.BookingRequestHandler;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.BookingBuilder;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Application service that orchestrates validation and booking persistence
 * through BookingRepositoryPort.
 */
@Service // Obligatorio para que Spring inyecte esta clase
public class BookingService {

    private final BookingRequestHandler validationChain;
    private final BookingRepositoryPort bookingRepository; // Reemplazamos AgendaSingleton por el Puerto

    public BookingService(BookingRequestHandler validationChain,
                          BookingRepositoryPort bookingRepository) {
        this.validationChain = validationChain;
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
            .withService(request.getServiceId())
            .withDate(request.getDateTime())
            .build(); // AsegÃºrate de llamar a build()

        // Delegamos la persistencia al puerto (Postgres o Memoria, al dominio no le importa)
        return bookingRepository.save(booking);
    }
}