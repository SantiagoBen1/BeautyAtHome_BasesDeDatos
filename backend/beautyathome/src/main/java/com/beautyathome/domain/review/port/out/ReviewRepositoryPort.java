package com.beautyathome.domain.review.port.out;

import java.util.Collections;
import java.util.List;

import com.beautyathome.domain.review.Review;

/**
 * DAO especializado para reseÃ±as de profesionales.
 */
public interface ReviewDAO extends BaseDAO<Review, String> {

	/**
	 * Busca reseÃ±as asociadas a una profesional.
	 *
	 * @param professionalId profesional evaluada
	 * @return lista de reseÃ±as encontradas
	 */
	default List<Review> findByProfessionalId(String professionalId) {
		return Collections.emptyList();
	}

	/**
	 * Lista todas las reseÃ±as almacenadas.
	 */
	default List<Review> findAll() {
		return Collections.emptyList();
	}
}
