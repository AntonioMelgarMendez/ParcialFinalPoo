package org.example.proyecto.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.example.proyecto.Controllers.Utilities.DataBaseCredentials;
import org.example.proyecto.Controllers.Utilities.SaveTXT;
import org.example.proyecto.Controllers.Utilities.SceneChanger;
import org.example.proyecto.Controllers.Utilities.Transaccion;

import java.sql.*;
import java.time.LocalDate;

public class ReportAController { // 00038623 Declara la clase ReportAController

    @FXML // 00038623 Anotación FXML para vincular el campo con el componente del FXML
    private TextField idCliente; // 00038623 Campo para el ID del cliente
    @FXML
    private DatePicker fechaInicio; // 00038623 Campo para la fecha de inicio
    @FXML
    private DatePicker fechaFin; // 00038623 Campo para la fecha de fin
    @FXML
    private TableView<Transaccion> tableView; // 00038623 Tabla para mostrar transacciones
    @FXML
    private TableColumn<Transaccion, Integer> columnIdCliente; // 00038623 Columna para el ID del cliente
    @FXML
    private TableColumn<Transaccion, Integer> columnIdTransaccion; // 00038623 Columna para el ID de la transacción
    @FXML
    private TableColumn<Transaccion, Date> columnFechaCompra; // 00038623 Columna para la fecha de compra
    @FXML
    private TableColumn<Transaccion, Double> columnTotalMonto; // 00038623 Columna para el monto total
    @FXML
    private TableColumn<Transaccion, String> columnDescripcion; // 00038623 Columna para la descripción

    private ObservableList<Transaccion> transaccionList; // 00038623 Lista observable para manejar las transacciones

    @FXML // 00038623 Método anotado con FXML para ejecutar al inicializar el controlador
    private void initialize() {
        // Inicializa las columnas de la TableView
        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente")); // 00038623 Mapea la propiedad idCliente a la columna columnIdCliente
        columnIdTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion")); // 00038623 Mapea la propiedad idTransaccion a la columna columnIdTransaccion
        columnFechaCompra.setCellValueFactory(new PropertyValueFactory<>("fechaCompra")); // 00038623 Mapea la propiedad fechaCompra a la columna columnFechaCompra
        columnTotalMonto.setCellValueFactory(new PropertyValueFactory<>("totalMonto")); // 00038623 Mapea la propiedad totalMonto a la columna columnTotalMonto
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion")); // 00038623 Mapea la propiedad descripcion a la columna columnDescripcion
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de volver
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml"); // 00038623 Cambia la escena a la pantalla principal
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de limpiar
    private void limpiarCampos() {
        idCliente.clear(); // 00038623 Limpia el campo de texto del ID del cliente
        fechaInicio.setValue(null); // 00038623 Limpia la selección de fecha de inicio
        fechaFin.setValue(null); // 00038623 Limpia la selección de fecha de fin
        tableView.getItems().clear(); // 00038623 Limpia la tabla de transacciones
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de generar lista
    private void generateList() {
        String idClienteText = idCliente.getText(); // 00038623 Obtiene el texto del campo ID del cliente
        LocalDate inicioDate = fechaInicio.getValue(); // 00038623 Obtiene la fecha de inicio seleccionada
        LocalDate finDate = fechaFin.getValue(); // 00038623 Obtiene la fecha de fin seleccionada

        if (idClienteText.isEmpty() || inicioDate == null || finDate == null) { // 00038623 Verifica si algún campo está vacío
            AlertsManager.showAlert("ERROR","falta informacion","llena todos los campos");// 00038623 Muestra la alerta

            return; // 00038623 Sale del método si hay campos vacíos
        }

        int idCliente = Integer.parseInt(idClienteText); // 00038623 Convierte el texto del ID del cliente a entero
        Date startDate = Date.valueOf(inicioDate); // 00038623 Convierte la fecha de inicio a tipo Date de SQL
        Date endDate = Date.valueOf(finDate); // 00038623 Convierte la fecha de fin a tipo Date de SQL

        transaccionList = FXCollections.observableArrayList(); // 00038623 Inicializa la lista observable de transacciones
        try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) { // 00038623 Establece una conexión con la base de datos
            try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00038623 Cambia a la base de datos específica
                ps1.executeUpdate(); // 00038623 Ejecuta la actualización para usar la base de datos
            }
            String query = "SELECT * FROM transaccion WHERE idCliente = ? AND fecha_compra BETWEEN ? AND ?"; // 00038623 Define la consulta SQL
            PreparedStatement preparedStatement = connection.prepareStatement(query); // 00038623 Prepara la consulta SQL
            preparedStatement.setInt(1, idCliente); // 00038623 Establece el valor del primer parámetro (ID del cliente)
            preparedStatement.setDate(2, startDate); // 00038623 Establece el valor del segundo parámetro (fecha de inicio)
            preparedStatement.setDate(3, endDate); // 00038623 Establece el valor del tercer parámetro (fecha de fin)

            ResultSet resultSet = preparedStatement.executeQuery(); // 00038623 Ejecuta la consulta y obtiene el resultado
            while (resultSet.next()) { // 00038623 Itera sobre los resultados de la consulta
                int idTransaccion = resultSet.getInt("idTransaccion"); // 00038623 Obtiene el ID de la transacción
                Date fechaCompra = resultSet.getDate("fecha_compra"); // 00038623 Obtiene la fecha de compra
                double totalMonto = resultSet.getDouble("totalMonto"); // 00038623 Obtiene el monto total
                String descripcion = resultSet.getString("descripcion"); // 00038623 Obtiene la descripción

                Transaccion transaccion = new Transaccion(idTransaccion, fechaCompra, totalMonto, descripcion, idCliente); // 00038623 Crea una nueva instancia de Transaccion
                transaccionList.add(transaccion); // 00038623 Agrega la transacción a la lista observable

            }
            SaveTXT.SaveAReport(Integer.toString(idCliente),inicioDate,finDate,transaccionList);
        } catch (SQLException e) {// 00038623 Captura las excepciones SQL
            e.printStackTrace();//00038623 error de excepcion
            AlertsManager.showAlert("ERROR","ERROR","No se encuentra en la base de datos");// 00038623 Muestra la alerta

        }

        tableView.setItems(transaccionList); //00038623 pone los datos en la lista transaccionList :)

    }
}
