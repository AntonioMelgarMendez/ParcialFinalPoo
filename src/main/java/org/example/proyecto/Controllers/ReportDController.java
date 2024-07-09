package org.example.proyecto.Controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyecto.Tables.Tarjeta;
import org.example.proyecto.Tables.Cliente;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

import static org.example.proyecto.Utilities.AlertsManager.showAlert;
import static org.example.proyecto.Utilities.CleanData.limpiarDatos;
import static org.example.proyecto.Utilities.SaveTXT.SaveDReport;


public class ReportDController { // 00083823 Controller para
    @FXML
    private ComboBox<String> comboBoxFacilitador; // 00083823 ComboBox para seleccionar el facilitador de tarjeta

    @FXML
    private Button btnBuscar; // 00083823 Botón para buscar los datos de las transacciones

    @FXML
    private Button btnLimpiar; // 00083823 Botón para limpiar los datos mostrados

    @FXML
    private Button btnSalir; // 00083823 Botón para Salir de la vista del reporte D

    @FXML
    private TableView<Cliente> tbMostrarDatos;

    @FXML
    private TableColumn<Cliente, Integer> tbidCliente;

    @FXML
    private TableColumn<Cliente, String> tbNombre;

    @FXML
    private TableColumn<Cliente, String> tbApellido;

    @FXML
    private TableColumn<Cliente, Integer> tbCantCompras;

    @FXML
    private TableColumn<Cliente, Double> tbTotal;

    @FXML
    public void initialize() {
        comboBoxFacilitador.setItems(FXCollections.observableArrayList("Visa", "MasterCard", "AmericanExpress"));

        tbidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tbNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tbCantCompras.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras"));
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("totalGastado"));

        tbMostrarDatos.setPlaceholder(new Label("No hay datos para mostrar"));
    }

    @FXML
    public void onBuscar() {
        String facilitador = comboBoxFacilitador.getValue();
        if (facilitador == null || facilitador.isEmpty()) {
            showAlert("ERROR", "Facilitador no seleccionado", "Por favor, seleccione un facilitador.");
            return;
        }

        ObservableList<Cliente> dataList = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(
                DataBaseCredentials.getInstance().getUrl(),
                DataBaseCredentials.getInstance().getUsername(),
                DataBaseCredentials.getInstance().getPassword())) {

            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                ps1.executeUpdate();
            }
            String query = "SELECT c.idCliente AS 'ID Cliente', c.nombre AS Nombre, c.apellido AS Apellido, " +
                    "COUNT(t.idTransaccion) AS 'Cantidad de Compras', SUM(t.totalMonto) AS Total " +
                    "FROM transaccion t " +
                    "INNER JOIN cliente c ON t.idCliente = c.idCliente " +
                    "INNER JOIN tarjeta tr ON c.idCliente = tr.idCliente " +
                    "WHERE tr.facilitador = ? " +
                    "GROUP BY c.idCliente, c.nombre, c.apellido";

            PreparedStatement ps2 = conn.prepareStatement(query);
            ps2.setString(1, facilitador);
            ResultSet rs = ps2.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("ID Cliente"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getInt("Cantidad de Compras"),
                        rs.getDouble("Total")
                );
                dataList.add(cliente);
            }
            tbMostrarDatos.setItems(dataList);
                conn.close();
                SaveDReport(facilitador, dataList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLimpiar() {
        comboBoxFacilitador.setValue(null);
        tbMostrarDatos.getItems().clear();
    }

    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void onFacilitadores(ActionEvent actionEvent) {
    }
}
