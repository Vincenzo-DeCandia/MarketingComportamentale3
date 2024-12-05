package com.market.entity;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * Represents a product in the system.
 * Stores details such as ID, name, category, description, quantity, price, and code.
 * Implements cloning and serialization for flexible usage.
 */
public class Product implements Cloneable, Serializable {

    private int productId;
    private String productName;
    private String productCode;
    private String productCategory;
    private String productDescription;
    private int productQuantity;
    private float productPrice;

    /**
     * Constructs a Product with specified details.
     *
     * @param productId The unique identifier of the product.
     * @param productName The name of the product.
     * @param productCategory The category of the product.
     * @param productDescription The description of the product.
     * @param productQuantity The quantity of the product available.
     * @param productPrice The price of the product.
     * @param productCode The unique code of the product.
     */
    public Product(int productId, String productName, String productCategory, String productDescription, int productQuantity, float productPrice, String productCode) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productCode = productCode;
    }

    /**
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Gets the product ID.
     *
     * @return The unique identifier of the product.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId The unique identifier to set for the product.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the product name.
     *
     * @return The name of the product.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName The name to set for the product.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the product category.
     *
     * @return The category of the product.
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * Sets the product category.
     *
     * @param productCategory The category to set for the product.
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * Gets the product description.
     *
     * @return The description of the product.
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the product description.
     *
     * @param productDescription The description to set for the product.
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * Gets the product quantity.
     *
     * @return The quantity of the product available.
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * Sets the product quantity.
     *
     * @param productQuantity The quantity to set for the product.
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * Gets the product price.
     *
     * @return The price of the product.
     */
    public float getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the product price.
     *
     * @param productPrice The price to set for the product.
     */
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Gets the product code.
     *
     * @return The unique code of the product.
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the product code.
     *
     * @param productCode The unique code to set for the product.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Creates and returns a clone of this Product.
     *
     * @return A cloned instance of this Product.
     * @throws AssertionError if cloning is not supported.
     */
    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

