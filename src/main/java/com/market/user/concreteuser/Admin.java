package com.market.user.concreteuser;

import com.market.user.BaseUser;


/**
 * Class representing an administrator in the system.
 * Extends the BaseUser class with admin-specific constructors.
 */
public class Admin extends BaseUser {

    /**
     * Constructor with username, password, and role.
     *
     * @param username the admin's email (used as a username)
     * @param password the admin's password
     * @param role the admin's role
     */
    public Admin(String username, String password, String role) {
        super(username, password, role);
    }

    /**
     * Default constructor.
     */
    public Admin() {
        super();
    }

    /**
     * Constructor with email and password.
     * Automatically sets the role to "admin".
     *
     * @param email the admin's email
     * @param password the admin's password
     */
    public Admin(String email, String password) {
        super(email, password);
        this.setRole("admin");
    }
}
