package com.beautyathome.infrastructure.adapter.out.media;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.service.image.Photo;

/**
 * Componente que simula un repositorio de fotos ligadas a reservas.
 */
@Component
public class PhotoGallery {
    private final BookingRepositoryPort bookingRepository;
    private final List<Photo> photos = new CopyOnWriteArrayList<>();
    private final StorageAdapter storageAdapter = new StorageAdapter();

    public PhotoGallery(BookingRepositoryPort bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

	/**
	 * Agrega una foto ligada a una reserva verificada.
	 *
	 * @param bookingId reserva asociada
	 * @param url recurso original
	 * @param isPublic bandera de visibilidad
	 */
	public void addPhoto(String bookingId, String url, boolean isPublic) {
		Booking booking = bookingRepository.findById(bookingId).orElse(null);
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
