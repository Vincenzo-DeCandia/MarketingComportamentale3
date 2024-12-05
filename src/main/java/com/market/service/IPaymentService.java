package com.market.service;

import com.market.exception.ExceptionScene;

import java.util.List;

/**
 * The IPaymentService interface defines the contract for services responsible for processing payments.
 * Implementations of this interface should provide the logic for executing different types of payments.
 */
public interface IPaymentService {

    /**
     * Executes a payment based on the specified payment type and amount.
     *
     * @param type The type of the payment (e.g., credit card, PayPal, etc.).
     * @param amount The amount of money to be paid.
     * @param paymentData A list of payment-specific data (e.g., credit card details, account info).
     */
    void executePayment(String type, float amount, List<String> paymentData) throws ExceptionScene;
}
