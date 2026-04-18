package com.beautyathome.infrastructure.proxy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.ReviewBuilder;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;

/**
 * Servicio que compone reseÃ±as a partir de daos y mantiene un cache ligero.
 */
public class ReviewService {

	private final BookingDAO bookingDAO;
	private final ClientDAO clientDAO;
	private final ProfessionalDAO professionalDAO;
	private final ReviewDAO reviewDAO;
	private final List<Review> cache = new CopyOnWriteArrayList<>();

	/**
	 * @param bookingDAO DAO de reservas
	 * @param clientDAO DAO de clientes
	 * @param professionalDAO DAO de profesionales
	 * @param reviewDAO DAO de reseÃ±as
	 */
	public ReviewService(BookingDAO bookingDAO,
						 ClientDAO clientDAO,
						 ProfessionalDAO professionalDAO,
						 ReviewDAO reviewDAO) {
		this.bookingDAO = bookingDAO;
		this.clientDAO = clientDAO;
		this.professionalDAO = professionalDAO;
		this.reviewDAO = reviewDAO;
	}

	/**
	 * Construye y persiste una reseÃ±a para la reserva proporcionada.
	 *
	 * @param bookingId id de la reserva
	 * @param rating calificaciÃ³n deseada
	 * @param text comentario opcional
	 * @return reseÃ±a almacenada
	 */
	public Review createReview(String bookingId, int rating, String text) {
		Booking booking = bookingDAO.findById(bookingId);
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found: " + bookingId);
		}
		Client client = clientDAO.findById(booking.getClientId());
		Professional professional = professionalDAO.findById(booking.getProfessionalId());
		if (client == null || professional == null) {
			throw new IllegalStateException("Booking references missing entities");
		}

		Review review = new ReviewBuilder()
				.withBooking(booking)
				.withClient(client)
				.withProfessional(professional)
				.withRating(rating)
				.withText(text)
				.build();

		Review persisted = reviewDAO.save(review);
		cache.add(persisted);
		return persisted;
	}

	/**
	 * Calcula el promedio reutilizando reseÃ±as en cache cuando el DAO estÃ¡ vacÃ­o.
	 *
	 * @param professionalId profesional evaluada
	 * @return promedio calculado
	 */
	public double getAverageForProfessional(String professionalId) {
		List<Review> reviews = reviewDAO.findByProfessionalId(professionalId);
		if (reviews.isEmpty()) {
			reviews = cache;
		}
		return reviews.stream()
				.filter(r -> r.getProfessional() != null)
				.filter(r -> professionalId.equals(r.getProfessional().getId()))
				.mapToInt(r -> r.getRating().getValue())
				.average()
				.orElse(0.0);
	}
}
