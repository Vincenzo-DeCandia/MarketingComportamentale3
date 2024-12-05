package com.market.database.mysql;

import com.market.database.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A MySQL-specific implementation of the {@link TransactionManager} interface.
 * Manages transactions for a {@link Connection} object, including starting,
 * committing, and rolling back transactions.
 */
public class MySqlTransactionManager implements TransactionManager {
    private Connection connection;

    @Override
    public void changeConnection(Connection connection) throws Exception {
        this.connection = connection;
    }


    /**
     * Constructs a new {@code MySqlTransactionManager} for managing transactions
     * on the provided database {@link Connection}.
     *
     * @param connection the {@link Connection} object to be managed; must not be null
     */
    public MySqlTransactionManager(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.connection = connection;
    }

    /**
     * Begins a transaction by setting the connection's auto-commit mode to {@code false}.
     *
     * @throws IllegalStateException if an error occurs while configuring the connection
     */
    @Override
    public void begin() {
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to begin transaction.", e);
        }
    }

    /**
     * Commits the current transaction and restores the connection's auto-commit mode.
     *
     * @throws IllegalStateException if an error occurs while committing the transaction
     */
    @Override
    public void commit() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to commit transaction.", e);
        }
    }

    /**
     * Rolls back the current transaction and restores the connection's auto-commit mode.
     *
     * @throws IllegalStateException if an error occurs while rolling back the transaction
     */
    @Override
    public void rollback() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to roll back transaction.", e);
        }
    }
}

