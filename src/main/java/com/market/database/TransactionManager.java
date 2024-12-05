package com.market.database;

import java.sql.Connection;

/**
 * Interface for managing database transactions.
 */
public interface TransactionManager {

    /**
     * Starts a new transaction.
     */
    public void begin();

    /**
     * Commits the current transaction, making all changes permanent.
     */
    public void commit();

    /**
     * Rolls back the current transaction, discarding any changes made.
     */
    public void rollback();

    void changeConnection(Connection connection) throws Exception;
}
