package org.example.proyecto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application { //00038623 Declara la clase Main que extiende Application

    @Override // 00038623 Sobrescribe el método start de la clase Application
    public void start(Stage stage) throws IOException { // 00038623 Define el método start que recibe un Stage y lanza IOException

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ViewsFXML/Login.fxml")); // 00038623 Crea una instancia de FXMLLoader y carga el archivo FXML
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720); // 00038623 Crea una nueva escena con el contenido del archivo FXML y establece el tamaño
        stage.setTitle("Login Menu"); // 00038623 Establece el título de la ventana
        stage.setScene(scene); // 00038623 Establece la escena en el escenario (Stage)
        stage.show(); // 00038623 Muestra el escenario (Stage)
    }

    public static void main(String[] args) { // 00038623 Define el método main que recibe un array de argumentos
        launch(); // 00038623 Llama al método launch para iniciar la aplicación JavaFX
    }
}