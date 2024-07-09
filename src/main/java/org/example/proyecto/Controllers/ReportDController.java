package org.example.proyecto.Controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.proyecto.Tables.Tarjeta;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;

import java.sql.*;

import static org.example.proyecto.Utilities.CleanData.limpiarDatos;
import static org.example.proyecto.Utilities.SaveTXT.SaveAReport;
import static org.example.proyecto.Utilities.SaveTXT.SaveCReport;

public class ReportDController { // 00083823 Controller para
    @FXML
    private ComboBox<String> comboBoxFacilitador; // 00083823 ComboBox para seleccionar el facilitador de tarjeta

    @FXML
    private Button btnBuscar; // 00083823 Botón para buscar los datos de las transacciones

    @FXML
    private Button btnLimpiar; // 00083823 Botón para limpiar los datos mostrados

    @FXML
    private Button btnSalir; // 00083823 Botón para Salir de la vista del reporte D


    }
