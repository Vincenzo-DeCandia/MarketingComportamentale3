package com.market.repository;

import java.util.List;

/**
 * Interface for an order repository. Defines methods for retrieving orders
 * based on user-specific criteria.
 *
 * @param <T> The type of the order entity managed by this repository.
 */
public interface IOrderRepository<T> {

    /**
     * Finds orders associated with a specific user ID.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of orders associated with the specified user ID.
     */
    List<T> findByUserId(int userId);
}

