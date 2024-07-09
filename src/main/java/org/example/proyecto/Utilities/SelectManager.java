package org.example.proyecto.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectManager {

    public List<List<String>> selectContent(String query) {//00009123 Método para seleccionar contenido de la base de datos
        List<List<String>> resultList = new ArrayList<>(); //00009123 Lista de lista que actuara como matriz
        try (Connection conn = DriverManager.getConnection(DataBaseCredentials.getInstance().getUrl(), DataBaseCredentials.getInstance().getUsername(), DataBaseCredentials.getInstance().getPassword())) { //00009123 Se intenta establecer una conexión a la base de datos usando las credenciales
            try (PreparedStatement ps1 = conn.prepareStatement("USE " + DataBaseCredentials.getInstance().getDatabase())) {  //00009123 Usar la base de datos por defecto
                ps1.executeUpdate();//00009123 Ejecutamos la query
            }
            try (PreparedStatement ps = conn.prepareStatement(query)) {//00009123 Preparar y ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) { //00009123 Intenta ejecutar la consulta
                    ResultSetMetaData metaData = rs.getMetaData();//00009123 Obtiene los metadatos de los resultados
                    int columnCount = metaData.getColumnCount(); //00009123 Obtiene el numero de columnas
                    List<String> columnNames = new ArrayList<>();  //00009123 Lista para almacenar los nombres de las columnas
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames.add(metaData.getColumnName(i)); //00009123 Llenar las columnas
                    }
                    resultList.add(columnNames); //00009123 agregar cada nombre de columnas
                    while (rs.next()) { //00009123 Itera sobre las filas del resultado y las añade a la lista
                        List<String> row = new ArrayList<>(); //00009123 Lista de filas
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(rs.getString(i));//00009123 Se agrega cada fila
                        }
                        resultList.add(row);//00009123 Se agrega a la lista las filas
                    }
                }
            }
        } catch (SQLException e) { //00009123 Se imprime un mensaje de error en caso de que ocurra una excepción
            System.err.println("Error al seleccionar datos de la tabla: " + e.getMessage());
        }
        return resultList;//00009123 Retorna la lista de resultados
    }

    public void renderTableView(TableView<ObservableList<String>> tableView, List<List<String>> matrix) { //00009123 Metodo para renderizar un TableView con los datos obtenidos de la base de datos
        tableView.getColumns().clear(); //00009123 Limpia las columnas del TableView antes de agregar las nuevas
        tableView.getItems().clear(); //00009123 Limpia las filas del TableView antes de agregar las nuevas
        List<String> columnNames = matrix.get(0); //00009123 Obtiene los nombres de las columnas de la primera fila de la matriz
        for (int i = 0; i < columnNames.size(); i++) {//00009123 Itera sobre la lista de nombres de columnas para crear y añadir columnas al TableView
            final int colIndex = i;//00009123 Define un índice final para su uso dentro de la expresion lambda
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i)); //00009123 Crea una nueva columna en el TableView con el nombre de la columna correspondiente
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));//00009123 Establece el valor de la celda para cada fila de esta columna
            tableView.getColumns().add(column); //00009123 Añade la columna creada al TableView
        }
        //00009123 Itera sobre las filas del matrix comenzando desde el índice 1 (para omitir los nombres de las columnas)
        for (int i = 1; i < matrix.size(); i++) {
            //00009123 Crea una ObservableList a partir de la fila actual de matrix
            ObservableList<String> row = FXCollections.observableArrayList(matrix.get(i));
            //00009123 Añade la fila al TableView
            tableView.getItems().add(row);
        }

    }
    public void showQueryResultsInTableView(String query, TableView<ObservableList<String>> tableView) { //00009123 Le pasamos la query y la tabla donde debe poner los resultados
        List<List<String>> matrix = selectContent(query);//00009123 Obtiene los datos de la base de datos
        renderTableView(tableView, matrix); //00009123 Renderiza el TableView con los datos obtenidos
    }
}
