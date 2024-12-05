package com.market.marketing.patternobserver.observer;

import com.market.entity.Offer;
import com.market.exception.ExceptionScene;
import com.market.marketing.patternobserver.observed.OfferManager;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.OfferRepository;

public class TransactionOffer extends OfferObserver {

    /**
     * Constructs a TransactionOffer and attaches it to the given OfferManager.
     *
     * This constructor calls the parent constructor to attach the observer to the OfferManager.
     *
     * @param offerManager The OfferManager to which this observer should be attached.
     */
    public TransactionOffer(OfferManager offerManager) {
        super(offerManager);
    }

    /**
     * Updates the TransactionOffer based on changes in the OfferManager.
     *
     * This method retrieves the current offer from the OfferManager and saves it using an OfferRepository.
     * The save method of the repository is invoked to persist the updated offer.
     */
    @Override
    public void update() throws ExceptionScene {
        Offer offer = this.offerManager.getOffer();
        IRepository<Offer, Integer> offerIRepository = new OfferRepository();
        if (!offerIRepository.save(offer)) {
            throw new ExceptionScene("Errore durante il salvataggio dell'offerta nel database");
        }
    }
}

