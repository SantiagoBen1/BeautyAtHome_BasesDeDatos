package domain.professional.factory;

import java.util.Map;

import domain.professional.Professional;

/**
 * Define los pasos para crear profesionales a partir de datos dinámicos.
 */
public interface ProfessionalAbstractFactory {

	/**
	 * Crea una profesional del tipo requerido usando los datos suministrados.
	 *
	 * @param type cadena que determina la especialidad (p. ej. "hairstylist")
	 * @param data mapa con atributos de la profesional y sus servicios
	 * @return instancia lista para publicar en catálogos
	 */
	Professional createProfessional(String type, Map<String, Object> data);
}
