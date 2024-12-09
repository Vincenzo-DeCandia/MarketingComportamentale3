package com.market.repository.concreteRepository;

import com.market.database.facade.MySqlDatabaseFacade;
import com.market.repository.IBaseUserRepository;
import com.market.repository.IUserAnalyzeRepository;
import com.market.repository.MySqlRepository;
import com.market.user.concreteuser.User;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing User entities in a MySQL database.
 * Implements methods for CRUD operations on User entities.
 */
public class UserRepository extends MySqlRepository<User, Integer> implements IBaseUserRepository<User>, IUserAnalyzeRepository {

    private final MySqlDatabaseFacade mySqlDatabaseFacade = new MySqlDatabaseFacade();

    /**
     * Finds a User by their unique user ID.
     *
     * @param id The user ID.
     * @return The User entity if found, otherwise null.
     */
    @Override
    public User findById(Integer id) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM user WHERE user_id = ?", id);
        User user = null;
        if (!rows.isEmpty()) {
            user = new User();
            Map<String, Object> row = rows.getFirst();
            user.setIdUser(Integer.parseInt(row.get("user_id").toString()));
            extract_user(row, user);
        }
        return user;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all User entities, or null if no users are found.
     */
    @Override
    public List<User> findAll() {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM user");
        return getUsers(rows);
    }

    /**
     * Helper method to extract user details from a Map and set them to a User object.
     *
     * @param row The row of data representing a user.
     * @param user The User object to populate with data.
     */
    private void extract_user(Map<String, Object> row, User user) {
        user.setIdUser((Integer)(row.get("user_id")));
        user.setEmail(row.get("user_email").toString());
        user.setName(row.get("user_name").toString());
        user.setPassword(row.get("user_password").toString());
        user.setSurname(row.get("user_surname").toString());
        user.setRole(row.get("user_role").toString());
        user.setPhone(row.get("user_phone").toString());
    }

    /**
     * Saves a new User to the database.
     *
     * @param user The User entity to save.
     * @return True if the operation was successful, false otherwise.
     */
    @Override
    public boolean save(User user) {
        Map<String, List<Object>> queries = new HashMap<>();
        String query = "INSERT INTO user (user_id, user_name, user_surname, user_email, user_password, user_role, user_phone) VALUES (0, ?, ?, ?, sha2(?,256), ?, ?)";
        List<Object> list = new ArrayList<>();
        list.add(user.getName());
        list.add(user.getSurname());
        list.add(user.getEmail());
        list.add(user.getPassword());
        list.add(user.getRole());
        list.add(user.getPhone());
        queries.put(query, list);
        return mySqlDatabaseFacade.executeTransaction(queries);
    }

    /**
     * Deletes a User from the database by their ID.
     *
     * @param user The User entity to delete.
     * @return True if the operation was successful, false otherwise.
     */
    @Override
    public boolean delete(User user) {
        String query = "DELETE FROM user WHERE user_id = " + user.getIdUser();
        Map<String, List<Object>> map = new HashMap<>();
        map.put(query, null);
        return mySqlDatabaseFacade.executeTransaction(map);
    }

    /**
     * Updates a User in the database.
     * Currently, this method is not implemented.
     *
     * @param user The User entity to update.
     * @return False, as this operation is not implemented.
     */
    @Override
    public boolean update(User user) {
        return false; // Method not implemented yet
    }

    /**
     * Finds a User by their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return The User entity if found, otherwise null.
     */
    @Override
    public User findByEmailPassword(String email, String password) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM user WHERE user_email = ? and user_password = sha2(?, 256)", email, password);
        User user = null;

        for (Map<String, Object> row : rows) {
            user = new User();
            user.setIdUser((Integer) row.get("user_id"));
            user.setEmail((String) row.get("user_email"));
            user.setPassword((String) row.get("user_password"));
            user.setName((String) row.get("user_name"));
            user.setRole((String) row.get("user_role"));
            user.setSurname((String) row.get("user_surname"));
            user.setPhone((String) row.get("user_phone"));
        }

        return user;
    }

    /**
     * Searches for users who have placed more than a specified number of orders.
     *
     * @param orderNumber The minimum number of orders.
     * @return A list of users meeting the criteria, or null if no users are found.
     */
    @Override
    public List<User> searchByOrderNumber(int orderNumber) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT user_id, user_email, user_name, user_surname, user_role, COUNT(order_id) as num_order FROM user RIGHT JOIN `order` on user.user_id = `order`.user_id_fk GROUP BY user_id HAVING num_order > ?;", orderNumber);
        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            User user = new User();
            user.setIdUser((Integer) row.get("user_id"));
            user.setEmail((String) row.get("user_email"));
            user.setName((String) row.get("user_name"));
            user.setRole((String) row.get("user_role"));
            user.setSurname((String) row.get("user_surname"));
            user.setPhone((String) row.get("user_phone"));
            users.add(user);
        }
        return users.isEmpty() ? null : users;
    }

    /**
     * Helper method to map rows of query results to a list of User objects.
     *
     * @param rows The list of rows returned from the database query.
     * @return A list of User objects, or null if no rows are present.
     */
    @Nullable
    private List<User> getUsers(List<Map<String, Object>> rows) {
        List<User> users = null;

        if (rows != null && !rows.isEmpty()) {
            users = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                User user = new User();
                extract_user(row, user);
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Retrieves a random subset of users.
     *
     * @param count The number of users to retrieve randomly.
     * @return A list of randomly selected users.
     */
    @Override
    public List<User> randomUser(int count) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM user ORDER BY RAND() LIMIT ?", count);
        return getUsers(rows);
    }
}

