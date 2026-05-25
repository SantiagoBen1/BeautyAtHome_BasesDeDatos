package com.beautyathome.controllers.view;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.beautyathome.services.BeautyAtHomeFacade;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.history.ServiceHistory;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.domain.client.Client;
import com.beautyathome.repositories.ClientRepository;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.repositories.ProfessionalRepository;
import com.beautyathome.domain.review.Review;
import com.beautyathome.repositories.ReviewRepository;
import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.repositories.ServiceRepository;

/**
 * Simple MVC controller that renders the landing page with quick stats and links.
 */
@Controller
public class HomeViewController {

        private final ClientRepository clientRepositoryPort;
        private final ProfessionalRepository professionalRepositoryPort;
        private final BookingRepository bookingRepositoryPort;
        private final ReviewRepository reviewRepositoryPort;
        private final ServiceRepository serviceRepositoryPort;
        private final BeautyAtHomeFacade facade;

    public HomeViewController(ClientRepository clientRepositoryPort,
                              ProfessionalRepository professionalRepositoryPort,
                              BookingRepository bookingRepositoryPort,
                              ReviewRepository reviewRepositoryPort,
                              ServiceRepository serviceRepositoryPort,
                              BeautyAtHomeFacade facade) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.professionalRepositoryPort = professionalRepositoryPort;
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.reviewRepositoryPort = reviewRepositoryPort;
        this.serviceRepositoryPort = serviceRepositoryPort;
        this.facade = facade;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Client> clients = snapshot(clientRepositoryPort.findAll());
        List<Professional> professionals = snapshot(professionalRepositoryPort.findAll());
        List<Booking> bookings = snapshot(bookingRepositoryPort.findAll());
        List<Review> reviews = snapshot(reviewRepositoryPort.findAll());
                List<ServiceComponent> services = snapshot(serviceRepositoryPort.findAll());
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
