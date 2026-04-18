package domain.service.builder;

import java.util.List;

import domain.service.ServiceComponent;

/**
 * Director that orchestrates the {@link ServiceBuilder} steps to create
 * services with consistent defaults.
 */
public class ServiceDirector {

	private final ServiceBuilder builder;

	/**
	 * @param builder builder implementation to drive
	 */
	public ServiceDirector(ServiceBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Constructs a service using all available fields.
	 */
	public ServiceComponent constructService(String name,
											 String description,
											 double price,
											 int duration,
											 List<String> imageUrls) {
		builder.reset();
		builder.setName(name);
		builder.setDescription(description);
		builder.setPrice(price);
		builder.setDuration(duration);
		if (imageUrls != null) {
			imageUrls.forEach(builder::addImage);
		}
		return builder.getResult();
	}
}
