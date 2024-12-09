package com.market.newmarketapp.managerscene;

import javafx.scene.control.Alert;

/**
 * Abstract class that represents a controller in the application.
 * Controllers manage the logic and interactions for different views.
 */
public abstract class Controller {

    // The scene manager responsible for switching between scenes.
    private SceneManager sceneManager;

    /**
     * Default constructor for the controller.
     */
    public Controller() {}

    /**
     * Constructor that initializes the controller with a scene manager.
     *
     * @param sceneManager The scene manager used to switch scenes.
     */
    public Controller(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Sets the scene manager for the controller.
     *
     * @param sceneManager The scene manager to be set.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Retrieves the scene manager for the controller.
     *
     * @return The current scene manager.
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * Displays an alert with the specified title, message, and alert type.
     *
     * @param title The title of the alert.
     * @param message The message content of the alert.
     * @param alertType The type of the alert (e.g., ERROR, INFORMATION, WARNING).
     */
    protected void showAlert(String title, String message, Alert.AlertType alertType) {
        // Create an alert of the specified type
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text for the alert
        alert.setContentText(message); // Set the content text to the provided message
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }
}
