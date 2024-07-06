package org.example.proyecto.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectManager {

    public List<List<String>> selectContent(String query) {
        List<List<String>> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) {
            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {
                ps1.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    List<String> columnNames = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames.add(metaData.getColumnName(i));
                    }
                    resultList.add(columnNames);
                    while (rs.next()) {
                        List<String> row = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(rs.getString(i));
                        }
                        resultList.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar datos de la tabla: " + e.getMessage());
        }

        return resultList;
    }

    public void renderTableView(TableView<ObservableList<String>> tableView, List<List<String>> matrix) {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        List<String> columnNames = matrix.get(0);
        for (int i = 0; i < columnNames.size(); i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
            tableView.getColumns().add(column);
        }
        for (int i = 1; i < matrix.size(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList(matrix.get(i));
            tableView.getItems().add(row);
        }
    }

    public void showQueryResultsInTableView(String query, TableView<ObservableList<String>> tableView) {
        List<List<String>> matrix = selectContent(query);
        renderTableView(tableView, matrix);
    }
}
