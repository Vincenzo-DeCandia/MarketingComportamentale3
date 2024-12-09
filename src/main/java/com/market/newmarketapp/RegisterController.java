package com.market.newmarketapp;

import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.concreteuser.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the registration process of a new user.
 * This controller handles user input, validates it, and saves the new user's information.
 */
public class RegisterController extends Controller {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField surnameField;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField phoneField;

    /**
     * Handles the action when the back button is clicked.
     * Switches the scene back to the welcome page.
     *
     * @param event The event triggered by the button click.
     */
    @FXML
    protected void onBackButton(ActionEvent event) {
        // Switch to the welcome page scene.
        this.getSceneManager().switchScene("welcome");
    }

    /**
     * Handles the registration process when the register button is clicked.
     * Collects user input, validates the data, and attempts to create a new user.
     *
     * @param event The event triggered by the button click.
     */
    @FXML
    protected void registerButtonAction(ActionEvent event) {
        // Reset messages and field styles
        messageLabel.setVisible(false);
        resetFieldStyles();

        // Collect user input from fields
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String surname = surnameField.getText().trim();
        String phone = phoneField.getText().trim();

        // Basic validation: check if any fields are empty
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
            messageLabel.setText("Tutti i campi sono obbligatori.");
            messageLabel.setVisible(true);
            // Highlight empty fields
            highlightEmptyFields(name, email, password, surname, phone);
            return;
        }

        // Create a new user and set the role to "user"
        String role = "user";
        User user = new User(name, email, password, surname, role);
        user.setPassword(phone);  // Set the phone number as the password for this user (seems like a mistake)
        IRepository<User, Integer> userRepository = new UserRepository();

        // Save the user to the repository
        if (userRepository.save(user)) {
            // Show success message and switch scene to the welcome page
            messageLabel.setText("Registrazione avvenuta con successo!");
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setVisible(true);
            getSceneManager().switchScene("welcome");
        } else {
            // Show error message if registration fails
            messageLabel.setText("Errore durante la registrazione. Riprova.");
            messageLabel.setVisible(true);
        }
    }

    /**
     * Resets the style of all input fields to the default style.
     * This is used when resetting the form or when there is no error.
     */
    private void resetFieldStyles() {
        // Set default style for all fields
        String defaultStyle = "-fx-border-color: #3498db; -fx-border-radius: 5; -fx-background-radius: 5;";
        nameField.setStyle(defaultStyle);
        emailField.setStyle(defaultStyle);
        passwordField.setStyle(defaultStyle);
        surnameField.setStyle(defaultStyle);
        phoneField.setStyle(defaultStyle);
    }

    /**
     * Highlights the fields that are empty by changing their border color to red.
     *
     * @param name The name input field value.
     * @param email The email input field value.
     * @param password The password input field value.
     * @param surname The surname input field value.
     * @param phone The phone input field value.
     */
    private void highlightEmptyFields(String name, String email, String password, String surname, String phone) {
        // Set error style for empty fields
        String errorStyle = "-fx-border-color: red; -fx-border-radius: 5; -fx-background-radius: 5;";
        if (name.isEmpty()) nameField.setStyle(errorStyle);
        if (email.isEmpty()) emailField.setStyle(errorStyle);
        if (password.isEmpty()) passwordField.setStyle(errorStyle);
        if (surname.isEmpty()) surnameField.setStyle(errorStyle);
        if (phone.isEmpty()) phoneField.setStyle(errorStyle);
    }

}

