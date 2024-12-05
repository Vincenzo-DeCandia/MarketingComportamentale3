package com.market.database.facade;

import com.market.database.DatabaseConnection;
import com.market.database.QueryExecutor;
import com.market.database.TransactionManager;
import com.market.database.mysql.MySqlDatabaseConnection;
import com.market.database.mysql.MySqlQueryExecutor;
import com.market.database.mysql.MySqlTransactionManager;

import java.sql.Connection;
import java.util.*;

/**
 * Facade for simplifying interactions with the MySQL database.
 * Provides a unified interface to manage database connections, queries, and transactions.
 */
public class MySqlDatabaseFacade {
    private DatabaseConnection databaseConnection;
    private QueryExecutor<List<Map<String, Object>>> queryExecutor;
    private TransactionManager transactionManager;

    /**
     * Initializes the facade, setting up the database connection, query executor, and transaction manager.
     */
    public MySqlDatabaseFacade() {
        try {
            databaseConnection = MySqlDatabaseConnection.getInstance();
            queryExecutor = new MySqlQueryExecutor(databaseConnection.getConnection());
            transactionManager = new MySqlTransactionManager(databaseConnection.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a SELECT query and returns the result as a list of maps.
     *
     * @param query The SQL query.
     * @param parameters Query parameters.
     * @return A list of maps representing query results, or null if an error occurs.
     */
    public List<Map<String, Object>> fetchData(String query, Object... parameters) {
        try {
            queryExecutor.changeConnection(databaseConnection.getConnection());
            List<Map<String, Object>> map = queryExecutor.executeQuery(query, Arrays.asList(parameters));
            close();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Executes a set of UPDATE queries within a transaction.
     *
     * @param query A map of SQL queries and their parameters.
     * @return A list of integers representing affected rows for each query, or null if an error occurs.
     */
    public boolean executeTransaction(Map<String, List<Object>> query) {
        List<Integer> rowsUpdated = new ArrayList<>();
        try {
            Connection connection = databaseConnection.getConnection();
            transactionManager.changeConnection(connection);
            queryExecutor.changeConnection(connection);
            transactionManager.begin();
            for (Map.Entry<String, List<Object>> entry : query.entrySet()) {
                rowsUpdated.add(queryExecutor.executeUpdate(entry.getKey(), entry.getValue()));
            }
            transactionManager.commit();
            System.out.println(rowsUpdated);
        } catch (Exception e) {
            transactionManager.rollback();
            e.printStackTrace();
            close();
            return false;
        }
        close();
        return true;
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        try {
            if (!databaseConnection.getConnection().isClosed()) {
                databaseConnection.getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


