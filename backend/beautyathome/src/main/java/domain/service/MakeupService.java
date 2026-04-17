package domain.service;

import java.util.List;

import domain.service.image.ImageReference;

/**
 * Concrete service representing makeup offerings.
 */
public class MakeupService extends ServiceLeaf {

	/**
	 * Crea un servicio de maquillaje con categoría predefinida.
	 *
	 * @param name         nombre del servicio mostrado al cliente
	 * @param description  detalle de lo que incluye el servicio
	 * @param price        precio final que se mostrará en la app
	 * @param durationMin  duración estimada en minutos
	 * @param images       imágenes de referencia para evidenciar resultados
	 */
	public MakeupService(String name,
				 String description,
				 double price,
				 int durationMin,
				 List<ImageReference> images) {
		super(name, description, price, durationMin, images);
		setCategory(new Category("Makeup"));
	}
}
