package org.example.proyecto.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.proyecto.Tables.Transaccion;
import org.example.proyecto.Utilities.AlertsManager;
import org.example.proyecto.Utilities.DataBaseCredentials;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.example.proyecto.Utilities.CleanData.limpiarDatos;
import static org.example.proyecto.Utilities.SaveTXT.SaveAReport;

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

    }

    @FXML
    public void onUpdateAction(){
        container.getChildren().clear();
    }

    @FXML
    public void onDeleteAction(){
        container.getChildren().clear();
    }
}
