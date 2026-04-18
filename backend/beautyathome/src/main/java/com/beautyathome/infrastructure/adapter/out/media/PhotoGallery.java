package com.beautyathome.infrastructure.adapter.out.media;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.service.image.Photo;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;

/**
 * Componente que simula un repositorio de fotos ligadas a reservas.
 */
public class PhotoGallery {

	private final StorageAdapter storageAdapter;
	private final BookingRepositoryPort bookingRepositoryPort;
	private final List<Photo> photos = new CopyOnWriteArrayList<>();

	/**
	 * @param storageAdapter adaptador que guarda fÃ­sicamente la imagen
	 * @param bookingRepositoryPort repositorio para validar la existencia de la reserva
	 */
	public PhotoGallery(StorageAdapter storageAdapter, BookingRepositoryPort bookingRepositoryPort) {
		this.storageAdapter = storageAdapter;
		this.bookingRepositoryPort = bookingRepositoryPort;
	}

	/**
	 * Agrega una foto ligada a una reserva verificada.
	 *
	 * @param bookingId reserva asociada
	 * @param url recurso original
	 * @param isPublic bandera de visibilidad
	 */
	public void addPhoto(String bookingId, String url, boolean isPublic) {
		Booking booking = bookingRepositoryPort.findById(bookingId);
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found for photo upload");
		}
		String storedUrl = storageAdapter.store(bookingId, url);
		photos.add(new Photo(bookingId, booking.getProfessionalId(), storedUrl, isPublic));
	}

	/**
	 * Lista las fotos asociadas a una profesional especÃ­fica.
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
