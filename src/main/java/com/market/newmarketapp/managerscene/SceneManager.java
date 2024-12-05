package com.market.newmarketapp.managerscene;

import com.market.newmarketapp.MarketApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Stage primaryStage;
    private final Map<String, String> scenes = new HashMap<>();

    public SceneManager() {}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void addScene(String name, String locationScene) {
        scenes.put(name, locationScene);
    }

    public void deleteScene(String name) {
        scenes.remove(name);
    }

    public void switchScene(String name) {
        String location = scenes.get(name);
        try {
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource(location));
            Parent root = loader.load();
            if (root != null) {
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);

                Controller controller = loader.getController();
                controller.setSceneManager(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
