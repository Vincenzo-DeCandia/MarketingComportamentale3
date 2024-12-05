package com.market.newmarketapp.managerscene;

import javafx.scene.control.Alert;

public class Controller {
    private SceneManager sceneManager;
    
    public Controller() {}
    
    public Controller(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    protected void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
