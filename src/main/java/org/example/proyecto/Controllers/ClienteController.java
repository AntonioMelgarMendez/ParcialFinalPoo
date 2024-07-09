package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyecto.Tables.Cliente;
import org.example.proyecto.Tables.Transaccion;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;
import org.example.proyecto.Utilities.SelectManager;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClienteController{

    @FXML private TextField idCliente;
    @FXML private TextField Nombre;
    @FXML private TextField Direccion;
    @FXML private TextField telefono;
    @FXML private TextField apellido;
    @FXML private TableView<Cliente> tableView;
    @FXML private TableColumn<Cliente, Integer> idClienteCol;
    @FXML private TableColumn<Cliente, String> NombreCol;
    @FXML private TableColumn<Cliente, String> DireccionCol;
    @FXML private TableColumn<Cliente, String> TelefonoCol;
    @FXML private TableColumn<Cliente, String> ApellidoCol;
    public void initialize(){
        DataBaseCredentials.getInstance().connectDatabase();
        initializeTable();
    }
    @FXML
    private void deleteClient(){
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement("DELETE FROM cliente WHERE idCliente = ?")) {
            statement.setInt(1, Integer.parseInt(idCliente.getText()));  // 00038623 Asigna el ID de transacción para identificar la fila a eliminar.
            statement.executeUpdate();  // 00038623 Ejecuta la eliminación en la base de datos.
            mostrartabla(); // 00038623 Actualiza la tabla en la interfaz gráfica.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void updateClient(){
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement(
                "UPDATE Cliente SET idCliente = ?, nombre = ?, apellido = ?, direccion = ?,telefono=? WHERE idCliente = ?")) {
            statement.setInt(1, Integer.parseInt(idCliente.getText()));  // 00038623 Asigna el nuevo ID de cliente desde el campo de texto.
            statement.setString(2, (Nombre.getText()));  // 00038623 Asigna el nuevo ID de cliente desde el campo de texto.
            statement.setString(3, (apellido.getText()));
            statement.setString(4, (Direccion.getText()));
            statement.setString(5, (telefono.getText()));
            statement.setInt(6, Integer.parseInt(idCliente.getText()));  // 00038623 Asigna el nuevo ID de cliente desde el campo de texto.
            statement.executeUpdate();  // 00038623 Ejecuta la actualización en la base de datos.
            mostrartabla();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void insertClient(){
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement(
                "INSERT INTO cliente (idCliente, nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?, ?)")) {
            statement.setInt(1, Integer.parseInt(idCliente.getText()));  // 00038623 Asigna el ID de transacción desde el campo de texto.
            statement.setString(2, (Nombre.getText()));  // 00038623 Asigna el ID de cliente desde el campo de texto.
            statement.setString(3, (apellido.getText()));
            statement.setString(4, (Direccion.getText()));
            statement.setString(5, (telefono.getText()));
            statement.executeUpdate();  // 00038623 Ejecuta la inserción de datos en la base de datos.
            mostrartabla();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void mostrartabla(){
        try {
            tableView.getItems().clear();  // 00009123 Limpiamos todos los elementos actuales en la tabla.
            String sql = "SELECT * FROM cliente";  // 00009123 Consultamos todos los registros de la tabla transaccion.
            Statement statement = DataBaseCredentials.getInstance().getConnection().createStatement();//00009123 Establecemos la conexion
            ResultSet resultSet = statement.executeQuery(sql);  // 00009123 Ejecuta la consulta y obtiene el conjunto de resultados.

            while (resultSet.next()) {// 00009123 Iteramos todos los valores
                Cliente cliente = new Cliente(
                        resultSet.getInt("idCliente"),  // 00009123  Obtenemos el id de cliente
                        resultSet.getString("nombre"),  // 00009123 Obtiene el nombre del cliente
                        resultSet.getString("apellido"),  // 00009123 Obtiene el apellido del cliente
                        resultSet.getString("direccion"),  // 00009123 Obtiene la direccion
                        resultSet.getString("telefono")  // 00009123 Obtiene el telefono del cliente
                );
                tableView.getItems().add(cliente);  // 00009123 Agrega la transacción a la tabla en la interfaz gráfica.
            }
        } catch (SQLException e) {
            AlertsManager.showAlert("Error","Se ha detectado un error","Error:"+e.getMessage());//00009123 Mostramos una alerta con error
        }
    }
    private void initializeTable() {
        NombreCol.setCellValueFactory(new PropertyValueFactory<>("Nombre"));  // 00009123 Asigna el valor de la columna Nombre
        idClienteCol.setCellValueFactory(new PropertyValueFactory<>("IdCliente"));  // 00009123 Asigna el valor de la columna idCliente
        ApellidoCol.setCellValueFactory(new PropertyValueFactory<>("Apellido"));  // 00009123 Asigna el valor de la columna Apellido
        TelefonoCol.setCellValueFactory(new PropertyValueFactory<>("Telefono")); // 00009123 Asigna el valor de la columna Telefono
        DireccionCol.setCellValueFactory(new PropertyValueFactory<>("Direccion"));  // 00009123 Asigna el valor de la columna Direccion
    }
    @FXML
    private void limpiarCampos(ActionEvent event) { //00009123 Limpiar todos los campos
        idCliente.clear();  // 00009123 Limpia el campo de id cliente
        Nombre.clear();  // 00009123 Limpia el campo de nombre
        Direccion.clear();  // 00009123 Borra la direccion
        telefono.clear();  // 00009123 Limpia el campo de telefono
        apellido.clear();  // 00009123 Limpia el campo de apellido
    }

    @FXML
    private void volver(ActionEvent event) { //00009123 Regresa al menu de opciones
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // 00009123 Obtenemos la ventana actual.
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");  // 00009123 Llamamos a la clase de cambio de escena
    }

}
