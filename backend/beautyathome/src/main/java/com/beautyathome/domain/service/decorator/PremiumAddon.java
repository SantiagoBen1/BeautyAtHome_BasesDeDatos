package domain.service.decorator;

import domain.service.ServiceComponent;

/**
 * Decorator that adds a premium upcharge to the wrapped service.
 */
public class PremiumAddon extends ServiceDecorator {

    private double extraPrice;

    /**
     * @param component service being upgraded
     * @param extraPrice additional price to charge
     */
    public PremiumAddon(ServiceComponent component, double extraPrice) {
        super(component);
        this.extraPrice = extraPrice;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return component.getName() + " (Premium)";
    }

    /** {@inheritDoc} */
    @Override
    public double getPrice() {
        return component.getPrice() + extraPrice;
    }
}
