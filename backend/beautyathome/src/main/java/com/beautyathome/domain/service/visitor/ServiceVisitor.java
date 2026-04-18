package domain.service.visitor;

import domain.service.ServiceComposite;
import domain.service.ServiceLeaf;

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

