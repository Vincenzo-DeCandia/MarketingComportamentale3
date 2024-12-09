package com.market.newmarketapp;

import com.market.newmarketapp.managerscene.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Controller for the welcome page of the application.
 * This controller handles the navigation to the register and login scenes.
 */
public class WelcomeController extends Controller {

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader.
     */
    public void initialize() {
        // Initialization logic can be added here if needed
    }

    /**
     * Handles the action of clicking the "Register" button.
     * Switches the scene to the registration page.
     *
     * @param event The event triggered by the button click.
     */
    @FXML
    protected void goToRegister(ActionEvent event) {
        // Switch to the register scene
        this.getSceneManager().switchScene("register");
    }

    /**
     * Handles the action of clicking the "Login" button.
     * Switches the scene to the login page.
     *
     * @param event The event triggered by the button click.
     */
    @FXML
    protected void goToLogin(ActionEvent event) {
        // Switch to the login scene
        this.getSceneManager().switchScene("login");
    }

}
