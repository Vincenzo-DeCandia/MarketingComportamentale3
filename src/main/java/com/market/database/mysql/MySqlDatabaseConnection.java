package com.market.database.mysql;

import com.market.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton implementation of a MySQL database connection.
 */
public class MySqlDatabaseConnection implements DatabaseConnection {
    // Singleton instance, volatile for thread-safety.
    private static MySqlDatabaseConnection instance;
    private Connection connection;

    // Database connection details (consider securing these).
    private final String url = "jdbc:mysql://localhost:3306/marketapp";
    private final String user = "root"; // Use environment variables for sensitive data.
    private final String password = "admin2001"; // Avoid hardcoding sensitive information.

    /**
     * Private constructor to initialize the connection.
     *
     * @throws SQLException if the connection cannot be established.
     */
    private MySqlDatabaseConnection() throws SQLException {
        connect();
    }

    /**
     * Establishes the connection to the MySQL database.
     *
     * @throws SQLException if the connection cannot be established.
     */
    private void connect() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Provides the singleton instance of the MySQL database connection.
     *
     * @return Singleton instance of MySqlDatabaseConnection.
     * @throws SQLException if the connection cannot be established.
     */
    public static MySqlDatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySqlDatabaseConnection();
        }
        return instance;
    }

    /**
     * Returns the current database connection. Reconnects if the connection is closed.
     *
     * @return The database connection.
     * @throws SQLException if the connection cannot be obtained.
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
            System.out.println("Connection established");
        }
        return connection;
    }
}

