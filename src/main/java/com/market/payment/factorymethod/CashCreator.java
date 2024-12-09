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
     * Constructor that initializes the {@link CashMethod} with user details.
     *
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param address The address where the payment will be made.
     */
    public CashCreator(String name, String surname, String address) {
        // Initialize the CashMethod instance with the provided details.
        cashMethod = new CashMethod(name, surname, address);
    }

    /**
     * Factory Method implementation.
     * Creates and returns a {@link CashMethod}.
     *
     * @return A {@link CashMethod} instance.
     */
    @Override
    public PaymentMethod createPaymentMethod() {
        return cashMethod;
    }
}
