package org.example.proyecto.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class ReportBController {
    //00009123 Campo para el id
    @FXML
    private TextField idButton;
    //00009123 Menu desplegable para colocar el year
    @FXML
    private MenuButton yearButton;
    //00009123 Menu desplegable para colocar el mes
    @FXML
    private MenuButton monthButton;
    //00009123 Metodo para poder obtener los datos de los botones y pasarselos a la funcion de renderizacion
    public void totalGastoB(){
        //00009123 Guardamos en un campo string cada valor
        String id = idButton.getText();
        String year = yearButton.getText();
        String month = monthButton.getText();
        //00009123 Comprobamos si algun campo esta vacio
        if(!id.isBlank() || !year.isBlank()|| !month.isBlank()){
            //00009123 Aqui llamariamos a la funcion para procesar los datos

        }
        else{
            //00009123 Mostramos una alerta mostrando que los campos estan incompletos
            AlertsManager.showAlert("Los campos no estan completos", "Campos incompletos","No ha llegado todos los campos de texto");
        }
    }
}
