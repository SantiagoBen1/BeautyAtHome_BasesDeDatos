package com.beautyathome.controllers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beautyathome.services.BeautyAtHomeFacade;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.dto.viewmodel.BookingForm;
import com.beautyathome.dto.viewmodel.BookingLane;

/**
 * MVC controller that renders booking lists and creation forms.
 */
@Controller
@RequestMapping("/bookings")
public class BookingViewController {

    private final BeautyAtHomeFacade facade;
    private final BookingRepository bookingRepositoryPort;

    public BookingViewController(BeautyAtHomeFacade facade, BookingRepository bookingRepositoryPort) {
        this.facade = facade;
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingRepositoryPort.findAll();
        model.addAttribute("bookings", bookings);
        model.addAttribute("bookingLanes", BookingLane.from(bookings));
        model.addAttribute("bookingForm", new BookingForm());
        return "bookings";
    }

    @PostMapping
    public String createBooking(@ModelAttribute("bookingForm") BookingForm form,
                                RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(form.getDateTime());
            facade.bookService(form.getClientId(),
                    form.getProfessionalId(),
                    form.getServiceId(),
                    dateTime,
                    form.getZone());
            redirectAttributes.addFlashAttribute("message", "Reserva creada correctamente");
        } catch (DateTimeParseException ex) {
            redirectAttributes.addFlashAttribute("error", "Formato de fecha invÃ¡lido. Use ISO-8601");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/bookings";
    }
}
