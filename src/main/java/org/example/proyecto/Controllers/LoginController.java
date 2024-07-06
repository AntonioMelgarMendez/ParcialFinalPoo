package org.example.proyecto.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.proyecto.Controllers.AlertsManager.showAlert;

public class LoginController {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField portField;

    public void initialize(){

    }
    public void OnClickExitButton(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void onClickSendButton(ActionEvent event){
      String user = userField.getText();
      String pass = passwordField.getText();
      String port = portField.getText();
      if(port.isBlank()){
          port = "3306";
      }
      if(pass.isBlank()){
            port = "";
        }
      if(user.isBlank()){
            port = "root";
      }
      if(user.isBlank() & pass.isBlank() & port.isBlank()){
          user = "root";
          pass="";
          port = "3306";
      }
      if(tryConnectionToDatabase(user,pass,port)){
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml");
      }
      else{
          clearButtons();
      }
    }
    private boolean tryConnectionToDatabase(String user, String pass,String port) {
        String url = "jdbc:mysql://localhost:"+port+"/";
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            if (conn != null) {
                System.out.println("Connection successful");
                showAlert("Conexión Exitosa", "Conexión a la base de datos", "Se ha conectado correctamente a la base de datos.");
                return true;
            } else {
                System.out.println("Connection failed");
                showAlert("Error de Conexión", "Conexión fallida", "No se pudo conectar a la base de datos.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            showAlert("Error de Conexión", "Error SQL: " + e.getErrorCode(), e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            showAlert("Error de Conexión", "Exception", e.getMessage());
            return false;
        }
    }
    private void clearButtons(){
        portField.clear();
        userField.clear();
        passwordField.clear();
    }

}
