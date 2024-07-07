package org.example.proyecto.Controllers.Utils;

import javafx.scene.control.*;
import org.example.proyecto.Controllers.Tarjeta;

public class CleanData { // 00018523 Esta clase resume todos los métodos para limpiar datos que se ocuparan en todos los reportes
    public static void limpiarDatos(TextField textID, ComboBox<String> comboBox, Label total){ // 00018523 Borrara los datos de los contenedores: TextField, ComboBox y Label
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        comboBox.setValue(null); // 00018523 Asigna como valor predeterminado nulo al ComboBox
        total.setText("Total: 0.00"); // 00018523 Asigna el valor del label a 0
    }

    public static void limpiarDatos(TextField textID, DatePicker fromDate, DatePicker toDate, TableView<String> tvData){ // 00018523 Borrara los datos de los contenedores: TextField, DatePicker y TableView
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        fromDate.setValue(null); // 00018523 Asigna como nulo la fecha de inicio
        toDate.setValue(null); // 00018523 Asigna como nulo la fecha de fin
        tvData.getColumns().clear(); // 00018523 Limpia los datos del TableView
    }

    public static void limpiarDatos(TextField textID, TableView<Tarjeta> tableView){ // 00018523 Eliminará los datos de los siguientes contenedores: TextField, TableView
        textID.setText(""); // 00018523 Asigna como carácter nulo el TextField de la ID a buscar
        tableView.getColumns().clear(); // 00018523 Limpia los datos del TableView
    }

    public static void limpiarDatos(ComboBox<String> facilitador, TableView<String> tvData) { // 00018523 Eliminará los datos de los siguientes contenedores: ComboBox, TableView
        facilitador.setValue(null); // 00018523 Asigna como valor predeterminado nulo al ComboBox
        tvData.getItems().clear(); // 00018523 Limpia los datos del TableView
    }
}
