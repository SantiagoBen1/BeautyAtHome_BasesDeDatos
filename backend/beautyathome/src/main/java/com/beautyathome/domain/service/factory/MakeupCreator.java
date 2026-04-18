package domain.service.factory;

import java.util.List;

import domain.service.MakeupService;
import domain.service.ServiceComponent;
import domain.service.image.ImageReference;

/**
 * Factory that produces {@link MakeupService} instances.
 */
public class MakeupCreator extends ServiceCreator {

    @Override
    protected ServiceComponent instantiate(String name,
                                           String description,
                                           double price,
                                           int duration,
                                           List<ImageReference> images) {
        return new MakeupService(name, description, price, duration, images);
    }
}
