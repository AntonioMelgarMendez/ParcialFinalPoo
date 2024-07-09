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
import org.example.proyecto.Utilities.SaveTXT;
import org.example.proyecto.Utilities.SceneChanger;
import java.sql.*;

public class ReportDController {

    @FXML
    private ComboBox<String> comboBoxFacilitador;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnSalir;

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
    public void initialize() {
        comboBoxFacilitador.setItems(FXCollections.observableArrayList("Visa", "MasterCard", "AmericanExpress"));

        tbidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tbidTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        tbFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador"));
        tbCantCompras.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras"));
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));

        tbMostrarDatos.setPlaceholder(new Label("No hay datos para mostrar"));
    }

    @FXML
    public void onBuscar() {
        String facilitador = comboBoxFacilitador.getValue();
        if (facilitador == null || facilitador.isEmpty()) {
            showAlert("ERROR", "Facilitador no seleccionado", "Por favor, seleccione un facilitador.");
            return;
        }

        ObservableList<TarjetaXTransaccion> dataList = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(
                DataBaseCredentials.getInstance().getUrl(),
                DataBaseCredentials.getInstance().getUsername(),
                DataBaseCredentials.getInstance().getPassword())) {

            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                ps1.executeUpdate();
            }
            String query = "SELECT c.idCliente AS 'ID Cliente', t.idTransaccion AS 'ID Transaccion', " +
                    "tr.facilitador AS 'Facilitador', COUNT(t.idTransaccion) AS 'Cantidad de Compras', " +
                    "SUM(t.totalMonto) AS 'Total' " +
                    "FROM transaccion t " +
                    "INNER JOIN cliente c ON t.idCliente = c.idCliente " +
                    "INNER JOIN tarjeta tr ON c.idCliente = tr.idCliente " +
                    "WHERE tr.facilitador = ? " +
                    "GROUP BY c.idCliente, t.idTransaccion, tr.facilitador";

            PreparedStatement ps2 = conn.prepareStatement(query);
            ps2.setString(1, facilitador);
            ResultSet rs = ps2.executeQuery();

            while (rs.next()) {
                TarjetaXTransaccion clienteTransaccion = new TarjetaXTransaccion(
                        rs.getInt("ID Cliente"),
                        rs.getInt("ID Transaccion"),
                        rs.getString("Facilitador"),
                        rs.getInt("Cantidad de Compras"),
                        rs.getDouble("Total")
                );
                dataList.add(clienteTransaccion);
            }

            tbMostrarDatos.setItems(dataList);

            if (!dataList.isEmpty()) {
                int idCliente = dataList.get(0).getIdCliente();
                SaveTXT.SaveDReport(idCliente, dataList, "reports");
            }

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
}
