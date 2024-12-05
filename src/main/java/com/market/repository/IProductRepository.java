package com.market.repository;

import java.util.List;

import java.util.List;

/**
 * Interface for a product repository. Defines methods for searching products
 * based on various criteria such as name, category, and code.
 *
 * @param <T> The type of the product entity managed by this repository.
 */
public interface IProductRepository<T> {

    /**
     * Searches for products by their name.
     *
     * @param name The name of the product(s) to search for.
     * @return A list of products matching the specified name.
     */
    List<T> searchByName(String name);

    /**
     * Searches for products by their category.
     *
     * @param category The category of the product(s) to search for.
     * @return A list of products matching the specified category.
     */
    List<T> searchByCategory(String category);

    /**
     * Searches for a product by its unique code.
     *
     * @param code The code of the product to search for.
     * @return The product matching the specified code, or null if not found.
     */
    T searchByCode(String code);
}

