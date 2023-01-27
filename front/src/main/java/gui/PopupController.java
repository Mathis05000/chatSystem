package gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metiers.Service;

public class PopupController {

    private Service service;
    @FXML
    private TextField pseudo;
    @FXML
    private Label errorLabel;

    public PopupController() {

    }

    public void setService(Service service) {
        this.service = service;
    }

    public void onSendMessage(Event event) {
        if (this.service.changePseudo(pseudo.getText()) == false) {
            errorLabel.setVisible(true);
            pseudo.clear();
        }
        else {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }
}
