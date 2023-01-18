package gui.front;

import factory.Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import metiers.Service;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField pseudo;
    @FXML
    private Label errorLabel;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private Service service;

    public LoginController() throws IOException {
        this.service = Factory.getService();
        this.service.serviceSendSetup();
    }

    public void login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
        root = loader.load();

        ChatController controller = loader.getController();

        if (this.service.setPseudo(pseudo.getText()) == false) {
            errorLabel.setVisible(true);
            pseudo.clear();
            return;
        }

        controller.setService(this.service);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(pseudo.getText());

        stage.setOnCloseRequest(e -> {
            try {
                this.service.serviceSendDisconnect();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
