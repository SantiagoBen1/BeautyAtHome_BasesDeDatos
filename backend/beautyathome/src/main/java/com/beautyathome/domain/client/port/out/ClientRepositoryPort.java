package com.beautyathome.domain.client.port.out;


import java.util.Collections;
import java.util.List;

import com.beautyathome.domain.client.Client;

/**
 * DAO para clientes que hereda las operaciones bÃ¡sicas.
 */
public interface ClientDAO extends BaseDAO<Client, String> {

	/**
	 * Lista todos los clientes disponibles.
	 */
	default List<Client> findAll() {
		return Collections.emptyList();
	}
}
