package infrastructure.persistence.dao;

import java.util.Collections;
import java.util.List;

import domain.professional.Professional;

/**
 * DAO encargado de administrar profesionales de belleza.
 */
public interface ProfessionalDAO extends BaseDAO<Professional, String> {

	/**
	 * Recupera todas las profesionales disponibles.
	 *
	 * @return lista inmutable por defecto
	 */
	default List<Professional> findAll() {
		return Collections.emptyList();
	}
}
