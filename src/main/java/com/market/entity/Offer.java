package com.market.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents an offer in the system.
 * Contains details such as the offer name, associated user and product IDs, discount,
 * validity period, and a description.
 */
public class Offer implements Serializable {

    private String nameOffer;
    private int idUser;
    private int idProduct;
    private float discount;
    private Date startDate;
    private Date endDate;
    private String description;

    /**
     * Default constructor for the Offer class.
     */
    public Offer() {}

    /**
     * Gets the name of the offer.
     *
     * @return The name of the offer.
     */
    public String getNameOffer() {
        return nameOffer;
    }

    /**
     * Sets the name of the offer.
     *
     * @param nameOffer The name of the offer.
     */
    public void setNameOffer(String nameOffer) {
        this.nameOffer = nameOffer;
    }

    /**
     * Gets the ID of the user associated with the offer.
     *
     * @return The user ID.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets the ID of the user associated with the offer.
     *
     * @param idUser The user ID.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Gets the ID of the product associated with the offer.
     *
     * @return The product ID.
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * Sets the ID of the product associated with the offer.
     *
     * @param idProduct The product ID.
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * Gets the discount percentage of the offer.
     *
     * @return The discount percentage.
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * Sets the discount percentage of the offer.
     *
     * @param discount The discount percentage.
     */
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * Gets the start date of the offer.
     *
     * @return The start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the offer.
     *
     * @param startDate The start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the offer.
     *
     * @return The end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the offer.
     *
     * @param endDate The end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the description of the offer.
     *
     * @return The description of the offer.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the offer.
     *
     * @param description The description of the offer.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
