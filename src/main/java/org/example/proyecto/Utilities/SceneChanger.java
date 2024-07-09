package org.example.proyecto.Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static org.example.proyecto.Utilities.AlertsManager.showAlert;
public class SceneChanger {//00009123 Clase para manejar el cambio de escena
    public static void changeScene(Stage stage, String fxmlPath) {//00009123 Metodo para cambiar de escena con el stage y el fxml al que se desea cambiar
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));//00009123 Creamos el FXMLLoader con la dirrecion proporcionada
            Scene scene = new Scene(fxmlLoader.load()); //00009123 Creamos la nueva escena
            stage.setScene(scene); //00009123 Seteamos la nueva escena
            stage.show();//00009123 Mostramos la nueva escena
        } catch (IOException e) {//00009123 Capturamos la excepcion para mostrar la alerta pasandole la causa
            showAlert("Error cambiar de escena", "Cambio fallido" + e.getCause(), e.getMessage());//00009123 Mostramos un mensaje de error
        }
    }
}


