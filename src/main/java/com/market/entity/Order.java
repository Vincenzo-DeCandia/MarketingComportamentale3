package com.market.entity;

import com.market.user.BaseUser;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Represents an order in the system.
 * Stores details such as the user, products, total price, payment method, order status, and delivery address.
 */
public class Order implements Serializable {

    private BaseUser baseUser;
    private List<Product> products;
    private Integer orderId;
    private Float totalPrice;
    private String paymentMethod;
    private Date dateOrder;
    private Character status;
    private String address;

    /**
     * Constructs an Order with a list of products.
     *
     * @param products The list of products included in the order.
     */
    public Order(List<Product> products) {
        this.products = products;
    }

    /**
     * Gets the list of products in the order.
     *
     * @return A list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products in the order.
     *
     * @param products A list of products to be set.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Gets the ID of the order.
     *
     * @return The order ID.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets the ID of the order.
     *
     * @param orderId The ID to set for the order.
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the user associated with the order.
     *
     * @return The user who placed the order.
     */
    public BaseUser getUser() {
        return baseUser;
    }

    /**
     * Sets the user associated with the order.
     *
     * @param baseUser The user to associate with the order.
     */
    public void setUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    /**
     * Gets the total price of the order.
     *
     * @return The total price of the order.
     */
    public Float getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice The total price to set.
     */
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the payment method used for the order.
     *
     * @return The payment method as a string.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method for the order.
     *
     * @param paymentMethod The payment method to set.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the date the order was placed.
     *
     * @return The order date.
     */
    public Date getDateOrder() {
        return dateOrder;
    }

    /**
     * Sets the date the order was placed.
     *
     * @param dateOrder The date to set for the order.
     */
    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    /**
     * Gets the status of the order.
     *
     * @return The order status
     */
    public Character getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status The status to set for the order.
     */
    public void setStatus(Character status) {
        this.status = status;
    }

    /**
     * Gets the delivery address for the order.
     *
     * @return The delivery address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the delivery address for the order.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

