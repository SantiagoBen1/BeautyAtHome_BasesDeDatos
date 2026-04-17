package domain.pricing;


import domain.client.Client;
import domain.service.ServiceComponent;

/**
 * Estrategia por defecto que mantiene el precio sin modificaciones.
 */
public class StandardPricingStrategy implements PricingStrategy {

    /** {@inheritDoc} */
    @Override
    public double calculatePrice(double basePrice, Client client, ServiceComponent service) {
        return basePrice;
    }
}
