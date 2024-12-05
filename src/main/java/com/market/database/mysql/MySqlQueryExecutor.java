package com.market.database.mysql;

import com.market.database.QueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * {@link QueryExecutor} implementation for executing SQL queries on a MySQL database.
 */
public class MySqlQueryExecutor implements QueryExecutor<List<Map<String, Object>>> {
    // The database connection used to execute queries.
    private Connection connection;

    /**
     * Constructor to initialize the MySQL query executor with a connection.
     *
     * @param connection The connection to the MySQL database.
     * @throws IllegalArgumentException if the connection is null.
     */
    public MySqlQueryExecutor(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.connection = connection;
    }

    /**
     * Executes a SELECT query and returns the result as a list of rows.
     * Each row is represented as a map with column names as keys and column values as values.
     *
     * @param query The SQL SELECT query to execute.
     * @param parameters The parameters to bind to the query.
     * @return A list of rows, where each row is a map of column name-value pairs.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public List<Map<String, Object>> executeQuery(String query, List<Object> parameters) throws SQLException {
        validateQuery(query); // Ensure the query is valid.
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            bindParameters(preparedStatement, parameters); // Bind query parameters.
            List<Map<String, Object>> rows = new ArrayList<>();

            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        row.put(resultSetMetaData.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }
            return rows; // Return the list of result rows.
        }
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE query and returns the number of affected rows.
     *
     * @param query The SQL query to execute.
     * @param parameters The parameters to bind to the query.
     * @return The number of rows affected by the query.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public int executeUpdate(String query, List<Object> parameters) throws SQLException {
        validateQuery(query); // Ensure the query is valid.
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            bindParameters(preparedStatement, parameters); // Bind query parameters.
            return preparedStatement.executeUpdate(); // Execute and return the number of affected rows.
        }
    }

    @Override
    public void changeConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    /**
     * Validates the query to ensure it is not null or empty.
     *
     * @param query The SQL query string to validate.
     * @throws IllegalArgumentException if the query is null or empty.
     */
    private void validateQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty.");
        }
    }

    /**
     * Binds the provided parameters to the prepared statement.
     *
     * @param preparedStatement The prepared statement to bind parameters to.
     * @param parameters The parameters to bind.
     * @throws SQLException if an error occurs while binding parameters.
     */
    private void bindParameters(PreparedStatement preparedStatement, List<Object> parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i)); // Bind each parameter.
            }
        }
    }
}


