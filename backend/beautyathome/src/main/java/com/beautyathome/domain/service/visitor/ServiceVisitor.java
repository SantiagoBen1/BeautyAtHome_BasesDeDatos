package com.beautyathome.domain.service.visitor;

import com.beautyathome.domain.service.ServiceComposite;
import com.beautyathome.domain.service.ServiceLeaf;

/**
 * Visitor contract for operations executed across the service hierarchy.
 */
public interface ServiceVisitor {

    /**
     * Executed when a leaf service is visited.
     */
    void visitServiceLeaf(ServiceLeaf leaf);

    /**
     * Executed when a composite service is visited.
     */
    void visitServiceComposite(ServiceComposite composite);
}

