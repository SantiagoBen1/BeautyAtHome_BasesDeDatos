package infrastructure.persistence.dao;

import java.util.Collections;
import java.util.List;

import domain.service.ServiceComponent;

/**
 * DAO encargado de almacenar servicios y su relación con profesionales.
 */
public interface ServiceDAO extends BaseDAO<ServiceComponent, String> {

	/**
	 * Obtiene los servicios publicados por una profesional.
	 */
	default List<ServiceComponent> findByProfessionalId(String professionalId) {
		return Collections.emptyList();
	}

	/**
	 * Guarda un servicio y lo asocia a una profesional cuando aplique.
	 */
	default ServiceComponent saveForProfessional(String professionalId, ServiceComponent serviceComponent) {
		return save(serviceComponent);
	}

	/**
	 * Lista todos los servicios publicados sin importar la profesional.
	 */
	default List<ServiceComponent> findAll() {
		return Collections.emptyList();
	}
}
