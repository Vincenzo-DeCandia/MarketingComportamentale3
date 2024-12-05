package com.market.authentication.chainofresponsibility.concretehandler;

import com.market.authentication.chainofresponsibility.handler.AuthHandler;
import com.market.repository.concreteRepository.AdminRepository;
import com.market.repository.concreteRepository.ShoppingCartRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.BaseUser;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.util.Pair;

/**
 * Handles user data validation during authentication.
 * This class is a concrete implementation of the AuthHandler
 * in the Chain of Responsibility pattern.
 *
 * It verifies user credentials against the appropriate repository
 * (user or admin) based on the role, and it retrieves additional
 * user-related data, such as shopping cart information.
 */
public class ValidateUserData extends AuthHandler {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * Constructs a ValidateUserDataAuthHandler and initializes
     * the required repositories for user and admin data validation.
     */
    public ValidateUserData() {
        userRepository = new UserRepository();
        adminRepository = new AdminRepository();
        shoppingCartRepository = new ShoppingCartRepository();
    }

    /**
     * Validates the user's credentials and retrieves additional
     * user information if applicable. Depending on the role of
     * the user (user/admin), it performs the appropriate validation.
     *
     * @param baseUser the user attempting to authenticate, containing
     *                 initial login data like email and password.
     * @return true if authentication succeeds, false otherwise.
     */
    @Override
    public boolean handleAuthentication(BaseUser baseUser) {
        if (baseUser.getRole().equals("user")) {
            // Validate and retrieve user data
            baseUser = userRepository.findByEmailPassword(baseUser.getEmail(), baseUser.getPassword());
            if (baseUser != null) {
                // Set the shopping cart for the user
                ((User) baseUser).setShoppingCart(
                        shoppingCartRepository.findById(new Pair<>(baseUser.getIdUser(), null))
                );
            }
        } else if (baseUser.getRole().equals("admin")) {
            // Validate and retrieve admin data
            baseUser = adminRepository.findByEmailPassword(baseUser.getEmail(), baseUser.getPassword());
        }

        // Logging and user assignment
        System.out.println("Dati presi");
        if (baseUser != null) {
            System.out.println(baseUser.getName());
        }

        // Set the authenticated user in the LoggedUserSingleton
        LoggedUserSingleton.getInstance().setLoggedUser(baseUser);

        // Check the next handler in the chain if the current authentication succeeds
        if (baseUser == null) {
            return false;
        } else {
            return checkNext(baseUser);
        }
    }
}

