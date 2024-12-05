package com.market.database;

import java.sql.Connection;
import java.util.List;

/**
 * Interface for executing SQL queries and updates.
 *
 * @param <S> The type of object returned by the query execution (e.g., List, Map, etc.)
 */
public interface QueryExecutor<S> {

    /**
     * Executes a SELECT query and returns the result.
     *
     * @param query The SQL SELECT query.
     * @param parameters The parameters to bind to the query.
     * @return The result of the query execution, of type {@link S}.
     * @throws Exception if a database access error occurs.
     */
    public S executeQuery(String query, List<Object> parameters) throws Exception;

    /**
     * Executes an INSERT, UPDATE, or DELETE query.
     *
     * @param query The SQL query to execute.
     * @param parameters The parameters to bind to the query.
     * @return The number of rows affected by the query.
     * @throws Exception if a database access error occurs.
     */
    public int executeUpdate(String query, List<Object> parameters) throws Exception;

    void changeConnection(Connection connection) throws Exception;
}
