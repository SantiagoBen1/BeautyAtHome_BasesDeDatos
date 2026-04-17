package api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ReviewRequest;
import application.facade.BeautyAtHomeFacade;
import domain.review.Review;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final BeautyAtHomeFacade facade;

    public ReviewController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public Review addReview(@RequestBody ReviewRequest request) {
        return facade.addReview(request.getBookingId(), request.getRating(), request.getText());
    }

    @GetMapping("/professional/{professionalId}/average")
    public double getAverageRating(@PathVariable String professionalId) {
        return facade.getProfessionalAverageRating(professionalId);
    }
}
