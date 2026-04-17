package ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.facade.BeautyAtHomeFacade;
import domain.booking.history.ServiceHistory;
import domain.review.Review;
import domain.service.image.Photo;
import infrastructure.persistence.dao.ReviewDAO;
import ui.viewmodel.ReviewForm;
import ui.viewmodel.ReviewShowcase;

/**
 * MVC controller that handles review pages.
 */
@Controller
@RequestMapping("/reviews")
public class ReviewViewController {

    private final BeautyAtHomeFacade facade;
    private final ReviewDAO reviewDAO;

    public ReviewViewController(BeautyAtHomeFacade facade, ReviewDAO reviewDAO) {
        this.facade = facade;
        this.reviewDAO = reviewDAO;
    }

    @GetMapping
    public String listReviews(Model model) {
        List<Review> reviews = StreamSupport.stream(reviewDAO.findAll().spliterator(), false)
            .collect(Collectors.toList());
        double averageRating = reviews.stream()
            .mapToInt(review -> review.getRating().getValue())
            .average()
            .orElse(0.0);
        List<ReviewShowcase> reviewCards = buildReviewShowcases(reviews);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewCards", reviewCards);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewForm", new ReviewForm());
        return "reviews";
    }

    @PostMapping
    public String addReview(@ModelAttribute("reviewForm") ReviewForm form,
                            RedirectAttributes redirectAttributes) {
        try {
            facade.addReview(form.getBookingId(), form.getRating(), form.getText());
            redirectAttributes.addFlashAttribute("message", "Reseña registrada");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/reviews";
    }

    private List<ReviewShowcase> buildReviewShowcases(List<Review> reviews) {
        Map<String, List<ServiceHistory>> historiesByProfessional = new HashMap<>();
        return reviews.stream()
            .map(review -> new ReviewShowcase(review, resolvePhotosFor(review, historiesByProfessional)))
            .collect(Collectors.toList());
    }

    private List<String> resolvePhotosFor(Review review, Map<String, List<ServiceHistory>> historiesByProfessional) {
        if (review == null
            || review.getProfessional() == null
            || review.getProfessional().getId() == null
            || review.getBooking() == null
            || review.getBooking().getId() == null) {
            return List.of();
        }
        String professionalId = review.getProfessional().getId();
        List<ServiceHistory> histories = historiesByProfessional.computeIfAbsent(professionalId, facade::viewProfessionalHistory);
        String bookingId = review.getBooking().getId();
        return histories.stream()
            .filter(history -> history.getBooking() != null && bookingId.equals(history.getBooking().getId()))
            .findFirst()
            .map(history -> history.getPhotos().stream()
                .filter(photo -> photo != null && photo.isPublic())
                .map(Photo::getUrl)
                .collect(Collectors.toList()))
            .orElse(List.of());
    }
}
