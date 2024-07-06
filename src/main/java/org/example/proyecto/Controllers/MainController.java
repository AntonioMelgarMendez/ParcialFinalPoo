package org.example.proyecto.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    /*00009123 TableView con una lista de strings*/
    private TableView<ObservableList<String>> tableView;
    @FXML
    public void onClickShowData() {
        /*00009123 Creamos una objeto de la clase selectManager*/
        SelectManager manager = new SelectManager();
        /*00009123 Al manager le pasamos la query que queremos realizar y la tabla donde vamos a renderizar*/
        manager.showQueryResultsInTableView("select c.idCliente, c.nombre, c.apellido, t.fecha_compra, t.totalMonto from transaccion t inner join Cliente c on c.idCliente = t.idCliente inner join tarjeta tr on tr.idCliente = c.idCliente where t.idCliente = 1 and fecha_compra between '2024-01-01' and '2024-02-01';", tableView);
    }

}