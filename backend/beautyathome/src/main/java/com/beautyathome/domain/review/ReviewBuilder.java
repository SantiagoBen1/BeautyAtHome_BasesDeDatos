package com.beautyathome.domain.review;

import java.time.LocalDateTime;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.review.rating.RatingValueObject;

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
     * Define el identificador que se usarÃ¡ al construir.
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
     * Establece el {@link Client} que redacta la reseÃ±a.
     */
    public ReviewBuilder withClient(Client client) {
        this.client = client;
        return this;
    }

    /**
     * Define la profesional sobre la que recae la opiniÃ³n.
     */
    public ReviewBuilder withProfessional(Professional professional) {
        this.professional = professional;
        return this;
    }

    /**
     * Crea un {@link RatingValueObject} con la puntuaciÃ³n dada.
     */
    public ReviewBuilder withRating(int ratingValue) {
        this.rating = new RatingValueObject(ratingValue);
        return this;
    }

    /**
     * Establece el texto libre de la reseÃ±a.
     */
    public ReviewBuilder withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Fija la fecha de creaciÃ³n (Ãºtil para importaciones histÃ³ricas).
     */
    public ReviewBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Construye la reseÃ±a final usando los valores configurados.
     */
    public Review build() {
        return new Review(id, booking, client, professional, rating, text, createdAt);
    }
}
