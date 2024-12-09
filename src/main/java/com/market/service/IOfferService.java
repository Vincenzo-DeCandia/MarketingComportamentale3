package com.market.service;

import com.market.exception.ExceptionScene;

import java.sql.Date;

/**
 * Service interface for managing offers.
 * Provides methods for creating offers and notifying relevant users.
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
     * @throws ExceptionScene if there are issues with creating the offer or notifying users.
     */
    void createAndNotifyOffer(String name, String description, float discount, Date startDate, Date endDate, int idUser, int idProduct) throws ExceptionScene;
}
