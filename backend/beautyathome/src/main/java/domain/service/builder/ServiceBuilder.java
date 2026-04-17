package domain.service.builder;

import domain.service.ServiceComponent;

/**
 * Builder contract that constructs {@link ServiceComponent} instances step by
 * step.
 */
public interface ServiceBuilder {

    /**
     * Resets the builder state.
     */
    void reset();

    /**
     * @param name service name
     */
    void setName(String name);

    /**
     * @param description service description
     */
    void setDescription(String description);

    /**
     * @param price base price
     */
    void setPrice(double price);

    /**
     * @param durationMinutes service duration in minutes
     */
    void setDuration(int durationMinutes);

    /**
     * Adds an image reference to the service being built.
     */
    void addImage(String url);

    /**
     * @return constructed service component
     */
    ServiceComponent getResult();
}
