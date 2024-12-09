package com.market.database.facade;

import java.util.List;
import java.util.Map;

/**
 * The IDatabaseFacade interface provides a simplified abstraction for interacting
 * with a database. It hides the complexity of executing queries and managing
 * transactions.
 */
public interface IDatabase {

    /**
     * Fetches data from the database based on the provided query and parameters.
     *
     * @param query The SQL query string to execute.
     * @param parameters Optional parameters to bind to the query (e.g., for prepared statements).
     * @return A list of maps, where each map represents a row from the query result,
     *         with column names as keys and corresponding values as map values.
     */
    List<Map<String, Object>> fetchData(String query, Object... parameters);

    /**
     * Executes a transaction containing multiple queries.
     *
     * @param query A map where keys are SQL query strings and values are lists of parameters
     *              to bind to those queries. Each entry represents a single query to be
     *              executed as part of the transaction.
     * @return True if the transaction was successful, false otherwise.
     */
    boolean executeTransaction(Map<String, List<Object>> query);
}

