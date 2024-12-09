package com.market.newmarketapp.managerscene;

import com.market.newmarketapp.MarketApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages scene transitions in the application.
 * This class is responsible for storing and switching between scenes in the primary stage.
 */
public class SceneManager {

    // The primary stage of the application, which represents the main window.
    private Stage primaryStage;

    // A map that holds scene names and their corresponding scene file locations.
    private final Map<String, String> scenes = new HashMap<>();

    /**
     * Default constructor for the SceneManager.
     */
    public SceneManager() {}

    /**
     * Sets the primary stage for the application.
     *
     * @param primaryStage The primary stage to be set.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Gets the primary stage for the application.
     *
     * @return The current primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Adds a scene to the manager with a name and location.
     *
     * @param name The name of the scene.
     * @param locationScene The location of the FXML file that defines the scene.
     */
    public void addScene(String name, String locationScene) {
        scenes.put(name, locationScene);
    }

    /**
     * Removes a scene from the manager by its name.
     *
     * @param name The name of the scene to be removed.
     */
    public void deleteScene(String name) {
        scenes.remove(name);
    }

    /**
     * Switches the current scene to the one specified by the name.
     * It loads the FXML file, sets the scene, and assigns the controller.
     *
     * @param name The name of the scene to switch to.
     */
    public void switchScene(String name) {
        // Get the location of the scene from the scenes map using the provided name
        String location = scenes.get(name);

        try {
            // Load the FXML file for the scene using the FXMLLoader
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource(location));
            Parent root = loader.load();

            // If the scene was loaded successfully, set it on the primary stage
            if (root != null) {
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);

                // Get the controller for the loaded FXML and set the scene manager
                Controller controller = loader.getController();
                controller.setSceneManager(this);
            }
        } catch (IOException e) {
            // Handle any exceptions that occur during scene loading
            e.printStackTrace();
        }
    }
}

