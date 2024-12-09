package com.market.payment.paymentmethod;

/**
 * Interface representing a payment method.
 */
public interface PaymentMethod {

    /**
     * Executes a payment using the specified method.
     *
     * @param amount The amount to be paid.
     * @return {@code true} if the payment is successful, {@code false} otherwise.
     */
    boolean pay(float amount);
}
