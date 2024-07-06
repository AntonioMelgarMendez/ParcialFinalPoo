package org.example.proyecto.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static org.example.proyecto.Controllers.AlertsManager.showAlert;

public class SceneChanger {
    public static void changeScene(Stage stage, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            showAlert("Error cambiar de escena","Cambio fallido"+e.getCause(),e.getMessage());
        }
    }

}