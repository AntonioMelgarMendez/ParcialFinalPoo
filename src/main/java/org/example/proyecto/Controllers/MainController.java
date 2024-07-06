package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void reportea(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte A
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportA.fxml"); // 00038623 Cambia la escena a ReportA.fxml
    }

    @FXML
    private void reporteb(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte B
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportB.fxml"); // 00038623 Cambia la escena a ReportB.fxml
    }

    @FXML
    private void reportec(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte C
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportC.fxml"); // 00038623 Cambia la escena a ReportC.fxml
    }

    @FXML
    private void reported(ActionEvent event) { // 00038623 Define el método para manejar el evento del botón Reporte D
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportD.fxml"); // 00038623 Cambia la escena a ReportD.fxml
    }

    @FXML
    private void salir(ActionEvent event){ // 00038623 Define el método para manejar el evento del botón Salir
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual desde el evento de acción
        stage.close(); // 00038623 Cierra la ventana actual
    }
}

