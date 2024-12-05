package com.market.service;

import com.market.entity.Offer;
import com.market.marketing.patternobserver.observed.OfferManager;
import com.market.marketing.patternobserver.observer.EmailNotificationObserver;
import com.market.marketing.patternobserver.observer.OfferObserver;
import com.market.marketing.patternobserver.observer.TransactionOffer;

import java.sql.Date;

/**
 * The OfferService class implements the IOfferService interface and provides the logic for creating and notifying offers.
 * It sets up the offer details and uses observers to notify relevant users about the newly created offer.
 */
public class OfferService implements IOfferService {

    /**
     * Creates a new offer with the specified details and notifies the relevant users.
     * This method sets the offer details, creates observers, and notifies the system of the new offer.
     *
     * @param name The name of the offer.
     * @param description A brief description of the offer.
     * @param discount The discount percentage or amount offered.
     * @param startDate The start date of the offer.
     * @param endDate The end date of the offer.
     * @param idUser The ID of the user creating the offer.
     * @param idProduct The ID of the product related to the offer.
     */
    @Override
    public void createAndNotifyOffer(String name, String description, float discount, Date startDate, Date endDate, int idUser, int idProduct) {
        // Create a new Offer object and set its details.
        Offer newOffer = new Offer();
        newOffer.setNameOffer(name);
        newOffer.setDescription(description);
        newOffer.setDiscount(discount);
        newOffer.setStartDate(startDate);
        newOffer.setEndDate(endDate);
        newOffer.setIdUser(idUser);
        newOffer.setIdProduct(idProduct);

        // Create the OfferManager and observers.
        OfferManager offerManager = new OfferManager();
        OfferObserver notificationObserver = new EmailNotificationObserver(offerManager);  // Observer for email notifications.
        OfferObserver transactionOffer = new TransactionOffer(offerManager);  // Observer for transaction processing.

        // Set the offer in the OfferManager to notify all observers.
        offerManager.setOffer(newOffer);
    }
}

