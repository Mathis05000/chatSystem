package gui;

import factory.Factory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import metiers.Service;

import java.io.IOException;

public class App extends Application {

    private Service service;
    @Override
    public void start(Stage stage) throws IOException {
        this.service = Factory.getService();
        this.service.serviceSendSetup();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();
        ((LoginController) fxmlLoader.getController()).setService(this.service);

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(e -> {
            try {
                this.service.serviceSendDisconnect();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
