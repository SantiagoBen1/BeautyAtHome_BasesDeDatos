package com.beautyathome.domain.review;

import java.time.LocalDateTime;
import java.util.UUID;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.review.rating.RatingValueObject;

/**
 * OpiniÃ³n que un cliente deja sobre un {@link Booking} y su profesional.
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
	 * Crea la reseÃ±a con contenido libre y calificaciÃ³n en estrellas.
	 *
	 * @param id             identificador opcional; se autogenera si es nulo
	 * @param booking        reserva evaluada
	 * @param client         cliente que emite la reseÃ±a
	 * @param professional   profesional evaluada
	 * @param rating         calificaciÃ³n (1 a 5)
	 * @param text           comentario libre
	 * @param createdAt      fecha de creaciÃ³n o ahora si es nulo
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
	 * @return identificador Ãºnico de la reseÃ±a
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
	 * @return cliente que emitiÃ³ la opiniÃ³n
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
	 * @return valor numÃ©rico encapsulado en {@link RatingValueObject}
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
	 * @return fecha de publicaciÃ³n de la reseÃ±a
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
