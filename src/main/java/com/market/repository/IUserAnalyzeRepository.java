package com.market.repository;

import com.market.user.concreteuser.User;

import java.util.List;

/**
 * Interface for user analysis repository.
 * Provides methods for retrieving users based on specific criteria, such as the number of orders or random selection.
 */
public interface IUserAnalyzeRepository {

    /**
     * Searches for users who have placed a specific number of orders.
     *
     * @param orderNumber The exact number of orders to filter users by.
     * @return A list of users who have placed the specified number of orders.
     */
    List<User> searchByOrderNumber(int orderNumber);

    /**
     * Retrieves a random subset of users.
     *
     * @param count The number of users to retrieve randomly.
     * @return A list containing the specified number of randomly selected users.
     */
    List<User> randomUser(int count);
}

