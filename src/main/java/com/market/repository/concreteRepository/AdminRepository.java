package com.market.repository.concreteRepository;

import com.market.repository.IBaseUserRepository;
import com.market.repository.MySqlRepository;
import com.market.user.concreteuser.Admin;

import java.util.List;
import java.util.Map;

import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Admin entities in a MySQL database.
 */
public class AdminRepository extends MySqlRepository<Admin, Integer> implements IBaseUserRepository<Admin> {

    /**
     * Finds an Admin by their unique identifier.
     *
     * @param id The ID of the Admin to find.
     * @return The Admin if found, or null otherwise.
     */
    @Override
    public Admin findById(Integer id) {
        return null;
    }

    /**
     * Retrieves all Admin entities.
     *
     * @return A list of all Admin entities, or an empty list if none exist.
     */
    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    /**
     * Saves a new Admin entity to the database.
     *
     * @param admin The Admin entity to save.
     * @return True if the Admin was successfully saved, false otherwise.
     */
    @Override
    public boolean save(Admin admin) {
        return false;
    }

    /**
     * Deletes an Admin entity from the database.
     *
     * @param admin The Admin entity to delete.
     * @return True if the Admin was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(Admin admin) {
        return false;
    }

    /**
     * Updates an existing Admin entity in the database.
     *
     * @param admin The Admin entity to update.
     * @return True if the Admin was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Admin admin) {
        return false;
    }

    /**
     * Finds an Admin by their email and password for authentication purposes.
     * The password is hashed using SHA-256 before querying the database.
     *
     * @param email The email of the Admin to find.
     * @param password The password of the Admin to find.
     * @return The Admin if a match is found, or null otherwise.
     */
    @Override
    public Admin findByEmailPassword(String email, String password) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(
                "SELECT * FROM staff WHERE staff_email = ? and staff_password = sha2(?, 256)",
                email,
                password
        );

        Admin admin = null;

        if (!rows.isEmpty()) {
            for (Map<String, Object> row : rows) {
                admin = new Admin();
                admin.setEmail(row.get("staff_email").toString());
                admin.setPassword(row.get("staff_password").toString());
                admin.setName(row.get("staff_name").toString());
                admin.setSurname(row.get("staff_surname").toString());
                admin.setRole(row.get("staff_role").toString());
            }
        }
        return admin;
    }
}

