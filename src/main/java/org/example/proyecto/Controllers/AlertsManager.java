package org.example.proyecto.Controllers;

import javafx.scene.control.Alert;

public class AlertsManager {
    /*00009123
    Generamos una clase alert para poder reutilizarla en diferentes contextos,
    necesita recibir como un parametro los datos del tipo de alerta
     */
    public static void showAlert(String title, String header, String content) {
        /*00009123 Creamos una nueva alerta*/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        /*00009123 Agregamos todos los datos a la nueva alerta*/
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        /*00009123 Mostramos la alerta*/
        alert.showAndWait();
    }
}
