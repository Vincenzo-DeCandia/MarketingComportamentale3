package com.market.marketing.patternobserver.observer;


import com.market.entity.Offer;
import com.market.exception.ExceptionScene;
import com.market.marketing.patternobserver.observed.OfferManager;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.concreteuser.User;

public class SmsNotificationObserver extends OfferObserver {

    /**
     * Constructs an OfferObserver and attaches it to the given OfferManager.
     * <p>
     * The observer is automatically added to the OfferManager's list of observers upon creation.
     *
     * @param offerManager The OfferManager to which this observer should be attached.
     */
    public SmsNotificationObserver(OfferManager offerManager) {
        super(offerManager);
    }

    @Override
    public void update(Offer offer) throws ExceptionScene {
        System.out.println("SMS notification observer");
        IRepository<User, Integer> repository = new UserRepository();
        System.out.println("Invio notifica al numero: " + repository.findById(offer.getIdUser()).getPhone());
    }
}
