package domain.service.composite;

import domain.service.ServiceComponent;

/**
 * Iterator abstraction used to traverse composite services.
 */
public interface ServiceIterator {
    /**
     * @return {@code true} when more components are available
     */
    boolean hasNext();

    /**
     * @return next component in the traversal order
     */
    ServiceComponent next();
}
