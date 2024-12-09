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
     * @param expiryDate The card expiry date (MM/YY format).
     * @param cvv The card CVV.
     * @param cardName The name on the card.
     * @param address The billing address associated with the card.
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
        // Simulate a Bancomat card payment process.
        // Future logic could involve integration with payment gateways or APIs.
        return true;
    }

    /**
     * Gets the billing address associated with the card.
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

    /**
     * Retrieves the cardholder's name.
     *
     * @return The name on the card.
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets a new cardholder name.
     *
     * @param cardName The new name to associate with the card.
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

}


