package com.market.newmarketapp;

import com.market.newmarketapp.managerscene.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class WelcomeController extends Controller {
    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    public void initialize() {
    }

    @FXML
    protected void goToRegister(ActionEvent event) {
        this.getSceneManager().switchScene("register");
    }

    @FXML
    protected void goToLogin(ActionEvent event) {
        this.getSceneManager().switchScene("login");
    }

}
