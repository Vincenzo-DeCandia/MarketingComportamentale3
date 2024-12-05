package com.market.user.singletonloggeduser;

import com.market.user.BaseUser;

/**
 * Singleton class to manage the currently logged-in user.
 */
public class LoggedUserSingleton {

    // Holds the logged-in user instance
    private BaseUser loggedBaseUser;

    // Singleton instance of LoggedUserSingleton
    private static LoggedUserSingleton instance;

    // Private constructor to prevent direct instantiation
    private LoggedUserSingleton() {}

    /**
     * Returns the single instance of LoggedUserSingleton.
     *
     * @return the singleton instance of LoggedUserSingleton
     */
    public static LoggedUserSingleton getInstance() {
        if (instance == null) {
            instance = new LoggedUserSingleton();
        }
        return instance;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the logged-in BaseUser instance, or null if no user is set
     */
    public BaseUser getLoggedUser() {
        return loggedBaseUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param loggedBaseUser the BaseUser to set as the logged-in user
     */
    public void setLoggedUser(BaseUser loggedBaseUser) {
        this.loggedBaseUser = loggedBaseUser;
    }
}
