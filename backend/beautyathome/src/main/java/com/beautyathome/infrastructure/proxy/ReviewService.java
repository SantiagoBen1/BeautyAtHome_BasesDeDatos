package com.beautyathome.infrastructure.proxy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.ReviewBuilder;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;
import com.beautyathome.domain.review.port.out.ReviewRepositoryPort;

/**
 * Servicio que compone reseÃ±as a partir de daos y mantiene un cache ligero.
 */
public class ReviewService {

	private final BookingRepositoryPort bookingRepositoryPort;
	private final ClientRepositoryPort clientRepositoryPort;
	private final ProfessionalRepositoryPort professionalRepositoryPort;
	private final ReviewRepositoryPort reviewRepositoryPort;
	private final List<Review> cache = new CopyOnWriteArrayList<>();

	/**
	 * @param bookingRepositoryPort puerto de repositorio de reservas
	 * @param clientRepositoryPort puerto de repositorio de clientes
	 * @param professionalRepositoryPort puerto de repositorio de profesionales
	 * @param reviewRepositoryPort puerto de repositorio de reseÃ±as
	 */
	public ReviewService(BookingRepositoryPort bookingRepositoryPort,
						 ClientRepositoryPort clientRepositoryPort,
						 ProfessionalRepositoryPort professionalRepositoryPort,
						 ReviewRepositoryPort reviewRepositoryPort) {
		this.bookingRepositoryPort = bookingRepositoryPort;
		this.clientRepositoryPort = clientRepositoryPort;
		this.professionalRepositoryPort = professionalRepositoryPort;
		this.reviewRepositoryPort = reviewRepositoryPort;
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
		Booking booking = bookingRepositoryPort.findById(bookingId);
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found: " + bookingId);
		}
		Client client = clientRepositoryPort.findById(booking.getClientId());
		Professional professional = professionalRepositoryPort.findById(booking.getProfessionalId());
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

		Review persisted = reviewRepositoryPort.save(review);
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
		List<Review> reviews = reviewRepositoryPort.findByProfessionalId(professionalId);
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
