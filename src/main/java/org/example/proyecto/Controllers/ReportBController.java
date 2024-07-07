package org.example.proyecto.Controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SaveTXT;
import org.example.proyecto.Utilities.SceneChanger;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.example.proyecto.Utilities.CleanData.limpiarDatos;
import static org.example.proyecto.Utilities.SaveTXT.SaveBReport;

public class ReportBController { // 00038623 Declara la clase ReportBController

    @FXML // 00038623 Anotación FXML para vincular el campo con el componente del FXML
    private TextField idCliente; // 00038623 Campo para el ID del cliente
    @FXML
    private ChoiceBox<String> mesChoiceBox; // 00038623 Caja de selección para el mes
    @FXML
    private ChoiceBox<String> anoChoiceBox; // 00038623 Caja de selección para el año
    @FXML
    private Label totalLabel; // 00038623 Etiqueta para mostrar el total

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de volver
    private void Back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00038623 Obtiene la ventana actual
        SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml"); // 00038623 Cambia la escena a la pantalla principal
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de limpiar
    private void limpiarCampos() {
        limpiarDatos(idCliente, totalLabel, mesChoiceBox, anoChoiceBox); // 00038623 Llama a la funcion para limpiar datos
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al hacer clic en el botón de calcular gasto
    private void calcularGasto() {
        String idClienteText = idCliente.getText(); // 00038623 Obtiene el texto del campo ID del cliente
        String mesString = mesChoiceBox.getValue(); // 00038623 Obtiene el mes seleccionado
        String year = anoChoiceBox.getValue(); // 00038623 Obtiene el año seleccionado

        if (idClienteText.isEmpty() || mesString == null || year == null) { // 00038623 Verifica si algún campo está vacío
            AlertsManager.showAlert("Campos vacios", "Faltan datos", "Por favor ingrese todos los campos"); // 00038623 Muestra una alerta si hay campos vacíos
            return; // 00038623 Sale del método si hay campos vacíos
        }

        int idCliente = Integer.parseInt(idClienteText); // 00038623 Convierte el texto del ID del cliente a entero
        int anoInt = Integer.parseInt(year); // 00038623 Convierte el año a entero

        // Mapeo de nombres de meses a números de mes
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"}; // 00038623 Arreglo de nombres de meses
        int mesInt = Arrays.asList(meses).indexOf(mesString) + 1; // 00038623 Obtiene el índice del mes y le suma 1 porque los meses en Java comienzan en 1

        double totalGasto = 0; // 00038623 Inicializa el total de gasto a 0
        CalcularTotal(idCliente, anoInt, mesInt, totalGasto); // 00038623 Llama al método para calcular el total
    }

    private void CalcularTotal(int idCliente, int anoInt, int mesInt, double totalGasto) { // 00038623 Método para calcular el total de gasto
        try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) { // 00038623 Establece una conexión con la base de datos
            try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00038623 Cambia a la base de datos específica
                ps1.executeUpdate(); // 00038623 Ejecuta la actualización para usar la base de datos
            }
            String query = "SELECT SUM(totalMonto) AS total FROM transaccion WHERE idCliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?"; // 00038623 Define la consulta SQL
            PreparedStatement preparedStatement = connection.prepareStatement(query); // 00038623 Prepara la consulta SQL
            preparedStatement.setInt(1, idCliente); // 00038623 Establece el valor del primer parámetro (ID del cliente)
            preparedStatement.setInt(2, mesInt); // 00038623 Establece el valor del segundo parámetro (mes)
            preparedStatement.setInt(3, anoInt); // 00038623 Establece el valor del tercer parámetro (año)

            ResultSet resultSet = preparedStatement.executeQuery(); // 00038623 Ejecuta la consulta y obtiene el resultado
            if (resultSet.next()) { // 00038623 Verifica si hay un resultado
                totalGasto = resultSet.getDouble("total"); // 00038623 Obtiene el total de gasto
            }

            if (totalGasto == 0) { // 00038623 Si el total es 0, muestra un mensaje indicando que no hay compras
                totalLabel.setText("No hay compras");
            } else { // 00038623 Si hay un total de gasto, lo muestra en la etiqueta
                totalLabel.setText(String.format("%.2f", totalGasto));
            }
            SaveBReport(totalLabel.getText(),idCliente,Integer.toString(anoInt),Integer.toString(mesInt));
        } catch (SQLException e) { // 00038623 Captura las excepciones SQL
            e.printStackTrace(); // 00038623 Imprime el stack trace de la excepción
            AlertsManager.showAlert("Error calculando el total", "Se ha detectado un error", "Ha ocurrido un error " + e.getMessage()); // 00038623 Muestra una alerta de error
        }
    }

    @FXML // 00038623 Método anotado con FXML para ejecutar al inicializar el controlador
    private void initialize() {
        // Opciones para el mes
        List<String> meses = Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"); // 00038623 Define la lista de meses
        mesChoiceBox.setItems(FXCollections.observableArrayList(meses)); // 00038623 Establece las opciones de mes en la caja de selección

        // Opciones para el año (por ejemplo, los últimos 10 años)
        int year = LocalDate.now().getYear(); // 00038623 Obtiene el año actual
        List<String> anos = FXCollections.observableArrayList(); // 00038623 Crea una lista observable para los años
        for (int i = 0; i < 10; i++) { // 00038623 Agrega los últimos 10 años a la lista
            anos.add(String.valueOf(year - i));
        }
        anoChoiceBox.setItems(FXCollections.observableArrayList(anos)); // 00038623 Establece las opciones de año en la caja de selección
    }
}
