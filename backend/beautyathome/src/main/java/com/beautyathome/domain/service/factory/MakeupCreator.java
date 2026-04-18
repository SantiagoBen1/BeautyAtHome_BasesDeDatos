package com.beautyathome.domain.service.factory;

import java.util.List;

import com.beautyathome.domain.service.MakeupService;
import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.image.ImageReference;

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
