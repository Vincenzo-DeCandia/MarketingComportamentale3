package com.market.payment.paymentmethod;

/**
 * {@link PaymentMethod} implementation for credit card payments.
 */
public class CreditCardMethod implements PaymentMethod {
    // Credit card details
    private String creditCardNumber;
    private String creditCardExpiration;
    private String creditCardCVV;
    private String address;

    /**
     * Constructor to initialize credit card details.
     *
     * @param creditCardNumber The credit card number.
     * @param creditCardExpiration The credit card expiration date.
     * @param creditCardCVV The credit card CVV.
     * @param address The billing address associated with the credit card.
     */
    public CreditCardMethod(String creditCardNumber, String creditCardExpiration, String creditCardCVV, String address) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiration = creditCardExpiration;
        this.creditCardCVV = creditCardCVV;
        this.address = address;
    }

    /**
     * Executes a credit card payment.
     *
     * @param amount The amount to be paid.
     * @return Always returns {@code true} to indicate a successful payment.
     */
    @Override
    public boolean pay(float amount) {
        return true;
    }

    /**
     * Gets the billing address associated with the credit card.
     *
     * @return The billing address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets a new billing address.
     *
     * @param address The new billing address.
     */
    public void setAddress(String address) {
        this.address = address;
    }


}

