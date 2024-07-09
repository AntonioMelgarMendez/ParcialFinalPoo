package org.example.proyecto.Utilities;

import javafx.scene.control.*;
import org.example.proyecto.Tables.Tarjeta;
import org.example.proyecto.Tables.Transaccion;

public class CleanData { // 00018523 Esta clase resume todos los métodos para limpiar datos que se ocuparan en todos los reportes
    public static void limpiarDatos(TextField textID, Label total, ChoiceBox<String> choiceBox1, ChoiceBox<String> choiceBox2){ // 00018523 Borrara los datos de los contenedores: TextField, ComboBox y Label
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        total.setText("0.00"); // 00018523 Asigna el valor del label a 0
        choiceBox1.setValue(null); // 00018523 Asigna como nulo el choiceBox1
        choiceBox2.setValue(null); // 00018523 Asigna como nulo el choiceBox2
    }

    public static void limpiarDatos(TextField textID, DatePicker fromDate, DatePicker toDate, TableView<Transaccion> tvData){ // 00018523 Borrara los datos de los contenedores: TextField, DatePicker y TableView
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        fromDate.setValue(null); // 00018523 Asigna como nulo la fecha de inicio
        toDate.setValue(null); // 00018523 Asigna como nulo la fecha de fin
        tvData.getItems().clear(); // 00018523 Limpia los datos del TableView
    }

    public static void limpiarDatos(TextField textID, TableView<Tarjeta> tableView){ // 00018523 Eliminará los datos de los siguientes contenedores: TextField, TableView
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        tableView.getItems().clear(); // 00018523 Limpia los datos del TableView
    }

    public static void limpiarDatos(ComboBox<String> facilitador, TableView<String> tvData) { // 00018523 Eliminará los datos de los siguientes contenedores: ComboBox, TableView
        facilitador.setValue(null); // 00018523 Asigna como valor predeterminado nulo al ComboBox
        tvData.getItems().clear(); // 00018523 Limpia los datos del TableView
    }

    public static void limpiarDatos(TextField textField1, TextField textField2, ComboBox<String> comboBox, RadioButton... rdButtons) { // 00018523 Eliminará los datos de los siguientes contenedores: ComboBox, TableView
        textField1.setText("");
        textField2.setText("");
        comboBox.setValue(null);
        for (RadioButton rdButton : rdButtons){
            rdButton.setSelected(false);
        }
    }
}
