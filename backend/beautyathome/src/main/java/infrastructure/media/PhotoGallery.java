package infrastructure.media;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import domain.booking.Booking;
import domain.service.image.Photo;
import infrastructure.persistence.dao.BookingDAO;

/**
 * Componente que simula un repositorio de fotos ligadas a reservas.
 */
public class PhotoGallery {

	private final StorageAdapter storageAdapter;
	private final BookingDAO bookingDAO;
	private final List<Photo> photos = new CopyOnWriteArrayList<>();

	/**
	 * @param storageAdapter adaptador que guarda físicamente la imagen
	 * @param bookingDAO DAO para validar la existencia de la reserva
	 */
	public PhotoGallery(StorageAdapter storageAdapter, BookingDAO bookingDAO) {
		this.storageAdapter = storageAdapter;
		this.bookingDAO = bookingDAO;
	}

	/**
	 * Agrega una foto ligada a una reserva verificada.
	 *
	 * @param bookingId reserva asociada
	 * @param url recurso original
	 * @param isPublic bandera de visibilidad
	 */
	public void addPhoto(String bookingId, String url, boolean isPublic) {
		Booking booking = bookingDAO.findById(bookingId);
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found for photo upload");
		}
		String storedUrl = storageAdapter.store(bookingId, url);
		photos.add(new Photo(bookingId, booking.getProfessionalId(), storedUrl, isPublic));
	}

	/**
	 * Lista las fotos asociadas a una profesional específica.
	 *
	 * @param professionalId profesional consultada
	 * @return lista inmutable de fotos
	 */
	public List<Photo> listByProfessional(String professionalId) {
		return Collections.unmodifiableList(
			photos.stream()
				.filter(photo -> photo.getProfessionalId().equals(professionalId))
				.collect(Collectors.toList())
		);
	}
}
