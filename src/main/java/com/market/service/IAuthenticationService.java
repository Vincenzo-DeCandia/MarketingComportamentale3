package com.market.service;

/**
 * Interface for authentication services.
 * Provides methods for user login.
 */
public interface IAuthenticationService {
    /**
     * Authenticates a user based on their username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return {@code true} if authentication is successful, {@code false} otherwise.
     */
    boolean login(String username, String password);
}

