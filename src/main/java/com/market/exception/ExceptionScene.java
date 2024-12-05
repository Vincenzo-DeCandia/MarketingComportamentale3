package com.market.exception;

import javafx.scene.control.Alert;

import javafx.scene.control.Alert;

/**
 * Custom exception class for handling errors and displaying an error message using an alert.
 */
public class ExceptionScene extends Exception {

    /**
     * Constructs a new ExceptionScene with the specified error message.
     * It also shows an error alert with the provided message.
     *
     * @param message The error message to be displayed in the alert.
     */
    public ExceptionScene(String message) {
        super(message);

        // Create and configure an alert to display the error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

