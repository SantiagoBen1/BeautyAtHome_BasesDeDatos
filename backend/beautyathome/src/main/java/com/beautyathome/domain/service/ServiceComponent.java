package com.beautyathome.domain.service;


import java.util.List;

import com.beautyathome.domain.service.image.ImageReference;
import com.beautyathome.domain.service.visitor.ServiceVisitor;

/**
 * Component in the composite/decorator hierarchy that represents either a
 * simple service or a composite of services.
 */
public interface ServiceComponent {

    /**
     * @return display name of the service
     */
    String getName();

    /**
     * @return textual description of the service
     */
    String getDescription();

    /**
     * @return base price of the service
     */
    double getPrice();

    /**
     * @return expected duration in minutes
     */
    int getDurationMin();

    /**
     * @return associated media references
     */
    List<ImageReference> getImages();

    /**
     * Executes the service (placeholder hook for future logic).
     */
    void execute();

    /**
     * Accepts a visitor to perform cross-cutting logic.
     *
     * @param visitor visitor to accept
     */
    void accept(ServiceVisitor visitor);
}
