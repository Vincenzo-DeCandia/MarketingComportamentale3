package com.market.newmarketapp;

import com.market.database.facade.MySqlDatabaseFacade;
import com.market.newmarketapp.managerscene.Controller;
import com.market.service.AuthenticationService;
import com.market.service.IAuthenticationService;
import com.market.user.BaseUser;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller responsible for managing the login functionality.
 * It handles user input validation, authentication, and scene navigation based on user roles.
 */
public class LoginController extends Controller {

    // Database facade for interacting with the MySQL database.
    private final MySqlDatabaseFacade mySqlDatabaseFacade = new MySqlDatabaseFacade();

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink link;

    /**
     * Initializes the controller by setting up the registration link's action.
     * When the registration link is clicked, it switches to the "register" scene.
     */
    @FXML
    public void initialize() {
        // Set action for registration link: Switch to the "register" scene when clicked.
        link.setOnAction(event -> {
            this.getSceneManager().switchScene("register");
        });
    }

    /**
     * Handles the action when the back button is clicked.
     * This method switches the scene back to the "welcome" screen.
     *
     * @param event The action event triggered by the back button.
     */
    @FXML
    protected void onBackButton(ActionEvent event) {
        // Switch to the "welcome" scene when the back button is clicked.
        this.getSceneManager().switchScene("welcome");
    }

    /**
     * Handles the login button click event.
     * It validates the input fields, performs authentication, and switches scenes based on the user's role.
     *
     * @param event The action event triggered by the login button.
     */
    @FXML
    protected void handleLoginButton(ActionEvent event) {
        // Get the email and password entered by the user and trim any excess whitespace.
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Reset the error message visibility.
        errorLabel.setVisible(false);

        // Basic validation: Ensure that both email and password are provided.
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("I campi non possono essere vuoti!");
            errorLabel.setVisible(true);
            return;
        }

        // Authenticate the user using the provided credentials.
        IAuthenticationService authenticationService = new AuthenticationService();
        boolean auth = authenticationService.login(email, password);

        // Check the role of the logged-in user and switch to the appropriate scene.
        if (LoggedUserSingleton.getInstance().getLoggedUser().getRole().equals("user")) {
            // Switch to the user home scene if the logged-in user is a regular user.
            getSceneManager().switchScene("userHome");
        } else if (LoggedUserSingleton.getInstance().getLoggedUser().getRole().equals("admin")) {
            // Switch to the admin dashboard if the logged-in user is an admin.
            System.out.println("admin logged in");
            getSceneManager().switchScene("admin_dashboard");
        } else {
            // Display an error message if the credentials are incorrect.
            errorLabel.setText("Credenziali errate! Riprova.");
            errorLabel.setVisible(true);
        }
    }

    /**
     * Sets the logged-in user in the singleton for the logged user.
     *
     * @param baseUser The user to set as logged-in.
     */
    private void setLoggedUser(BaseUser baseUser) {
        // Set the logged user in the singleton instance.
        LoggedUserSingleton.getInstance().setLoggedUser(baseUser);
    }
}

