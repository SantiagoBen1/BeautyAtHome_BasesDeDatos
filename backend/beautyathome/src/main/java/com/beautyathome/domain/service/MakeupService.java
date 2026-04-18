package com.beautyathome.domain.service;

import java.util.List;

import com.beautyathome.domain.service.image.ImageReference;

/**
 * Concrete service representing makeup offerings.
 */
public class MakeupService extends ServiceLeaf {

	/**
	 * Crea un servicio de maquillaje con categorÃ­a predefinida.
	 *
	 * @param name         nombre del servicio mostrado al cliente
	 * @param description  detalle de lo que incluye el servicio
	 * @param price        precio final que se mostrarÃ¡ en la app
	 * @param durationMin  duraciÃ³n estimada en minutos
	 * @param images       imÃ¡genes de referencia para evidenciar resultados
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
