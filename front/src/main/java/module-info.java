module front {
    requires javafx.controls;
    requires javafx.fxml;
    requires back;
    requires commun;
    requires java.sql;

    opens gui to javafx.fxml;
    exports gui;
}