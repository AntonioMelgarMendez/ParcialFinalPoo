package org.example.proyecto.Controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReportBController {

    @FXML
    private TextField idCliente;
    @FXML
    private ChoiceBox<String> mesChoiceBox;
    @FXML
    private ChoiceBox<String> anoChoiceBox;
    @FXML
    private Label totalLabel;

    @FXML
    private void Back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");
    }

    @FXML
    private void limpiarCampos() {
        idCliente.clear();
        mesChoiceBox.getSelectionModel().clearSelection();
        anoChoiceBox.getSelectionModel().clearSelection();
        totalLabel.setText("0");
    }

    @FXML
    private void calcularGasto() {
        String idClienteText = idCliente.getText();
        String mesString = mesChoiceBox.getValue();
        String year = anoChoiceBox.getValue();

        if (idClienteText.isEmpty() || mesString == null || year == null) {
            AlertsManager.showAlert("Campos vacios","Faltan datos","Por favor ingrese todos los compos");
            return;
        }

        int idCliente = Integer.parseInt(idClienteText);
        int anoInt = Integer.parseInt(year);
        // Mapeo de nombres de meses a números de mes
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int mesInt = Arrays.asList(meses).indexOf(mesString) + 1; // Sumar 1 porque los meses en Java comienzan en 1
        double totalGasto = 0;
        CalcularTotal(idCliente,anoInt,mesInt,totalGasto);
    }
    private void CalcularTotal(int idCliente, int anoInt, int mesInt, double totalGasto){
        try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) {
            try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                ps1.executeUpdate();
            }
            String query = "SELECT SUM(totalMonto) AS total FROM transaccion WHERE idCliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setInt(2, mesInt);
            preparedStatement.setInt(3, anoInt);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalGasto = resultSet.getDouble("total");
            }
            if(totalGasto ==0){
                totalLabel.setText("No hay compras");
            }
            else {
                totalLabel.setText(String.format("%.2f", totalGasto));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AlertsManager.showAlert("Error calculando el total","Se ha detectado un erro","Ha ocurrido un error "+e.getMessage());
        }

    }


    @FXML
    private void initialize() {
        // Opciones para el mes
        List<String> meses = Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        mesChoiceBox.setItems(FXCollections.observableArrayList(meses));

        // Opciones para el año (por ejemplo, los últimos 10 años)
        int year = LocalDate.now().getYear();
        List<String> anos = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            anos.add(String.valueOf(year - i));
        }
        anoChoiceBox.setItems(FXCollections.observableArrayList(anos));
    }
}