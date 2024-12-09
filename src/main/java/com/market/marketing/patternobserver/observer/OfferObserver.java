package com.market.marketing.patternobserver.observer;

import com.market.entity.Offer;
import com.market.exception.ExceptionScene;
import com.market.marketing.patternobserver.observed.OfferManager;

public abstract class OfferObserver implements IObserver {
    protected OfferManager offerManager; // The OfferManager that this observer is attached to

    /**
     * Constructs an OfferObserver and attaches it to the given OfferManager.
     *
     * The observer is automatically added to the OfferManager's list of observers upon creation.
     *
     * @param offerManager The OfferManager to which this observer should be attached.
     */
    public OfferObserver(OfferManager offerManager) {
        this.offerManager = offerManager;
        offerManager.attach(this);
    }

    /**
     * Abstract method to be implemented by concrete observers.
     *
     * This method is called when the offer is updated, allowing the observer to react to the change.
     * Concrete implementations should define how to handle the offer update.
     */
    public abstract void update(Offer offer) throws ExceptionScene;
}
