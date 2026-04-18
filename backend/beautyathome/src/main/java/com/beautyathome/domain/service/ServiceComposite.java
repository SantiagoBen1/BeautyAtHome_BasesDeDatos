package domain.service;


import java.util.ArrayList;
import java.util.List;

import domain.service.composite.CompositeServiceIterator;
import domain.service.composite.ServiceIterator;
import domain.service.image.ImageReference;
import domain.service.visitor.ServiceVisitor;

        /**
         * Composite node that groups multiple services and aggregates their price,
         * duration, images, and execution.
         */
        public class ServiceComposite implements ServiceComponent {

            private String name;
            private String description;
            private List<ServiceComponent> children = new ArrayList<>();

            /**
             * @param name composite name
             * @param description composite description
             */
            public ServiceComposite(String name, String description) {
                this.name = name;
                this.description = description;
            }

            /**
             * Adds a child component.
             *
             * @param component component to add
             */
            public void add(ServiceComponent component) {
                children.add(component);
            }

            /**
             * Removes a child component.
             *
             * @param component component to remove
             */
            public void remove(ServiceComponent component) {
                children.remove(component);
            }

            /**
             * Creates an iterator to traverse the composite children.
             *
             * @return iterator over child components
             */
            public ServiceIterator createIterator() {
                return new CompositeServiceIterator(children);
            }

            /** {@inheritDoc} */
            @Override
            public String getName() { return name; }

            /** {@inheritDoc} */
            @Override
            public String getDescription() { return description; }

            /** {@inheritDoc} */
            @Override
            public double getPrice() {
                return children.stream().mapToDouble(ServiceComponent::getPrice).sum();
            }

            /** {@inheritDoc} */
            @Override
            public int getDurationMin() {
                return children.stream().mapToInt(ServiceComponent::getDurationMin).sum();
            }

            /** {@inheritDoc} */
            @Override
            public List<ImageReference> getImages() {
                List<ImageReference> all = new ArrayList<>();
                for (ServiceComponent child : children) {
                    all.addAll(child.getImages());
                }
                return all;
            }

            /** {@inheritDoc} */
            @Override
            public void execute() {
                for (ServiceComponent child : children) {
                    child.execute();
                }
            }

            /** {@inheritDoc} */
            @Override
            public void accept(ServiceVisitor visitor) {
                visitor.visitServiceComposite(this);
                for (ServiceComponent child : children) {
                    child.accept(visitor);
                }
            }
        }
    
