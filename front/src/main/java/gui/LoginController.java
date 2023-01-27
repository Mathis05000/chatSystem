package gui;

import factory.Factory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metiers.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private TextField pseudo;
    @FXML
    private Label errorLabel;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private Service service;

    public void login(ActionEvent event) throws IOException {

        if (this.service.setPseudo(pseudo.getText()) == false) {
            errorLabel.setVisible(true);
            pseudo.clear();
            return;
        }

        FXMLLoader loaderChat = new FXMLLoader(getClass().getResource("chat-view.fxml"));
        root = loaderChat.load();
        ChatController controller = loaderChat.getController();

        controller.setService(this.service);
        controller.setName(pseudo.getText());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setService(Service service) {
        this.service = service;
    }

}
