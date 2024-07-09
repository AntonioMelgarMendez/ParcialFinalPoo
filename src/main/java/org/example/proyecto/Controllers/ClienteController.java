package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyecto.Tables.Cliente;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;
import java.sql.*;


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
        DataBaseCredentials.getInstance().connectDatabase();// 00009123 Conectamos a la base de datos
        initializeTable();// 00009123 Inicializamos los valores de la tabla
    }
    private boolean validateIdCliente() {// 00009123 Comprobamos si el id cliente es un numeor
        if (idCliente.getText().isEmpty() || !idCliente.getText().matches("\\d+")) {
            AlertsManager.showAlert("Validación", "Error de validación", "El ID del cliente debe ser un número entero.");// 00009123 Mostramos una alerta anunciando que el formato no es correcto
            return false;// 00009123 Retornamos false
        }
        return true;// 00009123 Retornamos true en caso de que si sea valido
    }

    private boolean validateFields() {// 00009123 Comprobamos los demas campos
        if (!validateIdCliente()) return false;// 00009123 Retornamos false si la comprobacion del id no es correcta
        if (Nombre.getText().isEmpty() || apellido.getText().isEmpty() || Direccion.getText().isEmpty() || telefono.getText().isEmpty()) {// 00009123 Comprobamos si algun campo es vacio
            AlertsManager.showAlert("Validación", "Error de validación", "Todos los campos deben estar completos.");// 00009123 Mostramos una alerta de error
            return false;
        }

        if (!telefono.getText().matches("\\d{8}")) {// 00009123 Comprobamos si el telefono tiene una longitud de 8
            AlertsManager.showAlert("Validación", "Error de validación", "El número de teléfono debe tener 8 dígitos.");// 00009123 Mostramos alerta con error
            return false;
        }

        return true;// 00009123 Retornamos true en caso de que funcione
    }
    @FXML
    private void deleteClient(){// 00009123 Funcion para borrar a un cliente
        if(!validateIdCliente()) return;// 00009123 Si el id no esta en formato correcto paramos
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement("DELETE FROM cliente WHERE idCliente = ?")) {// 00009123 Cargamos el stament para seleccionar todo
            statement.setInt(1, Integer.parseInt(idCliente.getText()));  // 00009123 Asigna el ID de cliente para identificar la fila a eliminar.
            statement.executeUpdate();  // 00009123 Ejecuta la eliminación en la base de datos.
            mostrartabla(); // 00009123 Actualiza la tabla en la interfaz gráfica.
        } catch (SQLException e) {
            System.out.println(e.getMessage());// 00009123 Muestra un mensaje de error
        }
    }
    @FXML
    private void updateClient(){// 00009123 Actualiza los datos del cliente
        if(!validateFields()) return;// 00009123 Termina la ejecucion si los campos no son validos
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement(
                "UPDATE Cliente SET  nombre = ?, apellido = ?, direccion = ?,telefono=? WHERE idCliente = ?")) {// 00009123 Prepara el query para actualizar los datos
            statement.setString(1, (Nombre.getText()));  // 00009123 Asigna el nuevo nombre de cliente desde el campo de texto.
            statement.setString(2, (apellido.getText()));// 00009123 Asigna el nuevo apellido de cliente desde el campo de texto.
            statement.setString(3, (Direccion.getText()));// 00009123 Asigna la nueva direccion de cliente desde el campo de texto.
            statement.setString(4, (telefono.getText()));// 00009123 Asigna el nuevo numero de telefono del cliente desde el campo de texto.
            statement.setInt(5, Integer.parseInt(idCliente.getText()));  // 00009123 Asigna el id de cliente a buscar
            statement.executeUpdate();  // 00009123 Ejecuta la actualización en la base de datos.
            mostrartabla();//00009123 Actualizamos la tabla
        } catch (SQLException e) {
            System.out.println(e.getMessage());//00009123 Muestra un mensaje de error
        }

    }
    @FXML
    private void insertClient(){// 00009123 Inserta un nuevo cliente
        if(!validateFields()) return;// 00009123 Termina la ejecucion en caso de que los campos no esten correctos
        try (PreparedStatement statement = DataBaseCredentials.getInstance().getConnection().prepareStatement(
                "INSERT INTO cliente (idCliente, nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?, ?)")) {//00009123 Prepara la query a ejecutar
            statement.setInt(1, Integer.parseInt(idCliente.getText()));  // 00009123 Asigna el ID de cliente desde el campo de texto.
            statement.setString(2, (Nombre.getText()));  // 00009123 Asigna el nombre de cliente desde el campo de texto.
            statement.setString(3, (apellido.getText()));// 00009123 Asigna el apellido de cliente desde el campo de texto.
            statement.setString(4, (Direccion.getText()));// 00009123 Asigna la direccion del cliente desde el campo de texto.
            statement.setString(5, (telefono.getText()));// 00009123 Asigna el telefono del cliente desde el campo de texto.
            statement.executeUpdate();  // 00009123 Ejecuta la inserción de datos en la base de datos.
            mostrartabla();//00009123 Actualizamos la tabla
        } catch (SQLException e) {
            System.out.println(e.getMessage());//00009123 Mostramos un mensaje de error
        }
    }
    @FXML
    private void mostrartabla(){//00009123 Muestra todos los elementos de la tabla
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
