package com.market.payment.factorymethod;

import com.market.payment.paymentmethod.CreditCardMethod;
import com.market.payment.paymentmethod.PaymentMethod;

/**
 * Concrete creator that creates a {@link CreditCardMethod} payment method.
 */
public class CreditCardCreator extends PaymentMethodCreator {
    // Instance of the credit card payment method.
    private final CreditCardMethod creditCardMethod;

    /**
     * Constructor that initializes the {@link CreditCardMethod} with credit card details.
     *
     * @param creditCardNumber Credit card number.
     * @param creditCardExpiryDate Expiry date of the card.
     * @param creditCardCVV CVV of the card.
     */
    public CreditCardCreator(String creditCardNumber, String creditCardExpiryDate, String creditCardCVV, String address) {
        creditCardMethod = new CreditCardMethod(creditCardNumber, creditCardExpiryDate, creditCardCVV, address);
    }

    /**
     * Creates and returns a {@link CreditCardMethod}.
     *
     * @return A {@link CreditCardMethod} instance.
     */
    @Override
    public PaymentMethod createPaymentMethod() {
        return creditCardMethod;
    }
}
