package com.market.repository;

/**
 * Interface for a base user repository. Defines methods for retrieving user
 * entities based on authentication credentials.
 *
 * @param <T> The type of the user entity managed by this repository.
 */
public interface IBaseUserRepository<T> {

    /**
     * Finds a user by their email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The user matching the specified email and password, or null if no match is found.
     */
    T findByEmailPassword(String email, String password);
}

