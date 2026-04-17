package ui.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import application.facade.BeautyAtHomeFacade;
import domain.booking.Booking;
import domain.booking.history.ServiceHistory;
import domain.client.Client;
import domain.professional.Professional;
import domain.review.Review;
import domain.service.ServiceComponent;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;
import infrastructure.persistence.dao.ServiceDAO;

/**
 * Simple MVC controller that renders the landing page with quick stats and links.
 */
@Controller
public class HomeViewController {

        private final ClientDAO clientDAO;
        private final ProfessionalDAO professionalDAO;
        private final BookingDAO bookingDAO;
        private final ReviewDAO reviewDAO;
        private final ServiceDAO serviceDAO;
        private final BeautyAtHomeFacade facade;

    public HomeViewController(ClientDAO clientDAO,
                              ProfessionalDAO professionalDAO,
                                                          BookingDAO bookingDAO,
                                                          ReviewDAO reviewDAO,
                                                          ServiceDAO serviceDAO,
                                                          BeautyAtHomeFacade facade) {
        this.clientDAO = clientDAO;
        this.professionalDAO = professionalDAO;
        this.bookingDAO = bookingDAO;
                this.reviewDAO = reviewDAO;
                this.serviceDAO = serviceDAO;
                this.facade = facade;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Client> clients = snapshot(clientDAO.findAll());
        List<Professional> professionals = snapshot(professionalDAO.findAll());
        List<Booking> bookings = snapshot(bookingDAO.findAll());
        List<Review> reviews = snapshot(reviewDAO.findAll());
                List<ServiceComponent> services = serviceDAO.findAll();
                List<ServiceHistory> historyHighlights = professionals.stream()
                                .flatMap(pro -> facade.viewProfessionalHistory(pro.getId()).stream())
                                .sorted(Comparator.comparing(ServiceHistory::getDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList());

        model.addAttribute("clientCount", clients.size());
        model.addAttribute("professionalCount", professionals.size());
        model.addAttribute("bookingCount", bookings.size());
        model.addAttribute("reviewCount", reviews.size());
        model.addAttribute("averageRating", reviews.stream()
                .mapToInt(review -> review.getRating().getValue())
                .average()
                .orElse(0.0));
        model.addAttribute("featuredClients", clients.stream().limit(3).collect(Collectors.toList()));
        model.addAttribute("heroProfessionals", professionals.stream().limit(3).collect(Collectors.toList()));
        model.addAttribute("upcomingBookings", bookings.stream()
                .sorted(Comparator.comparing(Booking::getDateTime))
                .limit(5)
                .collect(Collectors.toList()));
        model.addAttribute("latestReviews", reviews.stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                .limit(3)
                .collect(Collectors.toList()));
        model.addAttribute("spotlightServices", services.stream().limit(4).collect(Collectors.toList()));
        model.addAttribute("sponsoredProfessionals", professionals.stream()
                .filter(pro -> pro.getBrand() != null)
                .limit(3)
                .collect(Collectors.toList()));
        model.addAttribute("historyHighlights", historyHighlights);
        return "index";
    }

    private <T> List<T> snapshot(Iterable<T> source) {
        return StreamSupport.stream(source.spliterator(), false)
                .collect(Collectors.toList());
    }
}
