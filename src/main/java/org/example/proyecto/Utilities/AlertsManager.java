package org.example.proyecto.Utilities;

import javafx.scene.control.Alert;

public class AlertsManager {
    public static void showAlert(String title, String header, String content) {//00009123Generamos una clase alert para poder reutilizarla en diferentes contextos
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  //00009123 Creamos una nueva alerta
        alert.setTitle(title); //00009123 Agregamos todos los datos a la nueva alerta
        alert.setHeaderText(header);//00009123 Colocamos el header y el content text
        alert.setContentText(content);//00009123 Colocamos el contenido
        alert.showAndWait();//00009123 Mostramos la alerta
    }
}
