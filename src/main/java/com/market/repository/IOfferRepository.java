package com.market.repository;

import com.market.entity.Offer;

import java.util.List;

/**
 * Interface for an offer repository. Defines methods for retrieving offers
 * based on user and product-specific criteria.
 */
public interface IOfferRepository {

    /**
     * Finds all offers associated with a specific user ID.
     *
     * @param userId The ID of the user whose offers are to be retrieved.
     * @return A list of offers associated with the specified user ID.
     */
    List<Offer> findByUserId(int userId);

    /**
     * Finds a specific offer associated with a product ID.
     *
     * @param productId The ID of the product for which the offer is to be retrieved.
     * @return The offer associated with the specified product ID, or null if no offer is found.
     */
    Offer findByProductId(int productId);
}

