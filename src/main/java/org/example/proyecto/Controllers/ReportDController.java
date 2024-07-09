package org.example.proyecto.Controllers;  // 00083823 Define el paquete del controlador.

import javafx.fxml.FXML;  // 00083823 Importa las anotaciones de JavaFX para inyectar elementos del archivo FXML.
import javafx.scene.Node;  // 00083823 Importa la clase Node para manejar nodos de la escena de JavaFX.
import javafx.scene.control.*;  // 00083823 Importa clases de controles de JavaFX como ComboBox, Button, TableView, TableColumn, etc.
import javafx.collections.FXCollections;  // 00083823 Importa la clase FXCollections para manejar colecciones observables en JavaFX.
import javafx.collections.ObservableList;  // 00083823 Importa la interfaz ObservableList para manejar listas observables en JavaFX.
import javafx.event.ActionEvent;  // 00083823 Importa la clase ActionEvent para manejar eventos de JavaFX.
import javafx.scene.control.cell.PropertyValueFactory;  // 00083823 Importa la clase PropertyValueFactory para manejar propiedades de celdas en TableView.
import javafx.stage.Stage;  // 00083823 Importa la clase Stage para manejar la ventana de la aplicación.
import org.example.proyecto.Tables.TarjetaXTransaccion;  // 00083823 Importa la clase que representa la entidad TarjetaXTransaccion en la base de datos.
import org.example.proyecto.Utilities.DataBaseCredentials;  // 00083823 Importa la clase que maneja las credenciales de la base de datos.
import org.example.proyecto.Utilities.SaveTXT;  // 00083823 Importa la clase que maneja el guardado de archivos TXT.
import org.example.proyecto.Utilities.SceneChanger;  // 00083823 Importa la clase que permite cambiar escenas en JavaFX.
import java.sql.*;  // 00083823 Importa las clases para manejar SQL y base de datos.

public class ReportDController {

    @FXML
    private ComboBox<String> comboBoxFacilitador;  // 00083823 Define el ComboBox para seleccionar el facilitador.

    @FXML
    private Button btnBuscar;  // 00083823 Define el botón para buscar datos.

    @FXML
    private Button btnLimpiar;  // 00083823 Define el botón para limpiar los datos mostrados.

    @FXML
    private Button btnSalir;  // 00083823 Define el botón para salir de la aplicación.

    @FXML
    private TableView<TarjetaXTransaccion> tbMostrarDatos;  // 00083823 Define la TableView para mostrar los datos.

    @FXML
    private TableColumn<TarjetaXTransaccion, Integer> tbidCliente;  // 00083823 Define la columna para el ID del cliente.

    @FXML
    private TableColumn<TarjetaXTransaccion, Integer> tbCantCompras;  // 00083823 Define la columna para la cantidad de compras.

    @FXML
    private TableColumn<TarjetaXTransaccion, Double> tbTotal;  // 00083823 Define la columna para el total.

    @FXML
    public void initialize() {
        comboBoxFacilitador.setItems(FXCollections.observableArrayList("Visa", "MasterCard", "AmericanExpress"));  // 00083823 Inicializa el ComboBox con opciones predefinidas.

        tbidCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));  // 00083823 Asigna la propiedad de valor para la columna de ID de cliente.
        tbCantCompras.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras"));  // 00083823 Asigna la propiedad de valor para la columna de cantidad de compras.
        tbTotal.setCellValueFactory(new PropertyValueFactory<>("totalMonto"));  // 00083823 Asigna la propiedad de valor para la columna de total.

        tbMostrarDatos.setPlaceholder(new Label("No hay datos para mostrar"));  // 00083823 Configura un mensaje de relleno para la TableView.
    }

    @FXML
    public void onBuscar() {
        String facilitador = comboBoxFacilitador.getValue();  // 00083823 Obtiene el facilitador seleccionado en el ComboBox.
        if (facilitador == null || facilitador.isEmpty()) {  // 00083823 Verifica si el facilitador está seleccionado.
            showAlert("ERROR", "Facilitador no seleccionado", "Por favor, seleccione un facilitador.");  // 00083823 Muestra una alerta si no se seleccionó un facilitador.
            return;
        }

        ObservableList<TarjetaXTransaccion> dataList = FXCollections.observableArrayList();  // 00083823 Crea una lista observable para almacenar los datos consultados.
        try (Connection conn = DriverManager.getConnection(
                DataBaseCredentials.getInstance().getUrl(),  // 00083823 Obtiene la URL de conexión desde las credenciales.
                DataBaseCredentials.getInstance().getUsername(),  // 00083823 Obtiene el nombre de usuario desde las credenciales.
                DataBaseCredentials.getInstance().getPassword())) {  // 00083823 Obtiene la contraseña desde las credenciales.

            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {  // 00083823 Establece la base de datos a utilizar.
                ps1.executeUpdate();  // 00083823 Ejecuta la declaración para seleccionar la base de datos.
            }

            String query = "SELECT c.idCliente AS IDCliente," + // 00083823 Consulta SQL para obtener datos específicos de transacciones.
                    "       COUNT(t.idTransaccion) AS CantidadCompras," +
                    "       SUM(t.totalMonto) AS Total " +
                    "FROM transaccion t " +
                    "INNER JOIN cliente c ON t.idCliente = c.idCliente " +
                    "INNER JOIN tarjeta tr ON c.idCliente = tr.idCliente " +
                    "WHERE tr.facilitador = ?" +
                    "GROUP BY c.idCliente";

            PreparedStatement ps2 = conn.prepareStatement(query);  // 00083823 Prepara la consulta SQL.
            ps2.setString(1, facilitador);  // 00083823 Establece el facilitador como parámetro en la consulta preparada.
            ResultSet rs = ps2.executeQuery();  // 00083823 Ejecuta la consulta y obtiene el conjunto de resultados.

            while (rs.next()) {
                TarjetaXTransaccion clienteTransaccion = new TarjetaXTransaccion(  // 00083823 Crea objetos TarjetaXTransaccion con los resultados obtenidos.
                        rs.getInt("IDCliente"),  // 00083823 Obtiene el ID de cliente del resultado.
                        rs.getInt("CantidadCompras"),  // 00083823 Obtiene el ID de transacción del resultado.
                        rs.getDouble("Total")  // 00083823 Obtiene el total del resultado.
                );
                dataList.add(clienteTransaccion);  // 00083823 Agrega el objeto a la lista observable.
            }

            tbMostrarDatos.setItems(dataList);  // 00083823 Asigna los datos a mostrar en la TableView.

            if (!dataList.isEmpty()) {
                String facilitadorFinal = comboBoxFacilitador.getValue();  // 00083823 Obtiene el ID del cliente de la primera entrada en la lista.
                SaveTXT.SaveDReport(facilitadorFinal, dataList);  // 00083823 Guarda el reporte en un archivo de texto.
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLimpiar() {
        comboBoxFacilitador.setValue(null);  // 00083823 Limpia la selección actual del ComboBox.
        tbMostrarDatos.getItems().clear();  // 00083823 Limpia los datos mostrados en la TableView.
    }

    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // 00083823 Obtiene la ventana actual desde el evento.
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml");  // 00083823 Cambia a la escena principal.
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);  // 00083823 Crea una alerta de tipo error.
        alert.setTitle(title);  // 00083823 Establece el título de la alerta.
        alert.setHeaderText(header);  // 00083823 Establece el encabezado de la alerta.
        alert.setContentText(content);  // 00083823 Establece el contenido de la alerta.
        alert.showAndWait();  // 00083823 Muestra la alerta y espera a que el usuario la cierre.
    }
}
