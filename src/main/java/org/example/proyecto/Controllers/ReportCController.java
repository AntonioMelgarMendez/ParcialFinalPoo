package org.example.proyecto.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.*;

import static javafx.scene.layout.VBox.setMargin;
import static org.example.proyecto.Controllers.Utils.CleanData.limpiarDatos;

public class ReportCController {

    @FXML
    private TextField tfIDclient;

    @FXML
    private Button btnSearchClient;

    @FXML
    private Button btnCleanData;

    @FXML
    private TableView<Tarjeta> tbShowCards;

    @FXML
    private TableColumn<Tarjeta, String> tbcCardNumber;

    @FXML
    private TableColumn<Tarjeta, String> tbcCardType;

    @FXML
    private void initialize() {
        tbcCardType.setCellValueFactory(cellData -> {
            String tipoTarjeta = cellData.getValue().getTipoTarjeta();
            String tipoMostrado;

            if (tipoTarjeta.equals("C")){
                tipoMostrado = "Crédito";
            } else if (tipoTarjeta.equals("D")){
                tipoMostrado = "Débito";
            } else {
                tipoMostrado = "N/A";
            }

            return new SimpleStringProperty(tipoMostrado);
        });

        tbcCardNumber.setCellValueFactory(cellData -> {
            String numTarjeta = cellData.getValue().getNumTarjeta();
            char[] censoredCard = numTarjeta.toCharArray();
            String numMostrado = "XXXX XXXX XXXX " + censoredCard[12] + censoredCard[13] + censoredCard[14] + censoredCard[15];
            return new SimpleStringProperty(numMostrado);
        });

        tbShowCards.setPlaceholder(new Label("No hay tarjetas asociadas a este usuario"));
    }

    @FXML
    public void onSearchClient(){
        boolean flag = true;
        ObservableList<Tarjeta> cardList = FXCollections.observableArrayList();

        String data = tfIDclient.getText();
        if (!data.matches("\\d*")) {
            tfIDclient.setText("");
            flag = false;
        }

        if (flag) {
            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbSistemaBanco",
                        "root",
                        "admin123"
                );

                PreparedStatement ps = conn.prepareStatement("select c.idCliente as IDCliente, t.numTarjeta as numTarjeta, t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta from tarjeta t inner join cliente c on t.idCliente = c.idCliente where c.idCliente = ?;");
                ps.setInt(1, Integer.parseInt(tfIDclient.getText()));
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Tarjeta tarjeta = new Tarjeta(rs.getString("numTarjeta"), rs.getString("Facilitador"), rs.getString("TipoTarjeta"), rs.getInt("IDCliente"));
                    cardList.add(tarjeta);
                }

                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tbShowCards.setItems(cardList);
        } else {
            cardList.clear();
            tbShowCards.setItems(cardList);
        }

    }

    @FXML
    public void onCleanData(){

    }

    @FXML
    public void onBackAction(){

    }
}
