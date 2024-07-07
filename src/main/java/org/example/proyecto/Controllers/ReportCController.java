package org.example.proyecto.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.proyecto.Controllers.Tables.Tarjeta;

import java.sql.*;

import static org.example.proyecto.Controllers.Utilities.CleanData.limpiarDatos;

public class ReportCController {    // 00018523 Controlador para buscar las tarjetas asociadas a un usuario

    @FXML
    private TextField tfIDclient; // 00018523 TextField para escribir el ID del usuario a buscar

    @FXML
    private Button btnSearchClient; // 00018523 Botón para buscar los datos

    @FXML
    private Button btnCleanData; // 00018523 Botón para limpiar los datos mostrados

    @FXML
    private TableView<Tarjeta> tbShowCards; // 00018523 TableView para la visualización de las tarjetas

    @FXML
    private TableColumn<Tarjeta, String> tbcCardNumber; // 00018523 Columna de la tabla tbShowCards, esta especifíca el número de tarjeta

    @FXML
    private TableColumn<Tarjeta, String> tbcCardType; // 00018523 Columna de la tabla tbShowCards, esta especifíca el tipo de tarjeta

    @FXML
    private void initialize() { // 00018523 Inicializa los valores de las tablas que se utilizaran
        tbcCardNumber.setCellValueFactory(cellData -> { // 00018523 Establece el valor de la celda del número de la tarjeta
            String numTarjeta = cellData.getValue().getNumTarjeta(); // 00018523 Guarda como string el número de la tarjeta
            char[] censoredCard = numTarjeta.toCharArray(); // 00018523 Inicializa una cadena de caracteres a partir del número de la tarjeta
            String numMostrado; // 00018523 Inicializa una variable para censurar el número de la tarjeta

            if (!numTarjeta.isEmpty()){ // 00018523 Verifica si el string del número de la tarjeta tiene caracteres
                numMostrado = "XXXX XXXX XXXX " + censoredCard[12] + censoredCard[13] + censoredCard[14] + censoredCard[15]; // 00018523 Establece la variable numMostrado a una censura de los primeros 12 carácteres, dejando visibles los últimos 4
            } else { // 00018523 Si el string del número de la tarjeta está vacío, se ejecuta el bloque a continuación
                numMostrado = "N/A"; // 00018523 Establece la variable numMostrado a "N/A" (Sin Tarjeta)
            }
            return new SimpleStringProperty(numMostrado); // 00018523 Retorna el dato a renderizar en la columna del número de la tarjeta, en base a los casos anteriores
        });

        tbcCardType.setCellValueFactory(cellData -> { // 00018523 Establece el valor de la celda de los tipos de tarjeta
            String cardType = cellData.getValue().getTipoTarjeta(); // 00018523 Guarda como string el tipo de tarjeta
            String showType; // 00018523 Inicializa una variable para especificar el tipo de tarjeta

            if (cardType.equals("C")){ // 00018523 Si el tipo de tarjeta es C, se ejecuta el bloque a continuación
                showType = "Crédito"; // 00018523 Establece la variable showType a "Crédito"
            } else if (cardType.equals("D")){ // 00018523 Si el tipo de tarjeta es D, se ejecuta el bloque a continuación
                showType = "Débito"; // 00018523 Establece la variable showType a "Débito"
            } else { // 00018523 Si no se cumple ninguna de las condiciones anteriores, se ejecuta el bloque a continuación
                showType = "N/A"; // 00018523 Establece la variable showType a "N/A" (Sin tarjeta)
            }
            return new SimpleStringProperty(showType); // 00018523 Retorna el dato a renderizar en la columna del tipo de tarjeta, en base a los casos anteriores
        });

        tbShowCards.setPlaceholder(new Label("No hay tarjetas asociadas a este usuario")); // 00018523 Asigna un string predeterminado cuando no se encuentren datos de tarjetas
    }

    @FXML
    public void onSearchClient(){ // 00018523 Este método se ejecutará cuando se presiona el botón btnSearchCliente
        boolean flag = true; // 00018523 Establece una bandera para verificar que los campos de búsqueda sean correctos
        ObservableList<Tarjeta> cardList = FXCollections.observableArrayList(); // 00018523 Es una lista observable que cambia sus datos para la visualización de la tabla

        String data = tfIDclient.getText(); // 00018523 Guarda en una variable los datos escritos en el TextField
        if (!data.matches("\\d*")) { // 00018523 Verifica que los valores insertados en el TextField solo sean numéricos
            tfIDclient.setText(""); // 00018523 Asigna como vacío el TextField
            flag = false; // 00018523 Asigna como falso la bandera
        }

        if (flag) { // 00018523 Verifica que la bandera siga como verdadera o cambia basándose en el if anterior
            try { // 00018523 Intenta realizar el siguiente bloque de código
                Connection conn = DriverManager.getConnection( // 00018523 Crea la conexión a la base de datos
                        "jdbc:mysql://localhost:3306/dbSistemaBanco", // 00018523 Busca el nombre de la base de datos
                        "root", // 00018523 Asigna el usuario con el que se accede a la base de datos
                        "admin123" // 00018523 Asigna la contraseña con la que se accede a la base de datos
                );

                PreparedStatement ps = conn.prepareStatement("select c.idCliente as IDCliente, t.numTarjeta as numTarjeta, t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta from tarjeta t inner join cliente c on t.idCliente = c.idCliente where c.idCliente = ?;"); // 00018523 Prepara una consulta a la base de datos
                ps.setInt(1, Integer.parseInt(data)); // 00018523 Designa el valor "?" de la consulta anterior, basándose en lo digitado en el TextField
                ResultSet rs = ps.executeQuery(); // 00018523 Ejecuta la consulta, y guarda el resultado de esta en un ResultSet

                while (rs.next()) { // 00018523 Mientras la consulta tenga resultados (Filas con datos)
                    Tarjeta tarjeta = new Tarjeta(rs.getString("numTarjeta"), rs.getString("Facilitador"), rs.getString("TipoTarjeta"), rs.getInt("IDCliente")); // 00018523 Crea un objeto tarjeta, con los datos sacados de la consulta
                    cardList.add(tarjeta); // 00018523 Añade a la lista observable el objeto carta
                }

                conn.close(); // 00018523 Cierra la connexion a la base de datos
            } catch (SQLException e) { // 00018523 Guarda si se ejecuta un error con la base de datos
                e.printStackTrace(); // 00018523 Imprime la especificación del error
            }

            tbShowCards.setItems(cardList); // 00018523 Añade al TableView todos los datos guardados en la lista observable
        } else { // 00018523 Si la bandera se encuentra como falso, ejecuta lo siguiente
            cardList.clear(); // 00018523 Limpia la lista observable
            tbShowCards.setItems(cardList); // 00018523 La lista observable al no tener datos, limpia también el TableView
        }

    }

    @FXML
    public void onCleanData(){ // 00018523 Este método se ejecutará cuando se presione el botón btnCleanData
        limpiarDatos(tfIDclient, tbShowCards); // 00018523 Llama a la función estática de la clase CleanData
    }

    @FXML
    public void onBackAction(){ // 00018523 Este método se ejecutará cuando se presione el botón btnBack

    }
}
