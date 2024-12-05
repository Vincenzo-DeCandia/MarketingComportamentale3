package com.market.newmarketapp;

import com.market.datastorage.DataStorage;
import com.market.datastorage.SessionDataStorage;
import com.market.newmarketapp.managerscene.SceneManager;
import com.market.user.BaseUser;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class MarketApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager();
        sceneManager.setPrimaryStage(stage);
        sceneManager.addScene("welcome", "welcome-view.fxml");
        sceneManager.addScene("register", "register-view.fxml");
        sceneManager.addScene("login", "login-view.fxml");
        sceneManager.addScene("admin_dashboard", "admin-dashboard-view.fxml");
        sceneManager.addScene("productManagement", "product-management-view.fxml");
        sceneManager.addScene("marketAnalysis", "market-analysis-view.fxml");
        sceneManager.addScene("userHome", "user-home-view.fxml");
        sceneManager.addScene("shoppingCart", "shopping-cart-view.fxml");
        sceneManager.addScene("profile", "profile-view.fxml");
        sceneManager.addScene("orderScene", "order-list-view.fxml");


        DataStorage dataStorage = new SessionDataStorage("session.txt");
        BaseUser baseUser = (BaseUser) dataStorage.read();
        if (baseUser != null) {
            LoggedUserSingleton.getInstance().setLoggedUser(baseUser);
            if (baseUser.getRole().equals("admin")) {
                System.out.println("Admin logged in");
                System.out.println("Name: " + baseUser.getName());
                sceneManager.switchScene("admin_dashboard");
            } else if (baseUser.getRole().equals("user")) {
                System.out.println("User: " + baseUser.getEmail() + ", Role: " + baseUser.getRole());
                sceneManager.switchScene("userHome");
            }
        } else {
            sceneManager.switchScene("welcome");
        }


        stage.setTitle("Marketing comportamentale 3");
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}