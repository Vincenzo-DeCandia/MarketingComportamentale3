package com.market.authentication;

import com.market.authentication.chainofresponsibility.concretehandler.SaveSession;
import com.market.authentication.chainofresponsibility.concretehandler.ValidateUserData;
import com.market.authentication.chainofresponsibility.handler.AuthHandler;
import com.market.user.BaseUser;

/**
 * The AuthCOR class implements a Chain of Responsibility (COR) pattern
 * for handling authentication processes. This class initializes a chain
 * of authentication handlers and provides a method to authenticate a user.
 */
public class AuthCOR {
    private final AuthHandler authHandler;

    /**
     * Constructs the AuthCOR object and initializes the chain of
     * authentication handlers. Each handler in the chain performs
     * a specific step in the authentication process.
     */
    public AuthCOR() {
        // Handler for validating user data
        AuthHandler validateUserDataAuthHandler = new ValidateUserData();
        // Handler for saving the user session
        AuthHandler saveSession = new SaveSession();

        validateUserDataAuthHandler.setNextHandler(saveSession);

        authHandler = validateUserDataAuthHandler;
    }

    /**
     * Authenticates a user by passing the user's data through the chain
     * of authentication handlers.
     *
     * @param baseUser the user attempting to log in, encapsulated in a
     *                 BaseUser object.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean login(BaseUser baseUser) {
        return authHandler.handleAuthentication(baseUser);
    }
}
