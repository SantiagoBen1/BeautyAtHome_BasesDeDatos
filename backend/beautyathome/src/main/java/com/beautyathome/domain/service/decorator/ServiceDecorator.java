package domain.service.decorator;

import java.util.List;

import domain.service.ServiceComponent;
import domain.service.image.ImageReference;
import domain.service.visitor.ServiceVisitor;

/**
 * Base decorator that wraps another {@link ServiceComponent} and delegates all
 * operations to it. Subclasses override selective methods to modify behavior
 * (price, name, duration, etc.).
 */
public abstract class ServiceDecorator implements ServiceComponent {

	protected final ServiceComponent component;

	/**
	 * Creates a decorator around the provided component.
	 *
	 * @param component component to wrap
	 */
	protected ServiceDecorator(ServiceComponent component) {
		this.component = component;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return component.getName();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return component.getDescription();
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice() {
		return component.getPrice();
	}

	/** {@inheritDoc} */
	@Override
	public int getDurationMin() {
		return component.getDurationMin();
	}

	/** {@inheritDoc} */
	@Override
	public List<ImageReference> getImages() {
		return component.getImages();
	}

	/** {@inheritDoc} */
	@Override
	public void execute() {
		component.execute();
	}

	/** {@inheritDoc} */
	@Override
	public void accept(ServiceVisitor visitor) {
		component.accept(visitor);
	}
}
