package com.market.user.concreteuser;

import com.market.entity.ShoppingCart;
import com.market.user.BaseUser;

/**
 * Class representing a standard user in the system.
 * Extends the BaseUser class with additional functionality for a shopping cart.
 */
public class User extends BaseUser {

    // The shopping cart associated with the user
    private ShoppingCart shoppingCart;

    /**
     * Default constructor.
     */
    public User() {
        super();
    }

    /**
     * Constructor with name, email, password, surname, and role.
     *
     * @param name the user's first name
     * @param email the user's email
     * @param password the user's password
     * @param surname the user's last name
     * @param role the user's role
     */
    public User(String name, String email, String password, String surname, String role) {
        super(name, email, password, surname, role);
    }

    /**
     * Constructor with email and password.
     * Automatically sets the role to "user".
     *
     * @param email the user's email
     * @param password the user's password
     */
    public User(String email, String password) {
        super(email, password);
        this.setRole("user");
    }

    /**
     * Constructor with username, password, and role.
     *
     * @param username the user's email (used as a username)
     * @param password the user's password
     * @param role the user's role
     */
    public User(String username, String password, String role) {
        super(username, password, role);
    }

    /**
     * Sets the user's shopping cart.
     *
     * @param shoppingCart the ShoppingCart object to associate with the user
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Gets the user's shopping cart.
     *
     * @return the ShoppingCart object associated with the user
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}

