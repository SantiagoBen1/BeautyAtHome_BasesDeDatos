package com.beautyathome.domain.professional.factory;

import java.util.Map;

import com.beautyathome.domain.professional.Professional;

/**
 * Define los pasos para crear profesionales a partir de datos dinÃ¡micos.
 */
public interface ProfessionalAbstractFactory {

	/**
	 * Crea una profesional del tipo requerido usando los datos suministrados.
	 *
	 * @param type cadena que determina la especialidad (p. ej. "hairstylist")
	 * @param data mapa con atributos de la profesional y sus servicios
	 * @return instancia lista para publicar en catÃ¡logos
	 */
	Professional createProfessional(String type, Map<String, Object> data);
}
