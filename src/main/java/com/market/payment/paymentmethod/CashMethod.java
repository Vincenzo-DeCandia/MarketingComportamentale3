package com.market.payment.paymentmethod;

/**
 * {@link PaymentMethod} implementation for cash payments.
 */
public class CashMethod implements PaymentMethod {
    private String name;
    private String surname;
    private String address;

    /**
     * Constructor to initialize the Cash payment details.
     *
     * @param fiscalCode The user's fiscal code.
     * @param email The user's email.
     */
    public CashMethod(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    /**
     * Executes a cash payment.
     *
     * @param amount The amount to be paid.
     * @return Always returns {@code true} to indicate a successful payment.
     */
    @Override
    public boolean pay(float amount) {
        // In a real scenario, cash payment processing logic would be here
        return true;
    }
}
