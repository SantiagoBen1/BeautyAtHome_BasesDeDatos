package ui.viewmodel;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import domain.booking.history.ServiceHistory;
import domain.professional.Professional;
import domain.review.Review;
import domain.service.ServiceComponent;
import domain.service.image.ImageReference;
import domain.service.image.Photo;

/**
 * Aggregates everything needed to render a single professional in the deluxe showcase.
 */
public class ProfessionalShowcase {

    private final Professional professional;
    private final List<ServiceComponent> services;
    private final List<ServiceHistory> histories;
    private final List<Review> reviews;
    private final double averageRating;
    private final List<ServiceCard> serviceCards;
    private final List<ServiceSpotlight> serviceSpotlights;
    private final List<ReviewSpotlight> reviewSpotlights;

    public ProfessionalShowcase(Professional professional,
                                List<ServiceComponent> services,
                                List<ServiceHistory> histories,
                                List<Review> reviews,
                                double averageRating) {
        this.professional = professional;
        this.services = services == null ? Collections.emptyList() : List.copyOf(services);
        this.histories = histories == null ? Collections.emptyList() : List.copyOf(histories);
        this.reviews = reviews == null ? Collections.emptyList() : List.copyOf(reviews);
        this.averageRating = averageRating;
        this.serviceCards = buildServiceCards(this.services);
        this.serviceSpotlights = buildServiceSpotlights(this.histories);
        this.reviewSpotlights = buildReviewSpotlights(this.reviews, this.serviceSpotlights);
    }

    public Professional getProfessional() {
        return professional;
    }

    public List<ServiceComponent> getServices() {
        return services;
    }

    public List<ServiceHistory> getHistories() {
        return histories;
    }

    public List<ServiceCard> getServiceCards() {
        return serviceCards;
    }

    public ServiceHistory getHeroHistory() {
        return histories.isEmpty() ? null : histories.get(0);
    }

    public List<ServiceHistory> getHighlightHistories() {
        return histories.stream().limit(3).collect(Collectors.toList());
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Review> getFeaturedReviews() {
        return reviews.stream().limit(3).collect(Collectors.toList());
    }

    public double getAverageRating() {
        return averageRating;
    }

    public List<ServiceSpotlight> getServiceSpotlights() {
        return serviceSpotlights;
    }

        public List<ReviewSpotlight> getReviewSpotlights() {
        return reviewSpotlights;
        }

        private List<ServiceCard> buildServiceCards(List<ServiceComponent> services) {
        return services.stream()
            .map(service -> new ServiceCard(
                service.getName(),
                service.getDescription(),
                formatCop(service.getPrice()),
                service.getDurationMin() + " min",
                service.getImages() == null
                    ? List.of()
                    : service.getImages().stream()
                    .filter(Objects::nonNull)
                    .map(ImageReference::getUrl)
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
        }

        private List<ServiceSpotlight> buildServiceSpotlights(List<ServiceHistory> histories) {
        return histories.stream()
            .map(ServiceSpotlight::new)
            .collect(Collectors.toList());
        }

        private List<ReviewSpotlight> buildReviewSpotlights(List<Review> reviews,
                                   List<ServiceSpotlight> spotlights) {
        Map<String, List<String>> photosByBooking = spotlights.stream()
            .filter(story -> story.getBookingId() != null)
            .collect(Collectors.toMap(ServiceSpotlight::getBookingId,
                ServiceSpotlight::getPhotoUrls,
                (left, right) -> left));
        List<String> globalPhotos = spotlights.stream()
            .flatMap(story -> story.getPhotoUrls().stream())
            .collect(Collectors.toList());
        return reviews.stream()
            .map(review -> {
                String bookingId = review.getBooking() == null ? null : review.getBooking().getId();
                String cover = photosByBooking.getOrDefault(bookingId, Collections.emptyList()).stream()
                    .findFirst()
                    .orElseGet(() -> globalPhotos.isEmpty() ? null : globalPhotos.get(0));
                return new ReviewSpotlight(review, cover);
            })
            .collect(Collectors.toList());
        }

        private String formatCop(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        return formatter.format(value);
        }
    /**
     * DTO para mostrar información compacta de servicios publicados.
     */
    public static class ServiceCard {
        private final String name;
        private final String description;
        private final String price;
        private final String durationLabel;
        private final List<String> gallery;

        public ServiceCard(String name,
                           String description,
                           String price,
                           String durationLabel,
                           List<String> gallery) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.durationLabel = durationLabel;
            this.gallery = gallery;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }

        public String getDurationLabel() {
            return durationLabel;
        }

        public List<String> getGallery() {
            return gallery;
        }
    }

    /**
     * DTO para exponer cada servicio ejecutado con su evidencia visual.
     */
    public static class ServiceSpotlight {
        private final String bookingId;
        private final String serviceName;
        private final String clientName;
        private final LocalDateTime date;
        private final List<String> photoUrls;

        public ServiceSpotlight(ServiceHistory history) {
            this.bookingId = history.getBooking() == null ? null : history.getBooking().getId();
            this.serviceName = history.getService() == null ? "Servicio" : history.getService().getName();
            this.clientName = history.getClient() == null ? "Cliente" : history.getClient().getName();
            this.date = history.getDateTime();
            this.photoUrls = history.getPhotos() == null
                    ? List.of()
                    : history.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
        }

        public String getBookingId() {
            return bookingId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public String getClientName() {
            return clientName;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public List<String> getPhotoUrls() {
            return photoUrls;
        }
    }

    /**
     * DTO que conecta reseñas con la galería del servicio ejecutado.
     */
    public static class ReviewSpotlight {
        private final Review review;
        private final String coverPhotoUrl;

        public ReviewSpotlight(Review review, String coverPhotoUrl) {
            this.review = review;
            this.coverPhotoUrl = coverPhotoUrl;
        }

        public Review getReview() {
            return review;
        }

        public String getCoverPhotoUrl() {
            return coverPhotoUrl;
        }

        public String getCoverPhoto() {
            return coverPhotoUrl;
        }
    }
}
