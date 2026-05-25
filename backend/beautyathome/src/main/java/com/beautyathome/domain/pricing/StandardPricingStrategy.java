package com.beautyathome.domain.pricing;


import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.service.ServiceComponent;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;

/**
 * Estrategia por defecto que mantiene el precio sin modificaciones.
 */
@Component
@Primary
public class StandardPricingStrategy implements PricingStrategy {

    /** {@inheritDoc} */
    @Override
    public double calculatePrice(double basePrice, Client client, ServiceComponent service) {
        return basePrice;
    }
}
