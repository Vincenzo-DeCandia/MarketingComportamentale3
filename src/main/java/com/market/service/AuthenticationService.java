package com.market.service;

import com.market.datastorage.DataStorage;
import com.market.datastorage.SessionDataStorage;
import com.market.entity.ShoppingCart;
import com.market.repository.IBaseUserRepository;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.AdminRepository;
import com.market.repository.concreteRepository.ShoppingCartRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.BaseUser;
import com.market.user.concreteuser.Admin;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.util.Pair;

import java.util.List;

/**
 * Authentication service that handles user login and session management.
 */
public class AuthenticationService implements IAuthenticationService {
    private final IBaseUserRepository<User> userRepository = new UserRepository();
    private final IBaseUserRepository<Admin> adminRepository = new AdminRepository();
    private final IRepository<ShoppingCart, Pair<Integer, List<Integer>>> shoppingCartRepository = new ShoppingCartRepository();

    /**
     * Logs in a user based on the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return {@code true} if login is successful, {@code false} otherwise.
     */
    @Override
    public boolean login(String email, String password) {
        BaseUser baseUser;
        baseUser = userRepository.findByEmailPassword(email, password);
        if (baseUser != null) {
            // Set the shopping cart for the user
            ((User) baseUser).setShoppingCart(
                    shoppingCartRepository.findById(new Pair<>(baseUser.getIdUser(), null))
            );
        }
        if (baseUser == null) {
            baseUser = adminRepository.findByEmailPassword(email, password);
        }

        // Logging and user assignment
        System.out.println("Dati presi");
        if (baseUser != null) {
            System.out.println(baseUser.getName());
            DataStorage dataStorage = new SessionDataStorage("session.txt");

            // Write the user's session data to the file
            dataStorage.write(baseUser);
        }

        // Set the authenticated user in the LoggedUserSingleton
        LoggedUserSingleton.getInstance().setLoggedUser(baseUser);
        return baseUser != null;
    }
}
