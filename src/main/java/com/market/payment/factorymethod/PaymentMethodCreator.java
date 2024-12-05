package com.market.payment.factorymethod;

import com.market.payment.paymentmethod.PaymentMethod;

/**
 * Abstract class representing a creator for different payment methods.
 * Defines a factory method for creating a {@link PaymentMethod} object.
 * Subclasses are responsible for defining which type of payment method to create.
 */
public abstract class PaymentMethodCreator {

    /**
     * Factory method to create a specific {@link PaymentMethod}.
     * This method must be implemented by concrete creators to return the appropriate payment method.
     *
     * @return A specific {@link PaymentMethod} object.
     */
    public abstract PaymentMethod createPaymentMethod();

    /**
     * Handles the payment process by using the created {@link PaymentMethod} to make the payment.
     *
     * @param amount The amount to be paid.
     * @return {@code true} if the payment was successful, {@code false} otherwise.
     */
    public boolean pay(float amount) {
        // Create the payment method using the factory method
        PaymentMethod paymentMethod = createPaymentMethod();
        // Use the payment method to process the payment
        return paymentMethod.pay(amount);
    }
}

