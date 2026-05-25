package com.beautyathome.services.proxy;

import java.util.HashSet;
import java.util.Set;

import com.beautyathome.domain.review.Review;

/**
 * Proxy que protege la creaciÃ³n de reseÃ±as validando unicidad y rango.
 */
public class ReviewGuardProxy {

	private final ReviewService real;
	private final Set<String> reviewedBookings = new HashSet<>();

	/**
	 * @param real servicio real que persistirÃ¡ la reseÃ±a
	 */
	public ReviewGuardProxy(ReviewService real) {
		this.real = real;
	}

	/**
	 * Crea la reseÃ±a solo si la reserva no ha sido evaluada previamente.
	 *
	 * @param bookingId reserva a evaluar
	 * @param rating calificaciÃ³n en estrellas
	 * @param text comentario
	 * @return reseÃ±a creada por el servicio real
	 */
	public synchronized Review createReview(String bookingId, int rating, String text) {
		if (reviewedBookings.contains(bookingId)) {
			throw new IllegalStateException("Booking already reviewed");
		}
		if (rating < 1 || rating > 5) {
			throw new IllegalArgumentException("Invalid rating");
		}
		Review review = real.createReview(bookingId, rating, text);
		reviewedBookings.add(bookingId);
		return review;
	}
}
