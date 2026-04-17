package ui.viewmodel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import domain.review.Review;

/**
 * View model that couples a review with the public gallery extracted from the executed service.
 */
public class ReviewShowcase {

    private final Review review;
    private final List<String> photoUrls;

    public ReviewShowcase(Review review, List<String> photoUrls) {
        this.review = review;
        this.photoUrls = photoUrls == null ? List.of() : List.copyOf(photoUrls);
    }

    public Review getReview() {
        return review;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public boolean hasMedia() {
        return !photoUrls.isEmpty();
    }

    public String getCoverPhoto() {
        return hasMedia() ? photoUrls.get(0) : null;
    }

    public List<String> getSupportingPhotos() {
        if (photoUrls.size() <= 1) {
            return Collections.emptyList();
        }
        return photoUrls.stream().skip(1).limit(3).collect(Collectors.toList());
    }
}
