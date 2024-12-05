package com.market.service;

import java.sql.Date;

/**
 * The IOfferService interface defines the contract for services responsible for creating and notifying offers.
 * Implementations of this interface should provide the logic for creating an offer and notifying relevant users.
 */
public interface IOfferService {

    /**
     * Creates a new offer with the specified details and notifies the relevant users about the offer.
     *
     * @param name The name of the offer.
     * @param description A brief description of the offer.
     * @param discount The discount percentage or amount offered.
     * @param startDate The start date of the offer.
     * @param endDate The end date of the offer.
     * @param idUser The ID of the user who is creating the offer.
     * @param idProduct The ID of the product for which the offer is being created.
     */
    void createAndNotifyOffer(String name, String description, float discount, Date startDate, Date endDate, int idUser, int idProduct);
}
