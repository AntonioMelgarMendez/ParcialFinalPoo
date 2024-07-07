package org.example.proyecto.Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static org.example.proyecto.Utilities.AlertsManager.showAlert;

//00009123 Clase para manejar el cambio de escena
public class SceneChanger {
    //00009123 Metodo para cambiar de escena con el stage y el fxml al que se desea cambiar
    public static void changeScene(Stage stage, String fxmlPath) {
        try {
            //00009123 Creamos el FXMLLoader con la dirrecion proporcionada
            FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));
            //00009123 Creamos la nueva escena
            Scene scene = new Scene(fxmlLoader.load());
            //00009123 Seteamos la nueva escena
            stage.setScene(scene);
            //00009123 Mostramos la nueva escena
            stage.show();
        } catch (IOException e) {
            //00009123 Capturamos la excepcion para mostrar la alerta pasandole la causa
            showAlert("Error cambiar de escena", "Cambio fallido" + e.getCause(), e.getMessage());
        }
    }
}


