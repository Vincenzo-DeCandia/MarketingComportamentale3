package com.market.payment.factorymethod;

import com.market.payment.paymentmethod.CashMethod;
import com.market.payment.paymentmethod.PaymentMethod;

/**
 * Concrete creator that creates a {@link CashMethod} payment method.
 */
public class CashCreator extends PaymentMethodCreator {
    // Instance of the cash payment method.
    private final CashMethod cashMethod;

    /**
     * Constructor that initializes the {@link CashMethod} with fiscal code and email.
     *
     * @param fiscalCode User's fiscal code.
     * @param email User's email.
     */
    public CashCreator(String name, String surname, String address) {
        cashMethod = new CashMethod(name, surname, address);
    }

    /**
     * Creates and returns a {@link CashMethod}.
     *
     * @return A {@link CashMethod} instance.
     */
    @Override
    public PaymentMethod createPaymentMethod() {
        return cashMethod;
    }
}
