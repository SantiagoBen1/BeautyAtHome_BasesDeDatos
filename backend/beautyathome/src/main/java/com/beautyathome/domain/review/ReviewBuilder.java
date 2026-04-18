package domain.review;

import java.time.LocalDateTime;

import domain.booking.Booking;
import domain.client.Client;
import domain.professional.Professional;
import domain.review.rating.RatingValueObject;

/**
 * Builder fluido para crear instancias coherentes de {@link Review}.
 */
public class ReviewBuilder {

    private String id;
    private Booking booking;
    private Client client;
    private Professional professional;
    private RatingValueObject rating;
    private String text;
    private LocalDateTime createdAt;

    /**
     * Define el identificador que se usará al construir.
     */
    public ReviewBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Asigna el {@link Booking} evaluado.
     */
    public ReviewBuilder withBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    /**
     * Establece el {@link Client} que redacta la reseña.
     */
    public ReviewBuilder withClient(Client client) {
        this.client = client;
        return this;
    }

    /**
     * Define la profesional sobre la que recae la opinión.
     */
    public ReviewBuilder withProfessional(Professional professional) {
        this.professional = professional;
        return this;
    }

    /**
     * Crea un {@link RatingValueObject} con la puntuación dada.
     */
    public ReviewBuilder withRating(int ratingValue) {
        this.rating = new RatingValueObject(ratingValue);
        return this;
    }

    /**
     * Establece el texto libre de la reseña.
     */
    public ReviewBuilder withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Fija la fecha de creación (útil para importaciones históricas).
     */
    public ReviewBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Construye la reseña final usando los valores configurados.
     */
    public Review build() {
        return new Review(id, booking, client, professional, rating, text, createdAt);
    }
}
