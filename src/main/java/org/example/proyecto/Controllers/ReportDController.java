package org.example.proyecto.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyecto.Tables.TarjetaXTransaccion;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;
import static org.example.proyecto.Utilities.SaveTXT.SaveCReport;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class ReportDController {

    @FXML
    private ChoiceBox<String> comboBoxFacilitador;

    @FXML
    private TableView<TarjetaXTransaccion> tbMostrarDatos;

    @FXML
    private TableColumn<TarjetaXTransaccion, Integer> tbidCliente;

    @FXML
    private TableColumn<TarjetaXTransaccion, Integer> tbidTransaccion;

    @FXML
    private TableColumn<TarjetaXTransaccion, String> tbFacilitador;

    @FXML
    private TableColumn<TarjetaXTransaccion, Integer> tbCantCompras;

    @FXML
    private TableColumn<TarjetaXTransaccion, Double> tbTotal;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnSalir;

    // Initialize the database connection
    private Connection connection;

    public ReportDController() {
        try {
            connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        List<String> marcas = Arrays.asList("Visa", "MasterCard", "AmericanExpress");
        comboBoxFacilitador.setItems(FXCollections.observableArrayList(marcas));
        loadFacilitadores();
        setupTableColumns();
    }

    private void loadFacilitadores() {
        try {
            String query = "SELECT DISTINCT facilitador FROM tarjeta";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                comboBoxFacilitador.getItems().add(rs.getString("facilitador"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        tbidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tbidTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        tbFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador"));
        tbCantCompras.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras"));
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));
    }

    @FXML
    void onBuscar(ActionEvent event) {
        String selectedFacilitador = comboBoxFacilitador.getSelectionModel().getSelectedItem();
        if (selectedFacilitador != null) {
            loadTransacciones(selectedFacilitador);
        }
    }

    private void loadTransacciones(String facilitador) {
        ObservableList<TarjetaXTransaccion> transacciones = FXCollections.observableArrayList();
        try {
            String query = "SELECT c.idCliente, t.idTransaccion, ta.facilitador, COUNT(t.idTransaccion) AS cantidadCompras, SUM(t.totalMonto) AS totalMonto " +
                    "FROM cliente c " +
                    "JOIN tarjeta ta ON c.idCliente = ta.idCliente " +
                    "JOIN transaccion t ON c.idCliente = t.idCliente " +
                    "WHERE ta.facilitador = ? " +
                    "GROUP BY c.idCliente, t.idTransaccion, ta.facilitador";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, facilitador);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TarjetaXTransaccion transaccion = new TarjetaXTransaccion(
                        rs.getInt("idCliente"),
                        rs.getInt("idTransaccion"),
                        rs.getString("facilitador"),
                        rs.getInt("cantidadCompras"),
                        rs.getDouble("totalMonto")
                );
                transacciones.add(transaccion);
            }
            tbMostrarDatos.setItems(transacciones);
            //SaveCReport(,transacciones);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        comboBoxFacilitador.getSelectionModel().clearSelection();
        tbMostrarDatos.getItems().clear();
    }

    @FXML
    void onVolver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");
    }
}
