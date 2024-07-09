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

public class TarjetaController {
    @FXML
    private Button btnCreate;

    @FXML
    private Button btnRead;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private VBox container;

    private final Insets insetsHBox = new Insets(10);
    private final Insets insetsChildren = new Insets(0, 5, 0, 5);
    private final Insets insetsButtons = new Insets(10);

    @FXML
    public void onCreateAction(){
        container.getChildren().clear();

        HBox CardNumberContainer = new HBox();
        Label lblIDCliente = new Label("Ingrese el número de la tarjeta: ");
        TextField tfCardNumber = new TextField();
        CardNumberContainer.setAlignment(Pos.TOP_CENTER);
        CardNumberContainer.setPadding(insetsHBox);
        CardNumberContainer.getChildren().addAll(lblIDCliente, tfCardNumber);

        HBox FacilitadorContainer = new HBox();
        Label lblFacilitador = new Label("Escoja el facilitador: ");
        ComboBox<String> cbxFacilitador = new ComboBox<>();
        List<String> facilitadores = Arrays.asList("MasterCard", "Visa", "AmericanExpress");
        cbxFacilitador.setItems(FXCollections.observableArrayList(facilitadores));
        FacilitadorContainer.setAlignment(Pos.TOP_CENTER);
        FacilitadorContainer.setPadding(insetsHBox);
        FacilitadorContainer.getChildren().addAll(lblFacilitador, cbxFacilitador);

        HBox TypeContainer = new HBox();
        Label lblType = new Label("Escoja el tipo de tarjeta: ");
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

        HBox ClientIDContainer = new HBox();
        Label lblClientID = new Label("Ingrese el ID del cliente al que se le asociara la tarjeta: ");
        TextField tfClientID = new TextField();
        ClientIDContainer.setAlignment(Pos.TOP_CENTER);
        ClientIDContainer.setPadding(insetsHBox);
        ClientIDContainer.getChildren().addAll(lblClientID, tfClientID);

        HBox ButtonContainer = new HBox();
        Button btnClean = new Button("Limpiar datos");
        Button btnSend = new Button("Ingresar a la base de datos");
        btnClean.setPadding(insetsButtons);
        btnSend.setPadding(insetsButtons);
        ButtonContainer.setAlignment(Pos.TOP_CENTER);
        ButtonContainer.setPadding(insetsHBox);
        ButtonContainer.getChildren().addAll(btnClean, btnSend);
        HBox.setMargin(btnClean, new Insets(5));
        HBox.setMargin(btnSend, new Insets(5));

        btnClean.setOnAction(e -> limpiarDatos(tfCardNumber, tfClientID, cbxFacilitador, rdCredit, rdDebit));
        btnSend.setOnAction(e -> insertData(tfCardNumber, cbxFacilitador, (RadioButton) tgType.getSelectedToggle(), tfClientID));
        container.getChildren().addAll(CardNumberContainer, FacilitadorContainer, TypeContainer, ClientIDContainer, ButtonContainer);
    }

    private void insertData(TextField cardNumber, ComboBox<String> facilitador, RadioButton cardType, TextField ClientID) {
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

        if (!ClientID.getText().matches("\\d*")){
            AlertsManager.showAlert("ERROR","Información Errónea","Ocupa solo datos numéricos.");
            cardNumber.setText("");
            flag = false;

        } else if (ClientID.getText().isEmpty()){
            AlertsManager.showAlert("ERROR","Campo Vacío","Digite el ID del cliente");
            flag = false;
        }

        String cardNumberString = cardNumber.getText();
        String cardFacilitador = facilitador.getValue();
        String cardTypeString = String.valueOf(cardType.getText().charAt(0));
        System.out.println(cardTypeString);
        String clientIDString = ClientID.getText();
        System.out.println(cardFacilitador);
        if (flag){
            try (Connection connection = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) {
                try (PreparedStatement ps1 = connection.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                    ps1.executeUpdate();
                }

                PreparedStatement ps = connection.prepareStatement("Insert into Tarjeta(numTarjeta, facilitador, tipoTarjeta, idCliente) values (?, ?, ?, ?)");

                ps.setLong(1, Long.parseLong(cardNumberString));
                ps.setString(2, cardFacilitador);
                ps.setString(3, cardTypeString);
                ps.setInt(4, Integer.parseInt(clientIDString));
                int result = ps.executeUpdate();

                if (result == 0){
                    AlertsManager.showAlert("ERROR","Error al ingresar datos","No se pudo registrar los datos a la base de datos");
                } else if (result > 0) {
                    AlertsManager.showAlert("DATOS INGRESADOS","¡Completado con exito!","Los datos fueron ingresados correctamente");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                AlertsManager.showAlert("ERROR","Error en la base de datos","La base de datos tuvo un error, vuelva a intentarlo");
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
