package infrastructure.persistence.dao;


import java.util.Collections;
import java.util.List;

import domain.client.Client;

/**
 * DAO para clientes que hereda las operaciones básicas.
 */
public interface ClientDAO extends BaseDAO<Client, String> {

	/**
	 * Lista todos los clientes disponibles.
	 */
	default List<Client> findAll() {
		return Collections.emptyList();
	}
}
