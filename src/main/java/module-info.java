module org.example.parcialpoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.parcialpoo to javafx.fxml;
    exports org.example.parcialpoo;
}