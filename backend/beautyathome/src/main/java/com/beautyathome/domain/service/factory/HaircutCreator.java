package domain.service.factory;

import java.util.List;

import domain.service.HaircutService;
import domain.service.ServiceComponent;
import domain.service.image.ImageReference;

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
