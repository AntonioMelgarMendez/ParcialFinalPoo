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
    public void onReadAction(){
        container.getChildren().clear();
        HBox lblContainer = new HBox();
        HBox tableContainer = new HBox();
        ObservableList<Tarjeta> cardList = FXCollections.observableArrayList(); // 00018523 Es una lista observable que cambia sus datos para la visualización de la tabla

        Label lblHeader = new Label("Datos en la tabla Tarjeta");
        lblHeader.setStyle("-fx-font-size: 16px");

        TableView<Tarjeta> tvData = new TableView<>();
        TableColumn<Tarjeta, String> tbcCardNumber = new TableColumn<>("Número de Tarjeta");
        TableColumn<Tarjeta, String> tbcCardFacilitador = new TableColumn<>("Facilitador");
        TableColumn<Tarjeta, String> tbcCardType = new TableColumn<>("Tipo");
        TableColumn<Tarjeta, Integer> tbcCardIDClient = new TableColumn<>("ID Cliente");

        tbcCardNumber.setCellValueFactory(cellData -> {
            String numTarjeta = cellData.getValue().getNumTarjeta(); 
            char[] censoredCard = numTarjeta.toCharArray(); 
            String numMostrado;

            if (!numTarjeta.isEmpty()){
                numMostrado = "XXXX XXXX XXXX " + censoredCard[12] + censoredCard[13] + censoredCard[14] + censoredCard[15];
            } else {
                numMostrado = "N/A";
            }
            return new SimpleStringProperty(numMostrado);
        });

        tbcCardFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador"));

        tbcCardType.setCellValueFactory(cellData -> {
            String cardType = cellData.getValue().getTipoTarjeta();
            String showType;

            if (cardType.equals("C")){
                showType = "Crédito";
            } else if (cardType.equals("D")){
                showType = "Débito";
            } else {
                showType = "N/A";
            }
            return new SimpleStringProperty(showType);
        });

        tbcCardIDClient.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        tvData.getColumns().add(tbcCardNumber);
        tvData.getColumns().add(tbcCardFacilitador);
        tvData.getColumns().add(tbcCardType);
        tvData.getColumns().add(tbcCardIDClient);

        tbcCardNumber.setPrefWidth(200);
        tbcCardFacilitador.setPrefWidth(200);
        tbcCardType.setPrefWidth(100);
        tbcCardIDClient.setPrefWidth(100);
        tvData.setPrefWidth(600);

        lblContainer.getChildren().add(lblHeader);
        lblContainer.setAlignment(Pos.TOP_CENTER);
        lblContainer.setPadding(insetsHBox);
        tableContainer.setPrefWidth(600);
        tableContainer.setAlignment(Pos.TOP_CENTER);
        tableContainer.setPadding(insetsHBox);
        tableContainer.getChildren().add(tvData);
        tvData.setPlaceholder(new Label("No hay tarjetas en la base de datos"));

        try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())){
            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                ps1.executeUpdate();
            }

            PreparedStatement ps = conn.prepareStatement("select t.numTarjeta as numTarjeta, t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta, t.idCliente as IDCliente from tarjeta t;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta tarjeta = new Tarjeta(rs.getString("numTarjeta"), rs.getString("Facilitador"), rs.getString("TipoTarjeta"), rs.getInt("IDCliente"));
                cardList.add(tarjeta);
            }

            conn.close();
            tvData.setItems(cardList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        container.getChildren().addAll(lblContainer, tableContainer);
    }

    @FXML
    public void onUpdateAction(){
        container.getChildren().clear();
        HBox CardNumberContainer = new HBox();
        Label lblIDCliente = new Label("Ingrese el número de la tarjeta a actualizar: ");
        TextField tfCardNumber = new TextField();
        CardNumberContainer.setAlignment(Pos.TOP_CENTER);
        CardNumberContainer.setPadding(insetsHBox);
        CardNumberContainer.getChildren().addAll(lblIDCliente, tfCardNumber);

        HBox FacilitadorContainer = new HBox();
        Label lblFacilitador = new Label("Escoja el facilitador nuevo: ");
        ComboBox<String> cbxFacilitador = new ComboBox<>();
        List<String> facilitadores = Arrays.asList("MasterCard", "Visa", "AmericanExpress");
        cbxFacilitador.setItems(FXCollections.observableArrayList(facilitadores));
        FacilitadorContainer.setAlignment(Pos.TOP_CENTER);
        FacilitadorContainer.setPadding(insetsHBox);
        FacilitadorContainer.getChildren().addAll(lblFacilitador, cbxFacilitador);

        HBox TypeContainer = new HBox();
        Label lblType = new Label("Escoja el tipo de tarjeta nuevo: ");
        RadioButton rdCredit = new RadioButton("Crédito");
        RadioButton rdDebit = new RadioButton("Débito");
        ToggleGroup tgType = new ToggleGroup();
        rdCredit.setPadding(insetsChildren);
        rdDebit.setPadding(insetsChildren);
        rdCredit.setToggleGroup(tgType);
        rdDebit.setToggleGroup(tgType);
        TypeContainer.setPadding(insetsHBox);
        TypeContainer.setAlignment(Pos.TOP_CENTER);
        TypeContainer.getChildren().addAll(lblType, rdCredit, rdDebit);

        HBox ButtonContainer = new HBox();
        Button btnClean = new Button("Limpiar datos");
        Button btnSend = new Button("Actualizar datos");
        btnClean.setPadding(insetsButtons);
        btnSend.setPadding(insetsButtons);
        ButtonContainer.setAlignment(Pos.TOP_CENTER);
        ButtonContainer.setPadding(insetsHBox);
        ButtonContainer.getChildren().addAll(btnClean, btnSend);
        HBox.setMargin(btnClean, new Insets(5));
        HBox.setMargin(btnSend, new Insets(5));

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber, cbxFacilitador, rdCredit, rdDebit));
        btnSend.setOnAction(e -> updateData(tfCardNumber, cbxFacilitador, (RadioButton) tgType.getSelectedToggle()));
        container.getChildren().addAll(CardNumberContainer, FacilitadorContainer, TypeContainer, ButtonContainer);
    }

    private void updateData(TextField cardNumber, ComboBox<String> facilitador, RadioButton cardType) {
        boolean flag = true;
        if (!cardNumber.getText().matches("\\d*")){
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos.");
            cardNumber.setText("");
            flag = false;

        } else if (cardNumber.getText().isEmpty()){
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");
            flag = false;

        } else if (cardNumber.getText().length() != 16){
            AlertsManager.showAlert("ERROR","Cantidad de caracteres errónea","Número de la tarjeta tiene que ser de 16");
            flag = false;
        }

        if (facilitador.getValue() == null){
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un facilitador");
            flag = false;
        }

        if (cardType.getText() == null || cardType.getText().isEmpty()){
            AlertsManager.showAlert("ERROR","Campo sin llenar","Seleccione un tipo de tarjeta");
            flag = false;
        }


        String cardNumberString = cardNumber.getText();
        String cardFacilitador = facilitador.getValue();
        String cardTypeString = String.valueOf(cardType.getText().charAt(0));

        if (flag){
            try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) {
                try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                    ps1.executeUpdate();
                }

                String oldFacilitador = "";
                String oldCardType = "";
                PreparedStatement ps = connection.prepareStatement("update tarjeta set facilitador = ?, tipoTarjeta = ? where numTarjeta = ?;");
                ps.setString(1, cardFacilitador);
                ps.setString(2, cardTypeString);
                ps.setLong(3, Long.parseLong(cardNumberString));

                PreparedStatement psSelect = connection.prepareStatement("select t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta from tarjeta t where numTarjeta = ?;");
                psSelect.setLong(1, Long.parseLong(cardNumberString));
                ResultSet rs = psSelect.executeQuery();
                while (rs.next()) {
                    oldFacilitador = rs.getString("Facilitador");
                    oldCardType = rs.getString("TipoTarjeta");

                }

                int result = ps.executeUpdate();
                if (result == 0){
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos");
                } else if (result > 0) {
                    if (oldCardType.equals("C")){
                        oldCardType = "Crédito";
                    } else if (oldCardType.equals("D")){
                        oldCardType = "Débito";
                    }

                    if (cardTypeString.equals("C")){
                        cardTypeString = "Crédito";
                    } else if (cardTypeString.equals("D")){
                        cardTypeString = "Débito";
                    }

                    AlertsManager.showAlert(
                            "DATOS Actualizados",
                            "¡Completado con exito!",
                            "Los datos fueron actualizados correctamente:\n" +
                            oldFacilitador + " -> " + cardFacilitador + "\n" +
                            oldCardType + " -> " + cardTypeString + "\n"
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo");
            }
        }
    }

    @FXML
    public void onDeleteAction(){
        container.getChildren().clear();
        HBox dataContainer = new HBox();
        Label lblCardNumber = new Label("Ingrese el numero de la tarjeta que quiere eliminar: ");
        TextField tfCardNumber = new TextField();
        dataContainer.setAlignment(Pos.TOP_CENTER);
        dataContainer.setPadding(insetsHBox);

        HBox buttonContainer = new HBox();
        Button btnClean = new Button("Limpiar datos");
        Button btnDelete = new Button("Eliminar datos");
        btnClean.setPadding(insetsHBox);
        btnDelete.setPadding(insetsHBox);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        HBox.setMargin(btnClean, new Insets(5));
        HBox.setMargin(btnDelete, new Insets(5));
        buttonContainer.setPadding(insetsHBox);

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber));
        btnDelete.setOnAction(e -> deleteData(tfCardNumber));

        dataContainer.getChildren().addAll(lblCardNumber, tfCardNumber);
        buttonContainer.getChildren().addAll(btnClean, btnDelete);
        container.getChildren().addAll(dataContainer, buttonContainer);
    }

    public void deleteData(TextField tfCardNumber){
        boolean flag = true;
        if (tfCardNumber.getText().isEmpty()){
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");
        } else if (tfCardNumber.getText().isEmpty()){
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el número de la tarjeta de crédito");
            flag = false;

        } else if (tfCardNumber.getText().length() != 16){
            AlertsManager.showAlert("ERROR","Cantidad de caracteres errónea","Número de la tarjeta tiene que ser de 16");
            flag = false;
        }

        if (flag){
            try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) {
                try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                    ps1.executeUpdate();
                }

                PreparedStatement ps = connection.prepareStatement("delete from tarjeta where numTarjeta = ?;");

                ps.setLong(1, Long.parseLong(tfCardNumber.getText()));
                int result = ps.executeUpdate();

                if (result == 0){
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos");
                } else if (result > 0) {
                    AlertsManager.showAlert("DATOS ELIMINADOS","¡Datos eliminados con éxito!","Los datos fueron eliminados correctamente");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo");
            }
        }
    }

    public void onBackButton(ActionEvent event){ // 00018523 Este método se ejecutará cuando se presione el botón btnBack
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // 00018523 Obtiene la ventana actual
        SceneChanger.changeScene(stage,"/org/example/proyecto/ViewsFXML/Main.fxml"); // 00018523 Cambia la escena a la pantalla principal
    }
}
