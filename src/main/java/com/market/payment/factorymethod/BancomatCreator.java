package com.market.payment.factorymethod;

import com.market.payment.paymentmethod.BancomatMethod;
import com.market.payment.paymentmethod.PaymentMethod;

/**
 * Concrete creator that creates a {@link BancomatMethod} payment method.
 */
public class BancomatCreator extends PaymentMethodCreator {
    // Instance of the bancomat payment method.
    private final BancomatMethod bancomatMethod;

    /**
     * Constructor that initializes the {@link BancomatMethod} with card details.
     *
     * @param cardNumber The card number.
     * @param expiryDate The card expiry date.
     * @param cvv The card CVV.
     * @param cardName The name on the card.
     */
    public BancomatCreator(String cardNumber, String expiryDate, String cvv, String cardName, String address) {
        bancomatMethod = new BancomatMethod(cardNumber, expiryDate, cvv, cardName, address);
    }

    /**
     * Creates and returns a {@link BancomatMethod}.
     *
     * @return A {@link BancomatMethod} instance.
     */
    @Override
    public PaymentMethod createPaymentMethod() {
        return bancomatMethod;
    }
}

