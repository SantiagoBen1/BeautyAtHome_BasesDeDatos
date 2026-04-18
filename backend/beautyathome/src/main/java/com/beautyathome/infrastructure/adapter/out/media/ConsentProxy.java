package infrastructure.media;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.service.image.Photo;

/**
 * Proxy que aplica las políticas de consentimiento antes de almacenar fotos.
 */
public class ConsentProxy {

	private final PhotoGallery gallery;
	private final Set<String> consentedBookings = new HashSet<>();

	/**
	 * @param gallery galería subyacente encargada del almacenamiento
	 */
	public ConsentProxy(PhotoGallery gallery) {
		this.gallery = gallery;
	}

	/**
	 * Marca una reserva como autorizada para publicar fotografías públicas.
	 *
	 * @param bookingId identificador de la reserva
	 */
	public void addConsent(String bookingId) {
		consentedBookings.add(bookingId);
	}

	/**
	 * Solicita almacenar una foto respetando el consentimiento previo.
	 *
	 * @param bookingId reserva de origen
	 * @param url ruta de la foto
	 * @param isPublic si el cliente desea publicación
	 */
	public void addPhoto(String bookingId, String url, boolean isPublic) {
		boolean allowed = isPublic && consentedBookings.contains(bookingId);
		gallery.addPhoto(bookingId, url, allowed);
	}

	/**
	 * Retorna las fotos disponibles para una profesional.
	 *
	 * @param professionalId profesional consultada
	 * @return lista inmutable de fotos
	 */
	public List<Photo> listByProfessional(String professionalId) {
		return gallery.listByProfessional(professionalId);
	}
}
