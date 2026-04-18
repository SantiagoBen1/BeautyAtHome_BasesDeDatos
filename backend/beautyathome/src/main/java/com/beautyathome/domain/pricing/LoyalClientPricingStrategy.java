package domain.pricing;


import domain.client.Client;
import domain.service.ServiceComponent;

/**
 * Aplica un descuento fijo a clientes con alta recurrencia.
 */
public class LoyalClientPricingStrategy implements PricingStrategy {

    /** {@inheritDoc} */
    @Override
    public double calculatePrice(double basePrice, Client client, ServiceComponent service) {
        // Ejemplo: 10% de descuento
        return basePrice * 0.9;
    }
}
