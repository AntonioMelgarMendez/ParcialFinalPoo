package org.example.proyecto.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyecto.Tables.Tarjeta;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;
import org.example.proyecto.Utilities.SceneChanger;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import static org.example.proyecto.Utilities.CleanData.limpiarDatos;

public class TarjetaController { // 00018523 Clase que permitira insertar, ver, actualizar y borrar datos en la tabla Tarjeta de la base de datos
    @FXML
    private Button btnCreate; // 00018523 Botón para insertar datos en la tabla Tarjeta

    @FXML
    private Button btnRead; // 00018523 Botón para ver datos en la tabla Tarjeta

    @FXML
    private Button btnUpdate; // 00018523 Botón para actualizar datos en la tabla Tarjeta

    @FXML
    private Button btnDelete; // 00018523 Botón para borrar datos en la tabla Tarjeta

    @FXML
    private VBox container; // 00018523 Contenedor donde se pondran todos los campos para las acciones correspondientes

    private final Insets insetsHBox = new Insets(10);   // 00018523 Insets para todos los HBoxes que se crearan
    private final Insets insetsChildren = new Insets(0, 5, 0, 5); // 00018523 Insets para todos los elementos que estaran en los HBoxes
    private final Insets insetsButtons = new Insets(10); // 00018523 Insets para todos los Botones que estaran en los HBoxes

    @FXML
    public void onCreateAction(){ // 00018523 Método que se ocupara cuando se presione el btnCreate
        container.getChildren().clear(); // 00018523 Limpia todos los datos del contenedor del FXML

        HBox CardNumberContainer = new HBox(); // 00018523 Contenedor donde se inserteran los datos del número de Tarjeta
        Label lblIDCliente = new Label("Ingrese el número de la tarjeta: "); // 00018523 Label para informar al usuario lo que tiene que ingresar
        TextField tfCardNumber = new TextField(); // 00018523 TextField donde se escribira el número de la tarjeta
        CardNumberContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Posición en la que se insertaran los datos al HBox
        CardNumberContainer.setPadding(insetsHBox); // 00018523 Se aplica un padding al HBox
        CardNumberContainer.getChildren().addAll(lblIDCliente, tfCardNumber); // 00018523 Se le agrega al HBox los elementos para ingresar el numero de la tarjeta

        HBox FacilitadorContainer = new HBox(); // 00018523 Contenedor donde se inserteran los datos del facilitador
        Label lblFacilitador = new Label("Escoja el facilitador: "); // 00018523 Label para infromar al usuario lo que tiene que ingresar
        ComboBox<String> cbxFacilitador = new ComboBox<>(); // 00018523 ComboBox con las opciones de los facilitadores posibles
        List<String> facilitadores = Arrays.asList("MasterCard", "Visa", "AmericanExpress"); // 00018523 Los elementos que se visualizaran en el ComboBox
        cbxFacilitador.setItems(FXCollections.observableArrayList(facilitadores)); // 00018523 Se le agrega la lista con los elementos al ComboBox
        FacilitadorContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Posición en la que se insertaran los datos al HBox
        FacilitadorContainer.setPadding(insetsHBox); // 00018523 Se aplica un padding al HBox
        FacilitadorContainer.getChildren().addAll(lblFacilitador, cbxFacilitador); // 00018523 Se le agrega al HBox los elementos para ingresar el facilitador de la tarjeta

        HBox TypeContainer = new HBox(); // 00018523 Contenedor donde se inserteran los datos del tipo de tarjeta
        Label lblType = new Label("Escoja el tipo de tarjeta: "); // 00018523 Label para infromar al usuario lo que tiene que seleccionar
        RadioButton rdCredit = new RadioButton("Crédito"); // 00018523 RadioButton para que el usuario seleccione la opción de Crédito
        RadioButton rdDebit = new RadioButton("Débito"); // 00018523 RadioButton para que el usuario seleccione la opción de Débito
        ToggleGroup tgType = new ToggleGroup(); // 00018523 Grupo de opciones para encapsular los dos RadioButtons anteriores
        rdCredit.setPadding(insetsChildren); // 00018523 Padding para Radio Button
        rdDebit.setPadding(insetsChildren); // 00018523 Padding para Radio Button
        rdCredit.setToggleGroup(tgType); // 00018523 Se le asigna un grupo al RadioButton Crédito
        rdDebit.setToggleGroup(tgType); // 00018523 Se le asigna un grupo al RadioButton Débito
        TypeContainer.setPadding(insetsHBox); // 00018523 Se le aplica un padding al contenedor de los RadioButtons
        TypeContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Se le aplica un alignment al contenedor
        TypeContainer.getChildren().addAll(lblType, rdCredit, rdDebit); // 00018523 Se le inserta el Label y los RadioButtons al contenedor

        HBox ClientIDContainer = new HBox(); // 00018523 Contenedor donde se insertera el ID del cliente al que se le asociara la tarjeta
        Label lblClientID = new Label("Ingrese el ID del cliente al que se le asociara la tarjeta: "); // 00018523 Label para infromar al usuario lo que tiene que ingresar
        TextField tfClientID = new TextField(); // 00018523 TextField donde se escribira el ID del cliente
        ClientIDContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Se le aplica un alignment al contenedor
        ClientIDContainer.setPadding(insetsHBox); // 00018523 Se le aplica un padding al contenedor de los RadioButtons
        ClientIDContainer.getChildren().addAll(lblClientID, tfClientID); // 00018523 Se le inserta el Label y el TextField al contenedor

        HBox ButtonContainer = new HBox(); // 00018523 Contenedor para poner los botones
        Button btnClean = new Button("Limpiar datos"); // 00018523 Se crea un botón para limpiar datos en los campos
        Button btnSend = new Button("Ingresar a la base de datos");// 00018523 Se crea un botón para insertar los datos a la tabla de datos
        btnClean.setPadding(insetsButtons); // 00018523 Se le agrega padding al boton
        btnSend.setPadding(insetsButtons); // 00018523 Se le agrega padding al boton
        ButtonContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Se le agrega un alignment al contenedor para los botones
        ButtonContainer.setPadding(insetsHBox); // 00018523 Se le agrega un padding al contenedor
        ButtonContainer.getChildren().addAll(btnClean, btnSend); // 00018523 Se agrega al contenedor los dos botones para limpiar e insertar
        HBox.setMargin(btnClean, new Insets(5)); // 00018523 Se le agrega un margen al boton de Limpiar
        HBox.setMargin(btnSend, new Insets(5)); // 00018523 Se le agrega un margen al boton de Insertar datos

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber, tfClientID, cbxFacilitador, rdCredit, rdDebit)); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para limpiar datos en la clase CleanData
        btnSend.setOnAction(e -> insertData(tfCardNumber, cbxFacilitador, (RadioButton) tgType.getSelectedToggle(), tfClientID)); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para insertar a la base de datos
        container.getChildren().addAll(CardNumberContainer, FacilitadorContainer, TypeContainer, ClientIDContainer, ButtonContainer); // 00018523 Agrega al contenedor principal los contenedores creados anteriormente
    }

    private void insertData(TextField cardNumber, ComboBox<String> facilitador, RadioButton cardType, TextField ClientID) { // 00018523 Se inserta los datos a la base de datos en base a la información ingresada anteriormente
        boolean flag = true; // 00018523 Asigna una bandera para que no haya errores al ingresar datos a la base de datos
        if (!cardNumber.getText().matches("\\d*")){ // 00018523 Verifica que en el textfield solo haya datos númericos
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos."); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            cardNumber.setText(""); // 00018523 Asigna el TextField a nulo
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (cardNumber.getText().isEmpty()){ // 00018523 Verifica si el TextField esta vacío
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");  // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (cardNumber.getText().length() != 16){ // 00018523 Verifica que la cantidad de caracteres sea de exactamente de 16
            AlertsManager.showAlert("ERROR","Cantidad de caracteres errónea","Número de la tarjeta tiene que ser de 16"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (facilitador.getValue() == null){ // 00018523 Verifica que se haya seleccionado un facilitador en el ComboBox
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un facilitador"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (cardType.getText() == null || cardType.getText().isEmpty()){ // 00018523 Verifica que se haya seleccionado un tipo de tarjeta en los RadiButtons
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un tipo de tarjeta"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (!ClientID.getText().matches("\\d*")){ // 00018523 Se verifica que solo se hayan ingresado valores numéricos en el TextField
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos."); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            cardNumber.setText(""); // 00018523 Asigna el TextField a vacío
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (ClientID.getText().isEmpty()){ // 00018523 Verifica que el TextField no este vacío
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el ID del cliente"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        String cardNumberString = cardNumber.getText(); // 00018523 Asigna a una variable lo ingresado en el TextField del número de la tarjeta
        String cardFacilitador = facilitador.getValue(); // 00018523 Asigna a una variable lo ingresado en el ComboBox de el facilitador
        String cardTypeString = String.valueOf(cardType.getText().charAt(0)); // 00018523 Asigna a una variable el primer caracter de la selección de los RadioButtons (C o D)
        String clientIDString = ClientID.getText(); // 00018523 Asigna a una variable el ID del cliente ingresado en el TextFField

        if (flag){ // 00018523 Verifica que la bandera siga como verdadera para que no haya errores en la insercion de datos
            try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){ // 00018523 Realiza la conexión a la base de datos
                try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00018523 Cambia a la base de datos específica
                    ps1.executeUpdate(); // 00018523 Ejecuta la actualización para usar la base de datos
                }

                PreparedStatement ps = conn.prepareStatement("Insert into Tarjeta(numTarjeta, facilitador, tipoTarjeta, idCliente) values (?, ?, ?, ?)"); // 00018523 Ejecuta la query para ingresar a la base de datos

                ps.setLong(1, Long.parseLong(cardNumberString)); // 00018523 Asigna el valor del numero de la tarjeta al numTarjeta de la base de datos
                ps.setString(2, cardFacilitador); // 00018523 Asigna el valor del facilitador al facilitador de la base de datos
                ps.setString(3, cardTypeString); // 00018523 Asigna el valor del tipo de tarjeta al tipoTarjeta de la base de datos
                ps.setInt(4, Integer.parseInt(clientIDString)); // 00018523 Asigna el valor del ID del cliente al idCliente de la base de datos
                int result = ps.executeUpdate(); // 00018523 Ejecuta y guarda en una variable la cantidad de filas afectadas en la ejecucion del query

                if (result == 0){ // 00018523 Si el numero de filas afectadas es cero, hace lo siguiente
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos"); // 00018523 Muestra una alerta al usuario, "No se pudo ingresar los datos"
                } else if (result > 0) { // 00018523 Si el numero de filas afectadas es mayor a cero, hacer lo siguiente
                    AlertsManager.showAlert("DATOS INGRESADOS","¡Completado con exito!","Los datos fueron ingresados correctamente"); // 00018523 Muestra una alerta para confirmar que se ingresaron los datos con exito
                }

            } catch (SQLException e) { // 00018523 Atrapa si ocurre un error en la base de datos
                e.printStackTrace(); // 00018523 Imprime el error en la consola
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo"); // 00018523 Muestra una alerta al usuario, "Error en la base de datos"
            }
        }
    }

    @FXML
    public void onReadAction(){ // 00018523 Método que se ejecutara cuando se presion el btnRead
        container.getChildren().clear(); // 00018523 Limpia lo que haya en el container
        HBox lblContainer = new HBox(); // 00018523 Crea un contenedor para poner el label
        HBox tableContainer = new HBox(); // 00018523 Crea un contenedor para poner la tabla con los datos
        ObservableList<Tarjeta> cardList = FXCollections.observableArrayList(); // 00018523 Es una lista observable que cambia sus datos para la visualización de la tabla

        Label lblHeader = new Label("Datos en la tabla Tarjeta"); // 00018523 Crea un label como titulo de la tabla
        lblHeader.setStyle("-fx-font-size: 16px"); // 00018523 Cambia el tamaño del texto del label

        TableView<Tarjeta> tvData = new TableView<>(); // 00018523 Crea una tabla para visualizar los datos
        TableColumn<Tarjeta, String> tbcCardNumber = new TableColumn<>("Número de Tarjeta"); // 00018523 Crea una columna, y especifica el nombre Número de Tarjeta
        TableColumn<Tarjeta, String> tbcCardFacilitador = new TableColumn<>("Facilitador"); // 00018523 Crea una columna, y especifica el nombre Facilitador
        TableColumn<Tarjeta, String> tbcCardType = new TableColumn<>("Tipo"); // 00018523 Crea una columna, y especifica el nombre Tipo
        TableColumn<Tarjeta, Integer> tbcCardIDClient = new TableColumn<>("ID Cliente"); // 00018523 Crea una columna, y especifica el nombre ID Cliente

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

        tbcCardFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador")); // 00018523 Inicializa los datos que se pondran en la columna, con el nombre del atributo en la clase Tarjeta

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

        tbcCardIDClient.setCellValueFactory(new PropertyValueFactory<>("idCliente")); // 00018523 Inicializa los datos que se pondran en la columna, con el nombre del atributo en la clase Tarjeta

        tvData.getColumns().add(tbcCardNumber); // 00018523 Añade la columna al TableView
        tvData.getColumns().add(tbcCardFacilitador); // 00018523 Añade la columna al TableView
        tvData.getColumns().add(tbcCardType); // 00018523 Añade la columna al TableView
        tvData.getColumns().add(tbcCardIDClient); // 00018523 Añade la columna al TableView

        tbcCardNumber.setPrefWidth(200); // 00018523 Especifica el ancho de la columna
        tbcCardFacilitador.setPrefWidth(200); // 00018523 Especifica el ancho de la columna
        tbcCardType.setPrefWidth(100); // 00018523 Especifica el ancho de la columna
        tbcCardIDClient.setPrefWidth(100); // 00018523 Especifica el ancho de la columna
        tvData.setPrefWidth(600); // 00018523 Especifica el ancho del TableView

        lblContainer.getChildren().add(lblHeader); // 00018523 Agrega al contenedor del Label, el label creado
        lblContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Designa el alignment del contenedor
        lblContainer.setPadding(insetsHBox); // 00018523 Designa el padding del contenedor
        tableContainer.setPrefWidth(600); // 00018523 Designa el ancho del contenedor para la tabla
        tableContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Designa el alignment del contenedor para la tabla
        tableContainer.setPadding(insetsHBox); // 00018523 Le agrega un padding al contenedor de la tabla
        tableContainer.getChildren().add(tvData); // 00018523 Agrega al contenedor de la tabla, el TableView
        tvData.setPlaceholder(new Label("No hay tarjetas en la base de datos")); // 00018523 Deja un mensaje si en la tabla no hay datos

        try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){ // 00018523 Realiza la conexión a la base de datos
            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00018523 Cambia a la base de datos específica
                ps1.executeUpdate(); // 00018523 Ejecuta la actualización para usar la base de datos
            }

            PreparedStatement ps = conn.prepareStatement("select t.numTarjeta as numTarjeta, t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta, t.idCliente as IDCliente from tarjeta t;"); // 00018523 Ejecuta la query para ver los datos en la base de datos
            ResultSet rs = ps.executeQuery(); // 00018523 Ejecuta la consulta, y guarda el resultado de esta en un ResultSet
            while (rs.next()) { // 00018523 Mientras la consulta tenga datos (Filas)
                Tarjeta tarjeta = new Tarjeta(rs.getString("numTarjeta"), rs.getString("Facilitador"), rs.getString("TipoTarjeta"), rs.getInt("IDCliente")); // 00018523 Crea un nuevo objeto tarjeta con los datos de la fila actual
                cardList.add(tarjeta); // 00018523 Añade a la lista, el objeto tipo Tarjeta
            }

            conn.close(); // 00018523 Cierra la conexión
            tvData.setItems(cardList); // 00018523 Añade todos los elementos de la lista al TableView
        } catch (SQLException e) { // 00018523 Atrapa si hay una excepcion en labase de datos
            e.printStackTrace(); // 00018523 Imprime el error en la consola
        }

        container.getChildren().addAll(lblContainer, tableContainer); // 00018523 Añade el titulo (Label) y la tabla con los datos (TableView) al contenedor principal
    }

    @FXML
    public void onUpdateAction(){ // 00018523 Metodo que se ejecutara cuando se presione btnUpdate
        container.getChildren().clear(); // 00018523 Limpia el contenedor principal
        HBox CardNumberContainer = new HBox(); // 00018523 Contenedor para poner informacion del numero de tarjeta
        Label lblIDCliente = new Label("Ingrese el número de la tarjeta a actualizar: "); // 00018523 Crea un label con informacion de lo que hara el usuario
        TextField tfCardNumber = new TextField(); // 00018523 Crea un TextField para que el usuario ingrese los datos
        CardNumberContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Asigna el alignment para el contenedor del numero de tarjeta
        CardNumberContainer.setPadding(insetsHBox); // 00018523 Agrega padding al contenedor del numero de tarjeta
        CardNumberContainer.getChildren().addAll(lblIDCliente, tfCardNumber); // 00018523 Agrega al contenedor de numero de tarjeta: Label y TextField

        HBox FacilitadorContainer = new HBox(); // 00018523 Contenedor para poner informacion del Facilitador
        Label lblFacilitador = new Label("Escoja el facilitador nuevo: "); // 00018523 Crea un label para indicarle al usuario lo que tiene que hacer
        ComboBox<String> cbxFacilitador = new ComboBox<>(); // 00018523 Crea un nuevo ComboBox
        List<String> facilitadores = Arrays.asList("MasterCard", "Visa", "AmericanExpress"); // 00018523 Crea una lista con los valores que se asignaran al ComboBBox
        cbxFacilitador.setItems(FXCollections.observableArrayList(facilitadores)); // 00018523 Asigna al ComboBox la lista de valores
        FacilitadorContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Asigna al contenedor un alignment
        FacilitadorContainer.setPadding(insetsHBox); // 00018523 Asigna al contenedor un padding
        FacilitadorContainer.getChildren().addAll(lblFacilitador, cbxFacilitador); // 00018523 Agrega al contenedor del ComboBox el Label y el ComboBox

        HBox TypeContainer = new HBox(); // 00018523 Contenedor donde se inserteran los datos del tipo de tarjeta
        Label lblType = new Label("Escoja el tipo de tarjeta nuevo: "); // 00018523 Label para infromar al usuario lo que tiene que seleccionar
        RadioButton rdCredit = new RadioButton("Crédito"); // 00018523 RadioButton para que el usuario seleccione la opción de Crédito
        RadioButton rdDebit = new RadioButton("Débito"); // 00018523 RadioButton para que el usuario seleccione la opción de Débito
        ToggleGroup tgType = new ToggleGroup(); // 00018523 Grupo de opciones para encapsular los dos RadioButtons anteriores
        rdCredit.setPadding(insetsChildren); // 00018523 Padding para Radio Button
        rdDebit.setPadding(insetsChildren); // 00018523 Padding para Radio Button
        rdCredit.setToggleGroup(tgType); // 00018523 Se le asigna un grupo al RadioButton Crédito
        rdDebit.setToggleGroup(tgType); // 00018523 Se le asigna un grupo al RadioButton Débito
        TypeContainer.setPadding(insetsHBox); // 00018523 Se le aplica un padding al contenedor de los RadioButtons
        TypeContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Se le aplica un alignment al contenedor
        TypeContainer.getChildren().addAll(lblType, rdCredit, rdDebit); // 00018523 Se le inserta el Label y los RadioButtons al contenedor


        HBox ButtonContainer = new HBox(); // 00018523 Contenedor para poner los botones
        Button btnClean = new Button("Limpiar datos"); // 00018523 Se crea un botón para limpiar datos en los campos
        Button btnSend = new Button("Actualizar datos");// 00018523 Se crea un botón para actualizar los datos a la tabla de datos
        btnClean.setPadding(insetsButtons); // 00018523 Se le agrega padding al boton
        btnSend.setPadding(insetsButtons); // 00018523 Se le agrega padding al boton
        ButtonContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Se le agrega un alignment al contenedor para los botones
        ButtonContainer.setPadding(insetsHBox); // 00018523 Se le agrega un padding al contenedor
        ButtonContainer.getChildren().addAll(btnClean, btnSend); // 00018523 Se agrega al contenedor los dos botones para limpiar y actualizar
        HBox.setMargin(btnClean, new Insets(5)); // 00018523 Se le agrega un margen al boton de Limpiar
        HBox.setMargin(btnSend, new Insets(5)); // 00018523 Se le agrega un margen al boton de Actualizar datos

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber, cbxFacilitador, rdCredit, rdDebit)); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para limpiar datos en la clase CleanData
        btnSend.setOnAction(e -> updateData(tfCardNumber, cbxFacilitador, (RadioButton) tgType.getSelectedToggle())); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para actualizar datos a la base de datos
        container.getChildren().addAll(CardNumberContainer, FacilitadorContainer, TypeContainer, ButtonContainer); // 00018523 Agrega al contenedor principal los contenedores creados anteriormente
    }

    private void updateData(TextField cardNumber, ComboBox<String> facilitador, RadioButton cardType) { // 00018523 Se actualiza los datos a la base de datos en base a la información ingresada anteriormente
        boolean flag = true; // 00018523 Asigna una bandera para que no haya errores al ingresar datos a la base de datos
        if (!cardNumber.getText().matches("\\d*")){ // 00018523 Verifica que en el textfield solo haya datos númericos
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos."); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            cardNumber.setText(""); // 00018523 Asigna el TextField a nulo
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (cardNumber.getText().isEmpty()){ // 00018523 Verifica si el TextField esta vacío
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");  // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (cardNumber.getText().length() != 16){ // 00018523 Verifica que la cantidad de caracteres sea de exactamente de 16
            AlertsManager.showAlert("ERROR","Cantidad de caracteres errónea","Número de la tarjeta tiene que ser de 16"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (facilitador.getValue() == null){ // 00018523 Verifica que se haya seleccionado un facilitador en el ComboBox
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un facilitador"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (cardType.getText() == null || cardType.getText().isEmpty()){ // 00018523 Verifica que se haya seleccionado un tipo de tarjeta en los RadiButtons
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un tipo de tarjeta"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }


        String cardNumberString = cardNumber.getText(); // 00018523 Asigna a una variable lo ingresado en el TextField del número de la tarjeta
        String cardFacilitador = facilitador.getValue(); // 00018523 Asigna a una variable lo ingresado en el ComboBox de el facilitador
        String cardTypeString = String.valueOf(cardType.getText().charAt(0)); // 00018523 Asigna a una variable el primer caracter de la selección de los RadioButtons (C o D)

        if (flag){ // 00018523 Verifica que la bandera siga como verdadera para que no haya errores en la insercion de datos
            try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){ // 00018523 Realiza la conexión a la base de datos
                try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00018523 Cambia a la base de datos específica
                    ps1.executeUpdate(); // 00018523 Ejecuta la actualización para usar la base de datos
                }

                String oldFacilitador = ""; // 00018523 Inicializa una variable donde se guardara el facilitador que estaba en la base de datos
                String oldCardType = ""; // 00018523 Inicializa una variable donde se guardara el tipo de tarjeta que estaba en la base de datos
                PreparedStatement ps = conn.prepareStatement("update tarjeta set facilitador = ?, tipoTarjeta = ? where numTarjeta = ?;"); // 00018523 Ejecuta la query para actualizar los datos en la base de datos
                ps.setString(1, cardFacilitador); // 00018523 Asigna el primer ? al nuevo facilitador
                ps.setString(2, cardTypeString); // 00018523 Asigna el segundo ? al nuevo tipo de tarjeta
                ps.setLong(3, Long.parseLong(cardNumberString)); // 00018523 Asigna el tercer ? al numero de tarjeta a cambiar

                PreparedStatement psSelect = conn.prepareStatement("select t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta from tarjeta t where numTarjeta = ?;"); // 00018523 Ejecuta la query para ver los datos que estaban en la base de datos
                psSelect.setLong(1, Long.parseLong(cardNumberString)); // 00018523 Asigna al primer ? del select a el numero de tarjeta digitado
                ResultSet rs = psSelect.executeQuery(); // 00018523 Ejecuta la consulta, y guarda el resultado de esta en un ResultSet
                while (rs.next()) { // 00018523 Mientras la consulta tenga datos (Filas)
                    oldFacilitador = rs.getString("Facilitador"); // 00018523 Asigna el viejo facilitador al encontrado en la base de datos
                    oldCardType = rs.getString("TipoTarjeta"); // 00018523 Asigna el viejo tipo de tarjeta al encontrado en la base de datos

                    if (oldCardType.equals("C")){ // 00018523 Si el tipo de tarjeta viejo es igual a C, ejecuta lo siguiente
                        oldCardType = "Crédito"; // 00018523 Designa el tipo de tarjeta a ´Crédito´
                    } else if (oldCardType.equals("D")){ // 00018523 Si el tipo de tarjeta viejo es igual a D, ejecuta lo siguiente
                        oldCardType = "Débito"; // 00018523 Designa el tipo de tarjeta a ´Débito´
                    }
                }

                int result = ps.executeUpdate();// 00018523 Ejecuta y guarda en una variable la cantidad de filas afectadas en la ejecucion del query
                if (result == 0){ // 00018523 Si el numero de filas afectadas es cero, hace lo siguiente
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos"); // 00018523 Muestra una alerta al usuario, "No se pudo ingresar los datos"
                } else if (result > 0) { // 00018523 Si las filas afectadas son mayor a cero

                    if (cardTypeString.equals("C")){ // 00018523 Si el tipo de tarjeta viejo es nuevo a C, ejecuta lo siguiente
                        oldCardType = "Crédito"; // 00018523 Designa el tipo de tarjeta a ´Crédito´
                    } else if (cardTypeString.equals("D")) { // 00018523 Si el tipo de tarjeta nuevo es igual a D, ejecuta lo siguiente
                        cardTypeString = "Débito";  // 00018523 Designa el tipo de tarjeta a ´Débito´
                    }

                    AlertsManager.showAlert( // 00018523 Muestra una alerta al usuario
                            "DATOS Actualizados", // 00018523 Designa el titulo de la ventana
                            "¡Completado con exito!", // 00018523 Designa el titulo adentro de la ventana
                            "Los datos fueron actualizados correctamente:\n" + // 00018523 Muestra un mensaje como cuerpo
                            oldFacilitador + " -> " + cardFacilitador + "\n" + // 00018523 De que a que se cambio el facilitador
                            oldCardType + " -> " + cardTypeString + "\n" // 00018523 De que a que se cambio el tipo de tarjeta
                    );
                }
            } catch (SQLException e) { // 00018523 Atrapa si hay una excepcion en labase de datos
                e.printStackTrace(); // 00018523 Imprime el error en la consola
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo"); // 00018523 Muestra una alerta al usuario, si la conexion a la base de datos tuvo errores
            }
        }
    }

    @FXML
    public void onDeleteAction(){ // 00018523 Método que se ejecutara cuando se presione el boton btnDelete
        container.getChildren().clear(); // 00018523 Limpia el contenedor principal
        HBox dataContainer = new HBox(); // 00018523 Crea un contenedor para poner la informacion que el usuario debe de poner
        Label lblCardNumber = new Label("Ingrese el numero de la tarjeta que quiere eliminar: "); // 00018523 Crea un label para que el usuario sepa que escribir
        TextField tfCardNumber = new TextField(); // 00018523 Crea un nuevo TextField para que el usuario ingrese el numero de la tarjeta
        dataContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Asigna un alignment al contenedor de la tarjeta a eliminar
        dataContainer.setPadding(insetsHBox); // 00018523 Asigna un padding al contenedor de la tarjeta a eliminar

        HBox buttonContainer = new HBox(); // 00018523 Crea un contenedor para poner los botones
        Button btnClean = new Button("Limpiar datos"); // 00018523 Crea un boton para limpiar datos
        Button btnDelete = new Button("Eliminar datos"); // 00018523 Crea un boton para eliminar datos
        btnClean.setPadding(insetsHBox); // 00018523 Le asigna un padding al boton
        btnDelete.setPadding(insetsHBox); // 00018523 Le asigna un padding al boton
        buttonContainer.setAlignment(Pos.TOP_CENTER); // 00018523 Asigna el alignment del contenedor para botones
        HBox.setMargin(btnClean, new Insets(5)); // 00018523 Asigna un margen al boton
        HBox.setMargin(btnDelete, new Insets(5)); // 00018523 Asigna un margen al boton
        buttonContainer.setPadding(insetsHBox); // 00018523 Asigna un padding al contenedor de los botones

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber)); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para limpiar datos en la clase CleanData
        btnDelete.setOnAction(e -> deleteData(tfCardNumber)); // 00018523 Se define lo que hara el boton cuando se presione, En este caso llama a una funcion para eliminar un dato en la base de datos

        dataContainer.getChildren().addAll(lblCardNumber, tfCardNumber); // 00018523 Agrega al contenedor de la informacion a digitar el Label y TextField
        buttonContainer.getChildren().addAll(btnClean, btnDelete); // 00018523 Agrega al contenedor de los botones, los botones creados anteriormente
        container.getChildren().addAll(dataContainer, buttonContainer); // 00018523 Agrega al contenedor principal los contenedores anteriores
    }

    public void deleteData(TextField tfCardNumber){ // 00018523 Se elimina el dato en la base de datos, en base a la información ingresada anteriormente
        boolean flag = true; // 00018523 Asigna una bandera para que no haya errores al ingresar datos a la base de datos
        if (!tfCardNumber.getText().matches("\\d*")){ // 00018523 Verifica que en el textfield solo haya datos númericos
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos."); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            tfCardNumber.setText(""); // 00018523 Asigna el TextField a nulo
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (tfCardNumber.getText().isEmpty()){ // 00018523 Verifica si el TextField esta vacío
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");  // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos

        } else if (tfCardNumber.getText().length() != 16){ // 00018523 Verifica que la cantidad de caracteres sea de exactamente de 16
            AlertsManager.showAlert("ERROR","Cantidad de caracteres errónea","Número de la tarjeta tiene que ser de 16"); // 00018523 Muestra una alerta para mostrar al usuario lo que ingreso mal
            flag = false; // 00018523 Asigna la bandera a falso, para que no se pueda ingresar a la base de datos
        }

        if (flag){ // 00018523 Verifica que la bandera siga como verdadera para que no haya errores en la insercion de datos
            try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){ // 00018523 Realiza la conexión a la base de datos
                try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) { // 00018523 Cambia a la base de datos específica
                    ps1.executeUpdate(); // 00018523 Ejecuta la actualización para usar la base de datos
                }

                PreparedStatement ps = conn.prepareStatement("delete from tarjeta where numTarjeta = ?;"); // 00018523 Hace un query, donde eliminara un dato en base a lo que el usuario escribio

                ps.setLong(1, Long.parseLong(tfCardNumber.getText())); // Asigna al primer ? el valor que el usuario escribio en el TextField
                int result = ps.executeUpdate(); // 00018523 Ejecuta y guarda en una variable la cantidad de filas afectadas en la ejecucion del query

                if (result == 0){ // 00018523 Si el numero de filas afectadas es cero, hace lo siguiente
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos"); // 00018523 Muestra una alerta al usuario, "No se pudo ingresar los datos"
                } else if (result > 0) { // 00018523 Si la cantidad de filas afectadas es mayor a cero, hace lo siguiente
                    AlertsManager.showAlert("DATOS ELIMINADOS","¡Datos eliminados con éxito!","Los datos fueron eliminados correctamente"); // 00018523 Muestra un mensaje al usuario de exito
                }

            } catch (SQLException e) { // 00018523 Atrapa una excepcion si ha habido un error en la conexion a la base de datos
                e.printStackTrace(); // 00018523 Imprime en la consola el error
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo"); // 00018523 Muestra al usuario que ha habido un error en la conexion
            }
        }
    }

    public void onBackButton(ActionEvent event){ // 00018523 Este método se ejecutará cuando se presione el botón btnBack
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00018523 Obtiene la ventana actual
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml"); // 00018523 Cambia la escena a la pantalla principal
    }
}
