package com.market.newmarketapp;

import com.market.authentication.AuthCOR;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.concreteRepository.AdminRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.user.BaseUser;
import com.market.user.concreteuser.Admin;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.HyperlinkLabel;

public class LoginController extends Controller {
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

    @FXML
    public void initialize() {
        link.setOnAction(event -> {
            this.getSceneManager().switchScene("register");
        });
    }

    @FXML
    protected void onBackButton(ActionEvent event) {
        this.getSceneManager().switchScene("welcome");
    }

    @FXML
    protected void handleLoginButton(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Reset del messaggio di errore
        errorLabel.setVisible(false);

        // Validazione di base
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("I campi non possono essere vuoti!");
            errorLabel.setVisible(true);
            return;
        }

        // Autenticazione con AuthCOR
        AuthCOR authCOR = new AuthCOR();
        BaseUser user = new User(email, password);
        BaseUser admin = new Admin(email, password);

        if (authCOR.login(user)) {
            getSceneManager().switchScene("userHome");
        } else if (authCOR.login(admin)) {
            System.out.println("admin logged in");
            System.out.println(admin.getName());
            getSceneManager().switchScene("admin_dashboard");
        } else {
            errorLabel.setText("Credenziali errate! Riprova.");
            errorLabel.setVisible(true);
        }
    }

    private void setLoggedUser(BaseUser baseUser) {
        LoggedUserSingleton.getInstance().setLoggedUser(baseUser);
    }

}
