package com.beautyathome.domain.service;

import java.util.List;

import com.beautyathome.domain.service.image.ImageReference;

/**
 * Concrete factory-produced service representing a haircut offering.
 */
public class HaircutService extends ServiceLeaf {

	/**
	 * Crea un servicio de corte de cabello con su categorÃ­a asociada.
	 *
	 * @param name         nombre comercial del servicio
	 * @param description  descripciÃ³n visible para los clientes
	 * @param price        precio base en la moneda configurada
	 * @param durationMin  duraciÃ³n estimada en minutos
	 * @param images       galerÃ­a de apoyo para ilustrar resultados
	 */
	public HaircutService(String name,
						  String description,
						  double price,
						  int durationMin,
						  List<ImageReference> images) {
		super(name, description, price, durationMin, images);
		setCategory(new Category("Haircut"));
	}
}
