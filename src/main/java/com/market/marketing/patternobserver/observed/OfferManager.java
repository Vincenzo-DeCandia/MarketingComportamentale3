package com.market.marketing.patternobserver.observed;

import com.market.entity.Offer;
import com.market.marketing.patternobserver.observer.IObserver;

import java.util.ArrayList;
import java.util.List;


/**
 * The OfferManager class manages the current offer and notifies attached observers about changes to the offer.
 */
public class OfferManager {
    private final List<IObserver> observers; // List of observers monitoring the offer changes
    private Offer offer; // The current offer being managed

    /**
     * Constructs an OfferManager instance.
     * Initializes the observers list to keep track of all observers interested in the offer updates.
     */
    public OfferManager() {
        observers = new ArrayList<>();
    }

    /**
     * Attaches an observer to the OfferManager.
     * Adds an observer to the list of observers. The observer will be notified when the offer changes.
     *
     * @param observer The observer to be added to the list.
     */
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    /**
     * Detaches an observer from the OfferManager.
     * Removes an observer from the list of observers. The observer will no longer be notified of offer changes.
     *
     * @param observer The observer to be removed from the list.
     */
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers about the change in the offer.
     * This method calls the `update` method on each observer to notify them of any changes in the offer.
     */
    public void notifyObservers() {
        for (IObserver observer : observers) {
            try {
                observer.update(offer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the current offer.
     * This method returns the current offer that is being managed by the OfferManager.
     *
     * @return The current offer being managed.
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * Sets a new offer and notifies all observers of the change.
     * This method updates the offer and notifies all attached observers that the offer has changed.
     *
     * @param offer The new offer to be set.
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
        notifyObservers();
    }
}

