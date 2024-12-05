package com.market.user;


import com.market.datastorage.DataStorage;
import com.market.datastorage.SessionDataStorage;

import java.io.Serializable;

/**
 * Abstract class representing a base user in the system.
 * Provides common fields and methods for user management.
 */
public abstract class BaseUser implements Serializable {

    // User's email address
    private String email;

    // User's password
    private String password;

    // User's role in the system
    private String role;

    // Unique identifier for the user
    private int idUser;

    // User's first name
    private String name;

    // User's last name
    private String surname;

    /**
     * Default constructor.
     */
    public BaseUser() {}

    /**
     * Constructor with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     */
    public BaseUser(String email, String password) {
        this.email = email;
        this.password = password;
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
    public BaseUser(String name, String email, String password, String surname, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.role = role;
    }

    /**
     * Constructor with username, password, and role.
     *
     * @param username the user's email (used as a username)
     * @param password the user's password
     * @param role the user's role
     */
    public BaseUser(String username, String password, String role) {
        this.email = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's first name.
     *
     * @return the user's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's first name.
     *
     * @param name the user's first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's last name.
     *
     * @return the user's last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the user's last name.
     *
     * @param surname the user's last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's role.
     *
     * @return the user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the user's role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the user's unique ID.
     *
     * @return the user's ID
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets the user's unique ID.
     *
     * @param idUser the user's ID
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Logs out the user by clearing the session data.
     */
    public void logout() {
        DataStorage dataStorage = new SessionDataStorage("session.txt");
        dataStorage.clear();
    }
}

