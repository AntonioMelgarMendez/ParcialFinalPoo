package org.example.proyecto.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.*;

import static javafx.scene.layout.VBox.setMargin;
import static org.example.proyecto.Controllers.Utilities.CleanData.limpiarDatos;

public class ReportCController {

    @FXML
    private TextField tfIDclient;

    @FXML
    private Button btnSearchClient;

    @FXML
    private Button btnCleanData;

    @FXML
    private VBox creditCardContainer;

    @FXML
    private VBox debitCardContainer;

    @FXML
    public void onSearchClient(){
        boolean flag = true;

        creditCardContainer.getChildren().clear();
        debitCardContainer.getChildren().clear();

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

                PreparedStatement ps = conn.prepareStatement("select c.idCliente as IDCliente, t.numTarjeta as numTarjeta, t.facilitador as Facilitador, t.tipoTarjeta as TipoTarjeta from Tarjeta t inner join Cliente c on t.idCliente = c.idCliente where c.idCliente = ?;");
                ps.setInt(1, Integer.parseInt(tfIDclient.getText()));
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Label lblCreditNumber = new Label();

                    String creditCard = rs.getString("numTarjeta");
                    char[] censoredCreditCard = creditCard.toCharArray();
                    String censoredCard = "XXXX XXXX XXXX " + censoredCreditCard[12] + censoredCreditCard[13] + censoredCreditCard[14] + censoredCreditCard[15];

                    lblCreditNumber.setText(censoredCard);
                    lblCreditNumber.setStyle("-fx-font-size: 16px");

                    if (!rs.getString("TipoTarjeta").isEmpty()) {
                        if (rs.getString("TipoTarjeta").equals("C")) {
                            setMargin(lblCreditNumber, new Insets(0, 0, 5, 0));
                            creditCardContainer.getChildren().add(lblCreditNumber);
                        } else if (rs.getString("TipoTarjeta").equals("D")) {
                            setMargin(lblCreditNumber, new Insets(0, 0, 5, 0));
                            debitCardContainer.getChildren().add(lblCreditNumber);
                        }
                    }
                }

                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Label lblNothingCredit = new Label();
        Label lblNothingDebit = new Label();
        lblNothingCredit.setText("N/A");
        lblNothingDebit.setText("N/A");
        String style = "-fx-font-size: 16px; -fx-text-fill: red;";
        lblNothingCredit.setStyle(style);
        lblNothingDebit.setStyle(style);

        if (creditCardContainer.getChildren().isEmpty()) {
            creditCardContainer.getChildren().add(lblNothingCredit);
        }

        if (debitCardContainer.getChildren().isEmpty()){
            debitCardContainer.getChildren().add(lblNothingDebit);
        }
    }

    @FXML
    public void onCleanData(){
        limpiarDatos(tfIDclient, creditCardContainer, debitCardContainer);
    }

    @FXML
    public void onBackAction(){

    }
}
