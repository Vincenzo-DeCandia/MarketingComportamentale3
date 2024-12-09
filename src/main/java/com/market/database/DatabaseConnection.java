package com.market.database;

import java.sql.Connection;

/**
 * Interface for establishing a database connection.
 */
public interface DatabaseConnection {

    /**
     * Retrieves the database connection.
     *
     * @return The database connection.
     * @throws Exception if a database access error occurs or the connection is unavailable.
     */
    Connection getConnection() throws Exception;
}
