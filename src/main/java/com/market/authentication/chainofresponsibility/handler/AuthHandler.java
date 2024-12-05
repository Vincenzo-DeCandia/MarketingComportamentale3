package com.market.authentication.chainofresponsibility.handler;

import com.market.user.BaseUser;

/**
 * Abstract base class representing a handler in the authentication chain.
 * Each handler performs a specific task in the authentication process
 * and can delegate further processing to the next handler in the chain.
 */
public abstract class AuthHandler {
    /**
     * Reference to the next handler in the chain.
     */
    protected AuthHandler nextAuthHandler;

    /**
     * Sets the next handler in the chain.
     *
     * @param nextAuthHandler the handler to be linked as the next in the chain.
     */
    public void setNextHandler(AuthHandler nextAuthHandler) {
        this.nextAuthHandler = nextAuthHandler;
    }

    /**
     * Handles the authentication process for a user.
     * Concrete subclasses must implement this method to perform specific checks.
     *
     * @param baseUser the user attempting to authenticate.
     * @return true if the authentication step succeeds, false otherwise.
     */
    public abstract boolean handleAuthentication(BaseUser baseUser);

    /**
     * Passes the authentication process to the next handler in the chain,
     * if one exists. If no further handlers are present, returns true.
     *
     * @param baseUser the user attempting to authenticate.
     * @return true if the authentication process continues successfully,
     *         or if no further handlers exist; false otherwise.
     */
    protected boolean checkNext(BaseUser baseUser) {
        if (nextAuthHandler != null) {
            return nextAuthHandler.handleAuthentication(baseUser);
        }
        return true;
    }
}

