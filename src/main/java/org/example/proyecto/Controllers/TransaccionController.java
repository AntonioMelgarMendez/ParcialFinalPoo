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
        connectDatabase();  // 00038623 Llama al método para conectar a la base de datos.
        initializeTable();  //00038623 Llama al método para inicializar la tabla en la interfaz gráfica.
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection(
                    DataBaseCredentials.getInstance().getUrl(),  // 00038623 Obtiene la URL de conexión desde las credenciales.
                    DataBaseCredentials.getInstance().getUsername(),  // 00038623 Obtiene el nombre de usuario de las credenciales.
                    DataBaseCredentials.getInstance().getPassword()  // 00038623 Obtiene la contraseña de las credenciales.
            );
            // Selecciona la base de datos
            try (Statement stmt = connection.createStatement()) { //00038623 try catch para conectar a la base de datos en caso que no haya
                stmt.execute("USE " + DataBaseCredentials.getInstance().getDatabase());  // 00038623 Selecciona la base de datos especificada en las credenciales.
                System.out.println("Tabla on");  // 00038623 Imprime un mensaje de confirmación en la consola.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() {
        idTransaccionCol.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));  // 00038623 Asigna el valor de la propiedad para la columna de ID de transacción.
        idClienteCol.setCellValueFactory(new PropertyValueFactory<>("idCliente"));  // 00038623 Asigna el valor de la propiedad para la columna de ID de cliente.
        fechaCompraCol.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));  // 00038623 Asigna el valor de la propiedad para la columna de fecha de compra.
        totalMontoCol.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));  // 00038623 Asigna el valor de la propiedad para la columna de monto total.
        descripcionCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));  // 00038623 Asigna el valor de la propiedad para la columna de descripción.
    }


    @FXML
    private void insertarTransaccion(ActionEvent event) {
        if (validarInputs()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transaccion (idTransaccion, idCliente, fecha_compra, totalMonto, descripcion) VALUES (?, ?, ?, ?, ?)")) {
                statement.setInt(1, Integer.parseInt(idTransaccionFieldInsert.getText()));  // 00038623 Asigna el ID de transacción desde el campo de texto.
                statement.setInt(2, Integer.parseInt(idClienteField.getText()));  // 00038623 Asigna el ID de cliente desde el campo de texto.
                statement.setDate(3, Date.valueOf(fechaCompraPickerInsert.getValue()));  // 00038623 Asigna la fecha de compra desde el selector de fecha.
                statement.setDouble(4, Double.parseDouble(totalMontoField.getText()));  // 00038623 Asigna el monto total desde el campo de texto.
                statement.setString(5, descripcionField.getText());  // 00038623 Asigna la descripción desde el campo de texto.
                statement.executeUpdate();  // 00038623 Ejecuta la inserción de datos en la base de datos.
                mostrarTabla(null);  // 00038623 Actualiza la tabla en la interfaz gráfica.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void modificarTransaccion(ActionEvent event) { //00038623 funcion para el boton de modificar
        if (validarInputs()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE transaccion SET idCliente = ?, fecha_compra = ?, totalMonto = ?, descripcion = ? WHERE idTransaccion = ?")) {
                statement.setInt(1, Integer.parseInt(idClienteField.getText()));  // 00038623 Asigna el nuevo ID de cliente desde el campo de texto.
                statement.setDate(2, Date.valueOf(fechaCompraPickerInsert.getValue()));  // 00038623 Asigna la nueva fecha de compra desde el selector de fecha.
                statement.setDouble(3, Double.parseDouble(totalMontoField.getText()));  // 00038623 Asigna el nuevo monto total desde el campo de texto.
                statement.setString(4, descripcionField.getText());  // 00038623 Asigna la nueva descripción desde el campo de texto.
                statement.setInt(5, Integer.parseInt(idTransaccionFieldInsert.getText()));  // 00038623 Asigna el ID de transacción para identificar la fila a actualizar.
                statement.executeUpdate();  // 00038623 Ejecuta la actualización en la base de datos.
                mostrarTabla(null);  // 00038623 Actualiza la tabla en la interfaz gráfica.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void eliminarTransaccion(ActionEvent event) { // 00038623 funcion para el boton de eliminar
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM transaccion WHERE idTransaccion = ?")) {
            statement.setInt(1, Integer.parseInt(idTransaccionFieldInsert.getText()));  // 00038623 Asigna el ID de transacción para identificar la fila a eliminar.
            statement.executeUpdate();  // 00038623 Ejecuta la eliminación en la base de datos.
            mostrarTabla(null);  // 00038623 Actualiza la tabla en la interfaz gráfica.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarTabla(ActionEvent event) { //00038623 funcion para mostrar la tabla de la BD
        try {
            tableView.getItems().clear();  // 00038623 Limpia los elementos actuales en la tabla.
            String sql = "SELECT * FROM transaccion";  // 00038623 Consulta SQL para seleccionar todos los registros de la tabla transaccion.
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);  // 00038623 Ejecuta la consulta y obtiene el conjunto de resultados.

            while (resultSet.next()) {
                Transaccion transaccion = new Transaccion(
                        resultSet.getInt("idTransaccion"),  // 00038623 Obtiene el ID de transacción del resultado.
                        resultSet.getDate("fecha_compra"),  // 00038623 Obtiene la fecha de compra del resultado.
                        resultSet.getDouble("totalMonto"),  // 00038623 Obtiene el monto total del resultado.
                        resultSet.getString("descripcion"),  // 00038623 Obtiene la descripción del resultado.
                        resultSet.getInt("idCliente")  // 00038623 Obtiene el ID de cliente del resultado.
                );
                tableView.getItems().add(transaccion);  // 00038623 Agrega la transacción a la tabla en la interfaz gráfica.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos(ActionEvent event) { //0038623 funcion para el boton para limpiar los campos
        idTransaccionFieldInsert.clear();  // 00038623 Limpia el campo de ID de transacción.
        idClienteField.clear();  // 00038623 Limpia el campo de ID de cliente.
        fechaCompraPickerInsert.setValue(null);  // 00038623 Borra la fecha seleccionada en el selector de fecha.
        totalMontoField.clear();  // 00038623 Limpia el campo de monto total.
        descripcionField.clear();  // 00038623 Limpia el campo de descripción.
    }

    @FXML
    private void volver(ActionEvent event) { //00038623 funcion para volver al menu de opciones para modificar las tablas
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // 00038623 Obtiene la ventana actual desde el evento.
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");  // 00038623 Cambia a la escena principal.
    }

    private boolean validarInputs() { // 00038623 funcion para validar que los campos esten vacios o llenos
        if (idTransaccionFieldInsert.getText().isEmpty() || idClienteField.getText().isEmpty() ||
                fechaCompraPickerInsert.getValue() == null || totalMontoField.getText().isEmpty() ||
                descripcionField.getText().isEmpty()) { //00038623 verifica si tienen algun espacio vacio
            AlertsManager.showAlert("Campos vacíos", "Todos los campos son obligatorios.","Los campos estan vacios");  // 00038623 Muestra una alerta si hay campos vacíos.
            return false; //00038623 retorna falso y retorna la alerta
        }
        try {
            Integer.parseInt(idTransaccionFieldInsert.getText());  // 00038623 Intenta convertir el ID de transacción a entero.
            Integer.parseInt(idClienteField.getText());  // 00038623 Intenta convertir el ID de cliente a entero.
            Double.parseDouble(totalMontoField.getText());  // 00038623 Intenta convertir el monto total a double.
        } catch (NumberFormatException e) { //00038623 verifica si el formato es valido 
            AlertsManager.showAlert("Formato inválido", "Asegúrate de que los campos numéricos tengan el formato correcto.","Los numeros no tienen el formato correcto");  // 00038623 Muestra una alerta si los formatos numéricos no son válidos.
            return false;//00038623 retorna falso y retorna la alerta
        }
        return true;
    }
}
