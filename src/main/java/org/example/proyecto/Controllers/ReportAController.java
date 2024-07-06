package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportAController {

    @FXML
    private TextField idCliente;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private TableView<Transaccion> tableView;
    @FXML
    private TableColumn<Transaccion, Integer> columnIdCliente;
    @FXML
    private TableColumn<Transaccion, Integer> columnIdTransaccion;
    @FXML
    private TableColumn<Transaccion, Date> columnFechaCompra;
    @FXML
    private TableColumn<Transaccion, Double> columnTotalMonto;
    @FXML
    private TableColumn<Transaccion, String> columnDescripcion;

    private ObservableList<Transaccion> transaccionList;

    @FXML
    private void initialize() {
        // Initialize the TableView columns
        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        columnFechaCompra.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        columnTotalMonto.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }
    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml");
    }
    @FXML
    private void limpiarCampos() {
        idCliente.clear(); // Limpia el campo de texto del ID del cliente
        fechaInicio.setValue(null); // Limpia la selección de fecha de inicio
        fechaFin.setValue(null); // Limpia la selección de fecha de fin
        tableView.getItems().clear(); // Limpia la tabla de transacciones

    }


    @FXML
    private void generateList() {
        String idClienteText = idCliente.getText();
        LocalDate inicioDate = fechaInicio.getValue();
        LocalDate finDate = fechaFin.getValue();

        if (idClienteText.isEmpty() || inicioDate == null || finDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("espacios sin llenar");
            alert.show();
            return;
        }

        int idCliente = Integer.parseInt(idClienteText);
        Date startDate = Date.valueOf(inicioDate);
        Date endDate = Date.valueOf(finDate);

        transaccionList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbSistemaBanco", "root", "yaelgaymer")) {
            String query = "SELECT * FROM transaccion WHERE idCliente = ? AND fecha_compra BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setDate(2, startDate);
            preparedStatement.setDate(3, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idTransaccion = resultSet.getInt("idTransaccion");
                Date fechaCompra = resultSet.getDate("fecha_compra");
                double totalMonto = resultSet.getDouble("totalMonto");
                String descripcion = resultSet.getString("descripcion");

                Transaccion transaccion = new Transaccion(idTransaccion, fechaCompra, totalMonto, descripcion, idCliente);
                transaccionList.add(transaccion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("no se encuentra en la base de datos");
            alert.show();
        }

        tableView.setItems(transaccionList);
    }
}
