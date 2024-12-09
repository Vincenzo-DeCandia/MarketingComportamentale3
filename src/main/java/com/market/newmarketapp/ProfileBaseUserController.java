package com.market.newmarketapp;

import com.market.newmarketapp.managerscene.Controller;
import com.market.user.BaseUser;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for managing the profile information of a logged-in user.
 * This controller displays and allows modification of the base user's profile information.
 */
public class ProfileBaseUserController extends Controller {

    // The currently logged-in base user.
    private final BaseUser baseUser;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private TextField phoneTextField;

    /**
     * Constructor that initializes the controller and gets the logged-in user.
     */
    public ProfileBaseUserController() {
        // Get the logged-in user from the singleton instance.
        this.baseUser = LoggedUserSingleton.getInstance().getLoggedUser();
    }

    /**
     * Initializes the controller by setting the user's profile details into the text fields.
     * This method is called when the scene is loaded.
     */
    @FXML
    private void initialize() {
        // Set the profile details into the corresponding text fields.
        nameTextField.setText(baseUser.getName());
        emailTextField.setText(baseUser.getEmail());
        surnameTextField.setText(baseUser.getSurname());
        roleTextField.setText(baseUser.getRole());
        phoneTextField.setText(baseUser.getPhone());
    }

    /**
     * Handles the action when the back button is clicked.
     * Based on the user's role, it switches the scene back to either the admin dashboard or the user home screen.
     */
    @FXML
    private void onBackButton() {
        // Check the user's role and navigate to the appropriate scene.
        if (baseUser.getRole().equals("admin")) {
            // If the user is an admin, go to the admin dashboard.
            getSceneManager().switchScene("admin_dashboard");
        } else if (baseUser.getRole().equals("user")) {
            // If the user is a regular user, go to the user home screen.
            getSceneManager().switchScene("userHome");
        }
    }
}

