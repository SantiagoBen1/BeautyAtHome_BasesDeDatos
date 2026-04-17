package domain.review;

import java.time.LocalDateTime;
import java.util.UUID;

import domain.booking.Booking;
import domain.client.Client;
import domain.professional.Professional;
import domain.review.rating.RatingValueObject;

/**
 * Opinión que un cliente deja sobre un {@link Booking} y su profesional.
 */
public class Review {

	private final String id;
	private final Booking booking;
	private final Client client;
	private final Professional professional;
	private final RatingValueObject rating;
	private final String text;
	private final LocalDateTime createdAt;

	/**
	 * Crea la reseña con contenido libre y calificación en estrellas.
	 *
	 * @param id             identificador opcional; se autogenera si es nulo
	 * @param booking        reserva evaluada
	 * @param client         cliente que emite la reseña
	 * @param professional   profesional evaluada
	 * @param rating         calificación (1 a 5)
	 * @param text           comentario libre
	 * @param createdAt      fecha de creación o ahora si es nulo
	 */
	public Review(String id,
			  Booking booking,
			  Client client,
			  Professional professional,
			  RatingValueObject rating,
			  String text,
			  LocalDateTime createdAt) {
		this.id = id == null ? UUID.randomUUID().toString() : id;
		this.booking = booking;
		this.client = client;
		this.professional = professional;
		this.rating = rating;
		this.text = text;
		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}

	/**
	 * @return identificador único de la reseña
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return booking del cual se desprende la experiencia
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * @return cliente que emitió la opinión
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @return profesional evaluada
	 */
	public Professional getProfessional() {
		return professional;
	}

	/**
	 * @return valor numérico encapsulado en {@link RatingValueObject}
	 */
	public RatingValueObject getRating() {
		return rating;
	}

	/**
	 * @return comentario textual del cliente
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return fecha de publicación de la reseña
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
