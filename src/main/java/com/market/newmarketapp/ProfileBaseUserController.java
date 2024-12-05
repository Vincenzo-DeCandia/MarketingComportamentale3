package com.market.newmarketapp;

import com.market.newmarketapp.managerscene.Controller;
import com.market.user.BaseUser;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileBaseUserController extends Controller {
    private final BaseUser baseUser;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField roleTextField;

    public ProfileBaseUserController() {
        this.baseUser = LoggedUserSingleton.getInstance().getLoggedUser();
    }

    @FXML
    private void initialize() {
        nameTextField.setText(baseUser.getName());
        emailTextField.setText(baseUser.getEmail());
        surnameTextField.setText(baseUser.getSurname());
        roleTextField.setText(baseUser.getRole());
    }

    @FXML
    private void onBackButton() {
        if (baseUser.getRole().equals("admin")) {
            getSceneManager().switchScene("admin_dashboard");
        } else if (baseUser.getRole().equals("user")) {
            getSceneManager().switchScene("userHome");
        }
    }

}
