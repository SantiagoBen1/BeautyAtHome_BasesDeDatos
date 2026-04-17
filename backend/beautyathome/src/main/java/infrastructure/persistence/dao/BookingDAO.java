package infrastructure.persistence.dao;

import java.util.Collections;
import java.util.List;

import domain.booking.Booking;

/**
 * DAO especializado para reservas que expone búsquedas adicionales.
 */
public interface BookingDAO extends BaseDAO<Booking, String> {

	/**
	 * Obtiene todas las reservas asociadas a una profesional.
	 *
	 * @param professionalId identificador de la profesional
	 * @return lista (posiblemente vacía) de reservas
	 */
	default List<Booking> findByProfessionalId(String professionalId) {
		return Collections.emptyList();
	}

	/**
	 * Lista todas las reservas registradas.
	 */
	default List<Booking> findAll() {
		return Collections.emptyList();
	}
}
