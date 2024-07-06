module org.example.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens org.example.proyecto to javafx.fxml;
    exports org.example.proyecto;
    exports org.example.proyecto.Controllers;
    opens org.example.proyecto.Controllers to javafx.fxml;
}