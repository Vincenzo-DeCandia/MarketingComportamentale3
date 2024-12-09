package com.market.payment.paymentmethod;

/**
 * Class representing a cash payment method.
 */
public class CashMethod implements PaymentMethod {
    private String name;
    private String surname;
    private String address;

    /**
     * Constructor to initialize the Cash payment details.
     *
     * @param name The user's name.
     * @param surname The user's surname.
     * @param address The user's address where payment is associated.
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
        // Simulates a successful cash payment.
        // Future logic could include generating a receipt or recording the transaction.
        return true;
    }

    /**
     * Get the payer's full name.
     *
     * @return A string representing the full name of the payer.
     */
    public String getFullName() {
        return name + " " + surname;
    }

    /**
     * Gets the address associated with the cash payment.
     *
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address for the payment.
     *
     * @param address The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

