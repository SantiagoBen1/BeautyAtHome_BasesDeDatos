package com.beautyathome.application.review;

import com.beautyathome.domain.review.Review;
import com.beautyathome.infrastructure.proxy.ReviewGuardProxy;

/**
 * Servicio de aplicaciÃ³n que expone operaciones de reseÃ±as a la capa web.
 */
public class ReviewServiceApplication {

	private final ReviewGuardProxy reviewGuardProxy;

	/**
	 * @param reviewGuardProxy proxy que valida duplicados y rating
	 */
	public ReviewServiceApplication(ReviewGuardProxy reviewGuardProxy) {
		this.reviewGuardProxy = reviewGuardProxy;
	}

	/**
	 * Crea una reseÃ±a delegando en el proxy para validar negocio.
	 *
	 * @param bookingId reserva evaluada
	 * @param rating calificaciÃ³n del cliente
	 * @param text comentario opcional
	 * @return reseÃ±a persistida
	 */
	public Review addReview(String bookingId, int rating, String text) {
		return reviewGuardProxy.createReview(bookingId, rating, text);
	}
}
