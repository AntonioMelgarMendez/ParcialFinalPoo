package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void reportea(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportA.fxml");
    }
    @FXML
    private void reporteb(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportB.fxml");
    }
    @FXML
    private void reportec(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportC.fxml");
    }
    @FXML
    private void reported(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/ReportD.fxml");
    }

    @FXML
    private void salir(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
