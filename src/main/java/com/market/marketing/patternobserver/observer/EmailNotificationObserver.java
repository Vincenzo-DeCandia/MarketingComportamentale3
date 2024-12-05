package com.market.marketing.patternobserver.observer;

import com.market.entity.Offer;
import com.market.exception.ExceptionScene;
import com.market.marketing.patternobserver.observed.OfferManager;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.BaseUser;

public class EmailNotificationObserver extends OfferObserver {

    /**
     * Constructs an EmailNotificationObserver and attaches it to the given OfferManager.
     *
     * This constructor calls the parent constructor to attach the observer to the OfferManager.
     *
     * @param offerManager The OfferManager to which this observer should be attached.
     */
    public EmailNotificationObserver(OfferManager offerManager) {
        super(offerManager);
    }

    /**
     * Updates the EmailNotificationObserver based on changes in the OfferManager.
     *
     * This method retrieves the current offer from the OfferManager and simulates sending an email
     * notification to the user associated with the offer. It retrieves the user from the UserRepository
     * and prints out the email notification process.
     */
    @Override
    public void update() throws ExceptionScene {
        Offer offer = offerManager.getOffer();
        System.out.println("Notification observer received: " + offer);
        UserRepository userRepository = new UserRepository();
        BaseUser user = userRepository.findById(offer.getIdUser());
        System.out.println("Send email to " + user.getEmail());
    }
}

