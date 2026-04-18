package ui.controller;

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

import application.facade.BeautyAtHomeFacade;
import domain.booking.Booking;
import infrastructure.persistence.dao.BookingDAO;
import ui.viewmodel.BookingForm;
import ui.viewmodel.BookingLane;

/**
 * MVC controller that renders booking lists and creation forms.
 */
@Controller
@RequestMapping("/bookings")
public class BookingViewController {

    private final BeautyAtHomeFacade facade;
    private final BookingDAO bookingDAO;

    public BookingViewController(BeautyAtHomeFacade facade, BookingDAO bookingDAO) {
        this.facade = facade;
        this.bookingDAO = bookingDAO;
    }

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingDAO.findAll();
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
            redirectAttributes.addFlashAttribute("error", "Formato de fecha inválido. Use ISO-8601");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/bookings";
    }
}
