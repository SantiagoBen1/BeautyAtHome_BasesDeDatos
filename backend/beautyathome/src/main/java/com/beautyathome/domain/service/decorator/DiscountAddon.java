package domain.service.decorator;

import domain.service.ServiceComponent;

/**
 * Decorator applying a percentage-based discount to the wrapped service.
 */
public class DiscountAddon extends ServiceDecorator {

	private final double discountPercentage;

	/**
	 * @param component service to adjust
	 * @param discountPercentage percentage to subtract from price
	 */
	public DiscountAddon(ServiceComponent component, double discountPercentage) {
		super(component);
		this.discountPercentage = discountPercentage;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return component.getName() + " (Discount)";
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice() {
		double base = component.getPrice();
		return base - (base * (discountPercentage / 100.0));
	}
}
