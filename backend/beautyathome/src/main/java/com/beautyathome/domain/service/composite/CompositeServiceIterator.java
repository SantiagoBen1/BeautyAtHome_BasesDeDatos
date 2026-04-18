package domain.service.composite;

import java.util.Iterator;
import java.util.List;

import domain.service.ServiceComponent;

/**
 * Simple iterator implementation backed by the children list of a
 * {@link domain.service.ServiceComposite}.
 */
public class CompositeServiceIterator implements ServiceIterator {

    private final Iterator<ServiceComponent> iterator;

    /**
     * @param children children collection to iterate over
     */
    public CompositeServiceIterator(List<ServiceComponent> children) {
        this.iterator = children.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public ServiceComponent next() {
        return iterator.next();
    }
}
