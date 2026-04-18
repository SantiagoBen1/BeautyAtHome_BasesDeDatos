package application.review;

import domain.review.Review;
import infrastructure.proxy.ReviewGuardProxy;

/**
 * Servicio de aplicación que expone operaciones de reseñas a la capa web.
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
	 * Crea una reseña delegando en el proxy para validar negocio.
	 *
	 * @param bookingId reserva evaluada
	 * @param rating calificación del cliente
	 * @param text comentario opcional
	 * @return reseña persistida
	 */
	public Review addReview(String bookingId, int rating, String text) {
		return reviewGuardProxy.createReview(bookingId, rating, text);
	}
}
