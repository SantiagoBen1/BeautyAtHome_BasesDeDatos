package infrastructure.media;

/**
 * Adaptador mínimo que abstrae el almacenamiento físico de fotos.
 */
public class StorageAdapter {

	/**
	 * Simula el guardado de una foto y retorna la URL resultante.
	 *
	 * @param bookingId referencia de la reserva
	 * @param url origen de la imagen
	 * @return URL final almacenada (mock)
	 */
	public String store(String bookingId, String url) {
		return url;
	}
}
