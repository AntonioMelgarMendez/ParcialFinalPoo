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
import static org.example.proyecto.Utilities.SaveTXT.SaveDReport;


public class ReportDController { // 00083823 Clase que actúa como controlador para la vista del Reporte D
    @FXML
    private ComboBox<String> comboBoxFacilitador; // 00083823 ComboBox para seleccionar el facilitador de tarjeta

    @FXML
    private Button btnBuscar; // 00083823 Botón para buscar los datos de las transacciones

    @FXML
    private Button btnLimpiar; // 00083823 Botón para limpiar los datos mostrados

    @FXML
    private Button btnSalir; // 00083823 Botón para Salir de la vista del reporte D

    @FXML
    private TableView<Cliente> tbMostrarDatos; // 00083823 TableView para mostrar los datos de los clientes

    @FXML
    private TableColumn<Cliente, Integer> tbidCliente; // 00083823 Columna para el ID del cliente

    @FXML
    private TableColumn<Cliente, String> tbNombre; // 00083823 Columna para el nombre del cliente

    @FXML
    private TableColumn<Cliente, String> tbApellido; // 00083823 Columna para el apellido del cliente

    @FXML
    private TableColumn<Cliente, Integer> tbCantCompras; // 00083823 Columna para la cantidad de compras realizadas por el cliente

    @FXML
    private TableColumn<Cliente, Double> tbTotal; // 00083823 Columna para el total gastado por el cliente

    @FXML
    public void initialize() {  //  00083823 Método de inicialización que se ejecuta automáticamente al cargar la vista

        comboBoxFacilitador.setItems(FXCollections.observableArrayList("Visa", "MasterCard", "AmericanExpress"));  // 00083823 Configura las opciones del ComboBox con los facilitadores disponibles

        // 00083823 Configura las columnas de la TableView para que tomen los datos correctos de la clase Cliente
        tbidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente")); // 00083823 Asocia la columna con el atributo idCliente de la clase Cliente
        tbNombre.setCellValueFactory(new PropertyValueFactory<>("nombre")); // 00083823 Asocia la columna con el atributo nombre de la clase Cliente
        tbApellido.setCellValueFactory(new PropertyValueFactory<>("apellido")); // 00083823 Asocia la columna con el atributo apellido de la clase Cliente
        tbCantCompras.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras")); // 00083823 Asocia la columna con el atributo cantidadCompras de la clase Cliente
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("totalGastado")); // 00083823 Asocia la columna con el atributo totalGastado de la clase Cliente


        tbMostrarDatos.setPlaceholder(new Label("No hay datos para mostrar")); // 00083823 Configura un mensaje de marcador de posición cuando no hay datos para mostrar en la TableView
    }

    @FXML
    public void onBuscar() { // 00083823 Método que se ejecuta cuando se presiona el botón Buscar
        String facilitador = comboBoxFacilitador.getValue(); // 00083823 Obtiene el valor seleccionado del ComboBox
        if (facilitador == null || facilitador.isEmpty()) { // 00083823  Verifica si no se ha seleccionado ningún facilitador y muestra una alerta en caso afirmativo
            showAlert("ERROR", "Facilitador no seleccionado", "Por favor, seleccione un facilitador."); // 00083823 Llama al método showAlert para mostrar el mensaje de error
            return; // 00083823 Sale del método si no se ha seleccionado ningún facilitador
        }

        ObservableList<Cliente> dataList = FXCollections.observableArrayList(); // 00083823 Lista observable para almacenar los datos que se mostrarán en la TableView
        try (Connection conn = DriverManager.getConnection( // 00083823 Intentar conectarse a la base de datos usando las credenciales almacenadas en DataBaseCredentials
                DataBaseCredentials.getInstance().getUrl(), // 00083823 URL de la base de datos
                DataBaseCredentials.getInstance().getUsername(), // 00083823 Nombre de usuario de la base de datos
                DataBaseCredentials.getInstance().getPassword())) { // 00083823 Contraseña de la base de datos

            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00083823 Cambia la base de datos a la especificada en las credenciales
                ps1.executeUpdate(); // 00083823 Ejecuta la actualización para cambiar la base de datos
            }
            String query = "SELECT c.idCliente AS 'ID Cliente', c.nombre AS Nombre, c.apellido AS Apellido, " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "COUNT(t.idTransaccion) AS 'Cantidad de Compras', SUM(t.totalMonto) AS Total " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "FROM transaccion t " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "INNER JOIN cliente c ON t.idCliente = c.idCliente " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "INNER JOIN tarjeta tr ON c.idCliente = tr.idCliente " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "WHERE tr.facilitador = ? " + // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones
                    "GROUP BY c.idCliente, c.nombre, c.apellido"; // 00083823 Consulta SQL para obtener los datos de los clientes y sus transacciones

            PreparedStatement ps2 = conn.prepareStatement(query); // 00083823 Prepara la consulta SQL y establece el parámetro del facilitador seleccionado
            ps2.setString(1, facilitador); // 00083823 Establece el valor del facilitador en la consulta SQL
            ResultSet rs = ps2.executeQuery(); // 00083823 Ejecuta la consulta SQL y obtiene los resultados

            while (rs.next()) { // 00083823 Itera sobre los resultados de la consulta
                Cliente cliente = new Cliente( // 00083823 Crea un objeto Cliente con los datos obtenidos de la consulta
                        rs.getInt("ID Cliente"), // 00083823 Obtiene el ID del cliente
                        rs.getString("Nombre"), // 00083823 Obtiene el Nombre del cliente
                        rs.getString("Apellido"), // 00083823 Obtiene el Apellido del cliente
                        rs.getInt("Cantidad de Compras"), // 00083823 Obtiene la Cantidad de Compras del cliente
                        rs.getDouble("Total") // 00083823 Obtiene el total gastado por el cliente
                );
                dataList.add(cliente); // 00083823 Añade el objeto Cliente a la lista observable
            }
            tbMostrarDatos.setItems(dataList); // 00083823 Establece los datos obtenidos en la TableView
                conn.close(); // 00083823 Cierra la conexión con la base de datos
                SaveDReport(facilitador, dataList); // 00083823 Guarda el reporte usando el método SaveDReport

        } catch (SQLException e) { // 00083823 Captura cualquier excepción SQL
            e.printStackTrace(); // 00083823 Imprime el stack trace de la excepción
        }
    }

    @FXML
    public void onLimpiar() { // 00083823 Limpia la selección del ComboBox y los datos mostrados en la TableView
        comboBoxFacilitador.setValue(null); // 00083823 Limpia la selección del ComboBox
        tbMostrarDatos.getItems().clear(); // 00083823 Limpia los datos mostrados en la TableView
    }

    @FXML
    public void onVolver(ActionEvent event) { // 00083823 Método que se ejecuta al presionar el botón "Volver". Este método cambia la escena actual a la vista principal.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00083823 Obtiene la ventana (Stage) actual a partir del evento de acción (event).
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml"); // 00083823 Cambia la escena actual a la vista principal utilizando el método changeScene de la clase SceneChanger.
    }

    private void showAlert(String title, String header, String content) { // 00083823 Método para mostrar una alerta con un mensaje de error
        Alert alert = new Alert(Alert.AlertType.ERROR); // 00083823 Crea un nuevo cuadro de alerta de tipo ERROR
        alert.setTitle(title); // 00083823 Establece el título de la alerta
        alert.setHeaderText(header); // 00083823 Establece el encabezado de la alerta
        alert.setContentText(content); // 00083823 Establece el contenido del mensaje de la alerta
        alert.showAndWait(); // 00083823 Muestra la alerta y espera a que el usuario la cierre
    }

    public void onFacilitadores(ActionEvent actionEvent) { // 00083823 Implementacion vacía del evecto de seleccion de facilitadores (No afecta en nada)
    }
}
