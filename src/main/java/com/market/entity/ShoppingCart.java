package com.market.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart containing products and associated offers.
 * Allows adding/removing products, updating quantities, and calculating the total price
 * considering any applicable offers.
 */
public class ShoppingCart implements Serializable {

    private final List<Product> products;
    private final List<Offer> productOffers;

    /**
     * Constructs an empty shopping cart.
     */
    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.productOffers = new ArrayList<>();
    }

    /**
     * Adds a product to the shopping cart.
     * If the product already exists, its quantity is updated.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        for (Product p : this.products) {
            if (p.getProductId() == product.getProductId()) {
                p.setProductQuantity(p.getProductQuantity() + product.getProductQuantity());
                return;
            }
        }
        this.products.add(product);
    }

    /**
     * Removes a product from the shopping cart.
     *
     * @param product The product to be removed.
     */
    public void removeProduct(Product product) {
        this.products.removeIf(p -> p.getProductId() == product.getProductId());
    }

    /**
     * Gets the list of products in the shopping cart.
     *
     * @return A list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Updates the quantity of a specific product in the cart.
     *
     * @param product The product whose quantity is to be updated.
     * @param quantity The new quantity to set.
     */
    public void updateProductQuantity(Product product, int quantity) {
        for (Product p : this.products) {
            if (p.getProductId() == product.getProductId()) {
                p.setProductQuantity(quantity);
                return;
            }
        }
    }

    /**
     * Adds an offer to the shopping cart.
     * The offer will apply to relevant products during total price calculation.
     *
     * @param offer The offer to be added.
     */
    public void addProductOffer(Offer offer) {
        productOffers.add(offer);
    }

    /**
     * Calculates the total price of the products in the cart, considering any applicable offers.
     *
     * @return The total price of all products in the cart after applying offers.
     */
    public float calculateTotal() {
        float total = 0;
        for (Product p : this.products) {
            float price = p.getProductPrice();
            for (Offer o : this.productOffers) {
                if (o.getIdProduct() == p.getProductId()) {
                    price -= (o.getDiscount() / 100.0f) * price;
                }
            }
            total += p.getProductQuantity() * price;
        }
        return total;
    }
}
