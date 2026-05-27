package com.beautyathome.domain.service.factory;

import java.util.List;

import com.beautyathome.domain.service.HaircutService;
import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.image.ImageReference;

/**
 * Factory that produces {@link HaircutService} instances.
 */
public class HaircutCreator extends ServiceCreator {

	@Override
	protected ServiceComponent instantiate(String name,
										   String description,
										   double price,
										   int duration,
										   List<ImageReference> images) {
		return new HaircutService(name, description, price, duration, images);
	}
}
