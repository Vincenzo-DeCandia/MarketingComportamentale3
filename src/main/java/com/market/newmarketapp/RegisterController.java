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
    protected void onBackButton(ActionEvent event) {
        this.getSceneManager().switchScene("welcome");
    }

    @FXML
    protected void registerButtonAction(ActionEvent event) {
        // Reset messaggi e stili
        messageLabel.setVisible(false);
        resetFieldStyles();

        // Raccolta dati
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String surname = surnameField.getText().trim();

        // Validazione di base
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || surname.isEmpty()) {
            messageLabel.setText("Tutti i campi sono obbligatori.");
            messageLabel.setVisible(true);
            highlightEmptyFields(name, email, password, surname);
            return;
        }

        // Creazione utente e salvataggio
        String role = "user";
        User user = new User(name, email, password, surname, role);
        IRepository<User, Integer> userRepository = new UserRepository();

        if (userRepository.save(user)) {
            messageLabel.setText("Registrazione avvenuta con successo!");
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setVisible(true);

            // Switch scena
            getSceneManager().switchScene("welcome");
        } else {
            messageLabel.setText("Errore durante la registrazione. Riprova.");
            messageLabel.setVisible(true);
        }
    }

    private void resetFieldStyles() {
        String defaultStyle = "-fx-border-color: #3498db; -fx-border-radius: 5; -fx-background-radius: 5;";
        nameField.setStyle(defaultStyle);
        emailField.setStyle(defaultStyle);
        passwordField.setStyle(defaultStyle);
        surnameField.setStyle(defaultStyle);
    }

    private void highlightEmptyFields(String name, String email, String password, String surname) {
        String errorStyle = "-fx-border-color: red; -fx-border-radius: 5; -fx-background-radius: 5;";
        if (name.isEmpty()) nameField.setStyle(errorStyle);
        if (email.isEmpty()) emailField.setStyle(errorStyle);
        if (password.isEmpty()) passwordField.setStyle(errorStyle);
        if (surname.isEmpty()) surnameField.setStyle(errorStyle);
    }

}
