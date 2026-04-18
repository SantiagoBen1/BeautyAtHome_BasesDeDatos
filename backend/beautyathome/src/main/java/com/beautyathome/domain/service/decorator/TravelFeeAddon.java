package domain.service.decorator;

import domain.service.ServiceComponent;

/**
 * Decorator that adds a fixed travel fee to the service price.
 */
public class TravelFeeAddon extends ServiceDecorator {

	private final double travelFee;

	/**
	 * @param component service being decorated
	 * @param travelFee additional travel surcharge
	 */
	public TravelFeeAddon(ServiceComponent component, double travelFee) {
		super(component);
		this.travelFee = travelFee;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return component.getName() + " + Travel";
	}

	/** {@inheritDoc} */
	@Override
	public double getPrice() {
		return component.getPrice() + travelFee;
	}
}
