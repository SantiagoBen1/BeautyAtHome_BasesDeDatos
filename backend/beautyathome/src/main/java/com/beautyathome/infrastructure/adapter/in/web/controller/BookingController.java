package api.controller;


import java.util.Objects;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.booking.BookingRequest;
import application.facade.BeautyAtHomeFacade;
import domain.booking.Booking;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BeautyAtHomeFacade facade;

    public BookingController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        Objects.requireNonNull(request, "request");
        return facade.bookService(
                request.getClientId(),
                request.getProfessionalId(),
                request.getServiceId(),
                request.getDateTime(),
                request.getZone()
        );
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable String bookingId) {
        facade.cancelBooking(bookingId);
    }
}
