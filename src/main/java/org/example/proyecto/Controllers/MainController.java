package org.example.proyecto.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    public void onClickShowData() {
        SelectManager manager = new SelectManager();
        manager.showQueryResultsInTableView("select c.idCliente, c.nombre, c.apellido, t.fecha_compra, t.totalMonto from transaccion t inner join Cliente c on c.idCliente = t.idCliente inner join tarjeta tr on tr.idCliente = c.idCliente where t.idCliente = 1 and fecha_compra between '2024-01-01' and '2024-02-01';", tableView);
    }

}