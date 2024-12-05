package com.market.authentication.chainofresponsibility.concretehandler;

import com.market.authentication.chainofresponsibility.handler.AuthHandler;
import com.market.datastorage.DataStorage;
import com.market.datastorage.SessionDataStorage;
import com.market.user.BaseUser;

/**
 * Handles the saving of user session data during authentication.
 * This class is a concrete implementation of the AuthHandler
 * in the Chain of Responsibility pattern.
 *
 * It saves the authenticated user's session data to a file,
 * applying encryption to ensure security.
 */
public class SaveSession extends AuthHandler {

    /**
     * Saves the authenticated user's session data to a file and
     * passes control to the next handler in the chain, if any.
     *
     * @param baseUser the authenticated user whose session dat needs to be saved.
     * @return true if the operation succeeds and the chain
     *         continues, false otherwise.
     */
    @Override
    public boolean handleAuthentication(BaseUser baseUser) {
        // Decorate the file handler with encryption for secure writing
        DataStorage dataStorage = new SessionDataStorage("session.txt");

        // Write the user's session data to the file
        dataStorage.write(baseUser);
        System.out.println("File saved successfully");

        // Pass control to the next handler in the chain
        return checkNext(baseUser);
    }
}

