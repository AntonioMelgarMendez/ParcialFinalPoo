package org.example.proyecto.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class ReportBController {
    @FXML
    private TextField idButton;
    @FXML
    private MenuButton yearButton;
    @FXML
    private MenuButton monthButton;

    public void totalGastoB(){
      String id = idButton.getText();
      String year = yearButton.getText();
      String month = monthButton.getText();
      if(id.isBlank() || year.isBlank()|| month.isBlank()){
         //Llamar aqui la funcion de calculo y de renderizacion :p

      }
      else{
          AlertsManager.showAlert("Los campos no estan completos", "Campos incompletos","No ha llegado todos los campos de texto");
      }
    }
}
