module gui.front {
    requires javafx.controls;
    requires javafx.fxml;
    requires back;
    requires commun;

    opens gui.front to javafx.fxml;
    exports gui.front;
}