package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;
import org.example.proyecto.Tables.Transaccion;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TransaccionController implements Initializable {

    @FXML private TextField idTransaccionFieldInsert;
    @FXML private TextField idClienteField;
    @FXML private DatePicker fechaCompraPickerInsert;
    @FXML private TextField totalMontoField;
    @FXML private TextField descripcionField;
    @FXML private TableView<Transaccion> tableView;
    @FXML private TableColumn<Transaccion, Integer> idTransaccionCol;
    @FXML private TableColumn<Transaccion, Integer> idClienteCol;
    @FXML private TableColumn<Transaccion, Date> fechaCompraCol;
    @FXML private TableColumn<Transaccion, Double> totalMontoCol;
    @FXML private TableColumn<Transaccion, String> descripcionCol;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectDatabase();
        initializeTable();
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection(
                    DataBaseCredentials.getInstance().getUrl(),
                    DataBaseCredentials.getInstance().getUsername(),
                    DataBaseCredentials.getInstance().getPassword()
            );
            // Selecciona la base de datos
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("USE " + DataBaseCredentials.getInstance().getDatabase());
                System.out.println("Tabla on");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() {
        idTransaccionCol.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        idClienteCol.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        fechaCompraCol.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        totalMontoCol.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));
        descripcionCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }


    @FXML
    private void insertarTransaccion(ActionEvent event) {
        if (validateInputs()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transaccion (idTransaccion, idCliente, fecha_compra, totalMonto, descripcion) VALUES (?, ?, ?, ?, ?)")) {
                statement.setInt(1, Integer.parseInt(idTransaccionFieldInsert.getText()));
                statement.setInt(2, Integer.parseInt(idClienteField.getText()));
                statement.setDate(3, Date.valueOf(fechaCompraPickerInsert.getValue()));
                statement.setDouble(4, Double.parseDouble(totalMontoField.getText()));
                statement.setString(5, descripcionField.getText());
                statement.executeUpdate();
                mostrarTabla(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void modificarTransaccion(ActionEvent event) {
        if (validateInputs()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE transaccion SET idCliente = ?, fecha_compra = ?, totalMonto = ?, descripcion = ? WHERE idTransaccion = ?")) {
                statement.setInt(1, Integer.parseInt(idClienteField.getText()));
                statement.setDate(2, Date.valueOf(fechaCompraPickerInsert.getValue()));
                statement.setDouble(3, Double.parseDouble(totalMontoField.getText()));
                statement.setString(4, descripcionField.getText());
                statement.setInt(5, Integer.parseInt(idTransaccionFieldInsert.getText()));
                statement.executeUpdate();
                mostrarTabla(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void eliminarTransaccion(ActionEvent event) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM transaccion WHERE idTransaccion = ?")) {
            statement.setInt(1, Integer.parseInt(idTransaccionFieldInsert.getText()));
            statement.executeUpdate();
            mostrarTabla(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarTabla(ActionEvent event) {
        try {
            tableView.getItems().clear();
            String sql = "SELECT * FROM transaccion";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Transaccion transaccion = new Transaccion(
                        resultSet.getInt("idTransaccion"),
                        resultSet.getDate("fecha_compra"),
                        resultSet.getDouble("totalMonto"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("idCliente")
                );
                tableView.getItems().add(transaccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        idTransaccionFieldInsert.clear();
        idClienteField.clear();
        fechaCompraPickerInsert.setValue(null);
        totalMontoField.clear();
        descripcionField.clear();
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");
    }

    private boolean validateInputs() {
        if (idTransaccionFieldInsert.getText().isEmpty() || idClienteField.getText().isEmpty() ||
                fechaCompraPickerInsert.getValue() == null || totalMontoField.getText().isEmpty() ||
                descripcionField.getText().isEmpty()) {
            AlertsManager.showAlert("Campos vacíos", "Todos los campos son obligatorios.","Los campos estan vacios");
            return false;
        }
        try {
            Integer.parseInt(idTransaccionFieldInsert.getText());
            Integer.parseInt(idClienteField.getText());
            Double.parseDouble(totalMontoField.getText());
        } catch (NumberFormatException e) {
            AlertsManager.showAlert("Formato inválido", "Asegúrate de que los campos numéricos tengan el formato correcto.","Los numeros no tienen el formato correcto");
            return false;
        }
        return true;
    }


}
