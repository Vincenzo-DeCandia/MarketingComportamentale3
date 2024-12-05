package com.market.repository;

import com.market.database.facade.MySqlDatabaseFacade;

import java.util.List;

import java.util.List;

/**
 * Abstract base class for a MySQL repository.
 *
 * @param <T> The type of the entity managed by this repository.
 * @param <S> The type of the entity's identifier.
 */
public abstract class MySqlRepository<T, S> implements IRepository<T, S> {

    /**
     * Facade for interacting with the MySQL database.
     * This can be used to perform custom queries or operations.
     */
    protected final MySqlDatabaseFacade mySqlDatabaseFacade = new MySqlDatabaseFacade();

    /**
     * Finds an entity by its identifier.
     *
     * @param id The identifier of the entity to find.
     * @return The entity if found, or null if not found.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    @Override
    public T findById(S id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Retrieves all entities managed by this repository.
     *
     * @return A list of all entities.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Saves an entity to the database.
     *
     * @param t The entity to save.
     * @return True if the entity was successfully saved, false otherwise.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    @Override
    public boolean save(T t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Deletes an entity from the database.
     *
     * @param t The entity to delete.
     * @return True if the entity was successfully deleted, false otherwise.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    @Override
    public boolean delete(T t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Updates an entity in the database.
     *
     * @param t The entity to update.
     * @return True if the entity was successfully updated, false otherwise.
     * @throws UnsupportedOperationException If this method is not implemented.
     */
    @Override
    public boolean update(T t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
