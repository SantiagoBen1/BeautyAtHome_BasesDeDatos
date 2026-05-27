package com.beautyathome.domain.service.visitor;


import com.beautyathome.domain.service.ServiceComposite;
import com.beautyathome.domain.service.ServiceLeaf;

/**
 * Visitor that aggregates the price of every visited leaf.
 */
public class PricingVisitor implements ServiceVisitor {

    private double totalPrice = 0.0;

    @Override
    public void visitServiceLeaf(ServiceLeaf leaf) {
        totalPrice += leaf.getPrice();
    }

    @Override
    public void visitServiceComposite(ServiceComposite composite) {
        // Ya se visitan sus hijos desde composite.accept(...)
    }

    /**
     * @return accumulated price for the visited subtree
     */
    public double getTotalPrice() {
        return totalPrice;
    }
}
