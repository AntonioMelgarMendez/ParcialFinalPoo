package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.proyecto.Utilities.AlertsManager.showAlert;
public class LoginController {
    @FXML//00009123 Campo para insertar el user
    private TextField userField;
    @FXML //00009123 Campo para insertar la password (PasswordField censura automaticamente)
    private PasswordField passwordField;
    @FXML//00009123 Campo para insertar port
    private TextField portField;
    public void OnClickExitButton(ActionEvent event) {// 00009123 Metodo para poder salir del programa
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //00009123 Obtiene la escena del evento
        stage.close();//00009123 Cierra la ventana actual
    }
    public void onClickSendButton(ActionEvent event) {// 00009123 Metodo para manejar el evento de clic del boton de envio
        String user = userField.getText();// 00009123 Obtenemos el userfiel y lo guardamos
        String pass = passwordField.getText();// 00009123 Obtenemos el password y lo guardamos
        String port = portField.getText();// 00009123 Guardamos el puerto
        if (port.isBlank()) {
            port = "3306";//00009123 Establecer un valor por defecto para el port en caso de estar en blanco
        }
        if (pass.isBlank()) {
            pass = ""; //00009123 Establecer un valor por defecto para la password en caso de estar en blanco
        }
        if (user.isBlank()) {
            user = "root";  //00009123 Colocamos el valor de root como defecto en caso de estar vacio
        }
        if (tryConnectionToDatabase(user, pass, port)) { // 00009123 Intentamos conectarnos a la base de datos con las credenciales proporcionadas
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //00009123 Si la conexión es exitosa cambiamos de escena
            SceneChanger.changeScene(stage, "/org/example/proyecto/ViewsFXML/Main.fxml"); //00009123 Llamamos a la clase de cambiar escena
        } else {
            clearButtons(); //00009123 Si la conexión falla, limpia los campos de entrada
        }
    }
    private boolean tryConnectionToDatabase(String user, String pass, String port) { //00009123 Metodo para intentar conectarse a la base (devuelve true en caso de que funcione)
        String url = "jdbc:mysql://localhost:" + port + "/"; //00009123 Convertimos la url utilizando el port
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {//00009123 Intentamos hacer la conexion con las credenciales
            if (conn != null) {//00009123 Si la conexión es exitosa, muestra una alerta de éxito y establece las credenciales
                showAlert("Conexión Exitosa", "Conexión a la base de datos", "Se ha conectado correctamente a la base de datos.");//00009123 Mandamos a llamar a nuestro metodo para mostrar la alerta de que funciono la conexion
                DataBaseCredentials.getInstance().setUsername(user);  //00009123 Seteamos los datos de usuario
                DataBaseCredentials.getInstance().setPassword(pass);  //00009123 Seteamos los datos de password
                DataBaseCredentials.getInstance().setPort(port);//00009123 Seteamos los datos de puerto
                DataBaseCredentials.getInstance().setUrl(url);//00009123 Colocamos la url que se construyo anteriormente
                return true; //00009123 Retornar true si se logro hacer la conexion
            } else {
                showAlert("Error de Conexión", "Conexión fallida", "No se pudo conectar a la base de datos.");//00009123 Si la conexión falla, muestra una alerta de fallo
                return false;//00009123 Retornamos false
            }

        } catch (SQLException e) {//00009123 Implementamos un catch en caso de que obtengamos algun error de sql
            showAlert("Error de Conexión", "Error SQL: " + e.getErrorCode(), e.getMessage());  //00009123 Mandamos a llamar una alerta de error pasandole el mensaje
            return false;//00009123 Retornamos false
        } catch (Exception e) {
            showAlert("Error de Conexión", "Exception", e.getMessage());//00009123 Llamamos a la alerta para cualquier otro tipo de error que no sea de sql
            return false;//00009123 Retornamos false
        }
    }

    private void clearButtons() {  //00009123 Método para limpiar los campos de entrada
        portField.clear();//00009123 Limpiamos el campo de port
        userField.clear();//00009123 Limpiamos el userfield
        passwordField.clear();//00009123 Limpiamos el password field
    }
}