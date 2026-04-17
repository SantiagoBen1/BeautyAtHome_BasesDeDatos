package domain.service;

import java.util.List;

import domain.service.image.ImageReference;
import domain.service.visitor.ServiceVisitor;

    /**
     * Concrete leaf service that holds intrinsic service details such as
     * description, price, duration, images, and an optional category.
     */
    public class ServiceLeaf implements ServiceComponent {

        private final String name;
        private final String description;
        private final double price;
        private final int durationMin;
        private final List<ImageReference> images;
        private Category category;

        /**
         * @param name display name
         * @param description service description
         * @param price base price
         * @param durationMin duration in minutes
         * @param images associated images
         */
        public ServiceLeaf(String name,
                           String description,
                           double price,
                           int durationMin,
                           List<ImageReference> images) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.durationMin = durationMin;
            this.images = images;
        }

        /**
         * Assigns the category defining the type of service.
         *
         * @param category category reference
         */
        public void setCategory(Category category) {
            this.category = category;
        }

        /**
         * @return category associated with the service
         */
        public Category getCategory() {
            return category;
        }

        /** {@inheritDoc} */
        @Override
        public String getName() { return name; }

        /** {@inheritDoc} */
        @Override
        public String getDescription() { return description; }

        /** {@inheritDoc} */
        @Override
        public double getPrice() { return price; }

        /** {@inheritDoc} */
        @Override
        public int getDurationMin() { return durationMin; }

        /** {@inheritDoc} */
        @Override
        public List<ImageReference> getImages() { return images; }

        /** {@inheritDoc} */
        @Override
        public void execute() {
            // Lógica de ejecución de un servicio simple
        }

        /** {@inheritDoc} */
        @Override
        public void accept(ServiceVisitor visitor) {
            visitor.visitServiceLeaf(this);
        }
    }
