package com.market.payment.paymentmethod;

/**
 * {@link PaymentMethod} implementation for Bancomat card payments.
 */
public class BancomatMethod implements PaymentMethod {
    // Card details for the Bancomat method.
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardName;
    private String address;

    /**
     * Constructor to initialize the Bancomat card details.
     *
     * @param cardNumber The card number.
     * @param expiryDate The card expiry date.
     * @param cvv The card CVV.
     * @param cardName The name on the card.
     */
    public BancomatMethod(String cardNumber, String expiryDate, String cvv, String cardName, String address) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardName = cardName;
        this.address = address;
    }

    /**
     * Executes a payment using the Bancomat card.
     *
     * @param amount The amount to be paid.
     * @return Always returns {@code true} to indicate a successful payment.
     */
    @Override
    public boolean pay(float amount) {
        // In a real scenario, payment processing logic would be here
        return true;
    }
}

