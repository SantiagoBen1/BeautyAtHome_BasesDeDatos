package domain.service.factory;

import java.util.Collections;
import java.util.List;

import domain.service.ServiceComponent;
import domain.service.image.ImageReference;

/**
 * Factory Method base class that defines the creation template for services.
 */
public abstract class ServiceCreator {

	/**
	 * Creates a service while guarding against null collections.
	 */
	public ServiceComponent createService(String name,
										  String description,
										  double price,
										  int duration,
										  List<ImageReference> images) {
		List<ImageReference> safeImages = images == null ? Collections.emptyList() : images;
		return instantiate(name, description, price, duration, safeImages);
	}

	/**
	 * Concrete factory hook responsible for creating the service instance.
	 */
	protected abstract ServiceComponent instantiate(String name,
													 String description,
													 double price,
													 int duration,
													 List<ImageReference> images);
}
