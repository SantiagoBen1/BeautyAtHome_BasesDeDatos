package domain.service.builder;

import java.util.ArrayList;
import java.util.List;

import domain.service.ServiceComponent;
import domain.service.ServiceLeaf;
import domain.service.image.ImageReference;

/**
 * Simple builder that produces {@link ServiceLeaf} instances.
 */
public class BasicServiceBuilder implements ServiceBuilder {

	private String name;
	private String description;
	private double price;
	private int duration;
	private final List<ImageReference> images = new ArrayList<>();

	/** {@inheritDoc} */
	@Override
	public void reset() {
		name = null;
		description = null;
		price = 0;
		duration = 0;
		images.clear();
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/** {@inheritDoc} */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/** {@inheritDoc} */
	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	/** {@inheritDoc} */
	@Override
	public void setDuration(int durationMinutes) {
		this.duration = durationMinutes;
	}

	/** {@inheritDoc} */
	@Override
	public void addImage(String url) {
		images.add(new ImageReference(url));
	}

	/** {@inheritDoc} */
	@Override
	public ServiceComponent getResult() {
		ServiceLeaf service = new ServiceLeaf(name, description, price, duration, new ArrayList<>(images));
		reset();
		return service;
	}
}
