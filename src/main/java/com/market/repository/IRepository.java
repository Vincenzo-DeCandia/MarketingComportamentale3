package com.market.repository;

import java.util.List;

import java.util.List;

/**
 * Interface for a generic repository.
 *
 * @param <T> The type of the entity managed by this repository.
 * @param <S> The type of the entity's identifier.
 */
public interface IRepository<T, S> {

    /**
     * Finds an entity by its identifier.
     *
     * @param id The identifier of the entity to find.
     * @return The entity if found, or null if not found.
     */
    T findById(S id);

    /**
     * Retrieves all entities managed by this repository.
     *
     * @return A list of all entities.
     */
    List<T> findAll();

    /**
     * Saves an entity to the repository.
     *
     * @param t The entity to save.
     * @return True if the entity was successfully saved, false otherwise.
     */
    boolean save(T t);

    /**
     * Deletes an entity from the repository.
     *
     * @param t The entity to delete.
     * @return True if the entity was successfully deleted, false otherwise.
     */
    boolean delete(T t);

    /**
     * Updates an entity in the repository.
     *
     * @param t The entity to update.
     * @return True if the entity was successfully updated, false otherwise.
     */
    boolean update(T t);
}
