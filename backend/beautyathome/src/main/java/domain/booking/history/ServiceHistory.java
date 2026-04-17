package domain.booking.history;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.booking.Booking;
import domain.client.Client;
import domain.professional.Professional;
import domain.service.ServiceComponent;
import domain.service.image.Photo;

/**
 * Value object capturing the historical execution of a service, including the
 * associated booking data, the participants, and optional media evidence.
 */
public class ServiceHistory {

	private final Booking booking;
	private final Client client;
	private final Professional professional;
	private final ServiceComponent service;
	private final LocalDateTime dateTime;
	private final List<Photo> photos = new ArrayList<>();

	/**
	 * Creates a history entry for a specific booking execution.
	 *
	 * @param booking booking reference
	 * @param client client that received the service
	 * @param professional professional that executed the service
	 * @param service service performed
	 * @param dateTime execution timestamp
	 */
	public ServiceHistory(Booking booking,
						  Client client,
						  Professional professional,
						  ServiceComponent service,
						  LocalDateTime dateTime) {
		this.booking = booking;
		this.client = client;
		this.professional = professional;
		this.service = service;
		this.dateTime = dateTime;
	}

	/**
	 * @return linked booking
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * @return client involved in the service
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @return professional that performed the service
	 */
	public Professional getProfessional() {
		return professional;
	}

	/**
	 * @return service component executed
	 */
	public ServiceComponent getService() {
		return service;
	}

	/**
	 * @return execution date/time
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Stores an evidence photo for the service.
	 *
	 * @param photo media reference to add
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	/**
	 * @return immutable view of attached photos
	 */
	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
}
