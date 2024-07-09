package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.proyecto.Utilities.SceneChanger;

public class CrudController {
    @FXML
    private void transaccion(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte A
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/TablaTransaccion.fxml"); // 00038623 Cambia la escena a Tablatransaccion.fxml
    }
    @FXML
    private void tarjeta(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte A
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/TablaTarjeta.fxml"); // 00038623 Cambia la escena al de la tarjeta para modificarla
    }
    @FXML
    private void cliente(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte A
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/TablaCliente.fxml"); // 00038623 Cambia la escena al del cliente para modificarla
    }
    @FXML
    private void volver(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte A
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml"); // 00038623 Cambia la escena a Main.fxml
    }
}
