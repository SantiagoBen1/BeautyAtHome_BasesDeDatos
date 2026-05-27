package com.beautyathome.domain.pricing;

import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.service.ServiceComponent;

/**
 * Estrategia que permite ajustar el precio final en función del contexto.
 */
public interface PricingStrategy {

    /**
     * Calcula el precio definitivo para un servicio reservado.
     *
     * @param basePrice precio base sin descuentos o recargos
     * @param client    cliente que solicita el servicio
     * @param service   servicio concreto que se va a ejecutar
     * @return monto final a cobrar
     */
    double calculatePrice(double basePrice, Client client, ServiceComponent service);
}
