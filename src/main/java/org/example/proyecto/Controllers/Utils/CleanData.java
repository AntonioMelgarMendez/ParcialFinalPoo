package org.example.proyecto.Controllers.Utils;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CleanData {
    public static void limpiarDatos(TextField textID, ComboBox<String> comboBox, Label total){
        textID.setText("");
        comboBox.setValue(null);
        total.setText("Total: 0.00");
    }

    public static void limpiarDatos(TextField textID, DatePicker fromDate, DatePicker toDate, TableView<String> tvData){
        textID.setText("");
        fromDate.setValue(null);
        toDate.setValue(null);
        tvData.getItems().clear();
    }

    public static void limpiarDatos(TextField textID, VBox... VBoxes){
        textID.setText("");
        for (VBox VBox : VBoxes){
            VBox.getChildren().clear();
        }
    }

    public static void limpiarDatos(ComboBox<String> facilitador, TableView<String> tvData) {
        facilitador.setValue(null);
        tvData.getItems().clear();
    }
}
