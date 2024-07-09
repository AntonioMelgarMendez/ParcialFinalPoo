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
import static org.example.proyecto.Utilities.SaveTXT.SaveCReport;
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

    private Connection conn;

    @FXML
    public void initialize() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasebanco.sql", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){ // 00018523 Realiza la conexión a la base de datos
            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00083823 Cambia a la base de datos específica
                ps1.executeUpdate(); // 00083823 Ejecuta la actualización para usar la base de datos
            }

            String query = "SELECT c.idCliente as 'ID Cliente', c.nombre as Nombre, c.apellido as Apellido, COUNT(t.idTransaccion) AS 'Cantidad de Compras', SUM(t.totalMonto) AS Total " +
                    "FROM Transaccion t " +
                    "INNER JOIN Cliente c ON t.idCliente = c.idCliente " +
                    "INNER JOIN Tarjeta tr ON t.idTarjeta = tr.idTarjeta " +
                    "WHERE tr.facilitador = ? " +
                    "GROUP BY c.idCliente, c.nombre, c.apellido";

            PreparedStatement ps1 = conn.prepareStatement(query);
            ps1.setString(1, facilitador);
            ResultSet rs = ps1.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + ": " + rs.getString(i) + " ");
                }
                System.out.println(rsmd);

                Cliente cliente = new Cliente(
                        rs.getInt("ID Cliente"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getInt("Cantidad de Compras"),
                        rs.getDouble("Total")
                );
                dataList.add(cliente);
                System.out.println(cliente);
            }

            System.out.println("dataList size: " + dataList.size());

            tbMostrarDatos.setItems(dataList);

            conn.close();
            //SaveDReport(tbidCliente.getText(), facilitadorList);
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


   /* private void cargarFacilitadores() {
        String query = "SELECT DISTINCT facilitador FROM tarjeta";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            ObservableList<String> facilitadores = FXCollections.observableArrayList();
            while (rs.next()) {
                facilitadores.add(rs.getString("facilitador"));
            }
            comboBoxFacilitador.setItems(facilitadores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buscar() {
        String facilitador = comboBoxFacilitador.getValue();
        if (facilitador == null) {
            return;
        }

        String query = "SELECT c.idCliente, c.nombre, c.apellido, COUNT(t.idTransaccion) AS 'CantidadTransaccion', " +
                "SUM(t.totalMonto) AS 'TotalGastado', tr.facilitador " +
                "FROM Transaccion t " +
                "INNER JOIN Cliente c ON t.idCliente = c.idCliente " +
                "INNER JOIN tarjeta tr ON t.idCliente = tr.idCliente " +
                "WHERE tr.facilitador = ? " +
                "GROUP BY c.idCliente, c.nombre, c.apellido, tr.facilitador";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, facilitador);
            ResultSet rs = stmt.executeQuery();
            ObservableList<Cliente> datos = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int cantidadCompras = rs.getInt("CantidadTransaccion");
                double totalGastado = rs.getDouble("TotalGastado");
                datos.add(new Cliente(id, nombre, apellido, cantidadCompras, totalGastado));
            }
            tbMostrarDatos.setItems(datos);
            guardarReporte(facilitador, datos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        comboBoxFacilitador.setValue(null);
        tbMostrarDatos.setItems(FXCollections.observableArrayList());
    }

    private void salir() {
        // Close the window or application
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    private void guardarReporte(String facilitador, ObservableList<Cliente> datos) {
        StringBuilder reporte = new StringBuilder();
        for (Cliente dato : datos) {
            reporte.append("Cliente ID: ").append(dato.getIdCliente());
            reporte.append(", Nombre: ").append(dato.getNombre());
            reporte.append(", Apellido: ").append(dato.getApellido());
            reporte.append(", Compras: ").append(dato.getCantidadCompras());
            reporte.append(", Total gastado: ").append(dato.getTotalGastado());
            reporte.append("\n");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reportes/Reporte D - " + facilitador + " - " + java.time.LocalDateTime.now() + ".txt"))) {
            writer.write(reporte.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCleanData(){ // 00018523 Este método se ejecutará cuando se presione el botón btnCleanData
        limpiarDatos(comboBoxFacilitador, tbMostrarDatos); // 00083823 Llama a la función estática de la clase CleanData
    }

    private void limpiarDatos(ComboBox<String> comboBoxFacilitador, TableView<Cliente> tbMostrarDatos) {
        onCleanData();
    }

    @FXML
    public void onBackAction(ActionEvent event){ // 00018523 Este método se ejecutará cuando se presione el botón btnBack
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00083823 Obtiene la ventana actual
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml"); // 00083823 Cambia la escena a la pantalla principal
    }*/

