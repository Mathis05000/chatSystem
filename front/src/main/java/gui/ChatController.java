package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metiers.Service;
import models.MessageChat;
import models.RemoteUser;
import session.ISession;
import session.Session;
import commun.ConfigObserver;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ChatController implements Initializable, ConfigObserver {

    @FXML
    private TabPane tabpane;
    @FXML
    private Label label;
    @FXML
    private ListView listUser;
    @FXML
    private ListView listSession;
    @FXML
    private VBox conversation;
    @FXML
    private TextField input_text;

    private Service service;
    private ISession selectedSession;
    private ObservableList<RemoteUser> observableListRemoteUsers;
    private ObservableList<ISession> observableListSession;



    public ChatController() throws IOException {

    }

    public void setService(Service service) throws IOException {
        this.service = service;
        this.service.subscribeConfig(this);
        this.service.serviceSendConnect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.listSession.setOnMouseClicked(event -> {
            Session session = (Session) listSession.getSelectionModel().getSelectedItem();
            this.selectedSession = session;
            this.updateConversation();
        });

        this.listUser.setOnMouseClicked(event -> {
            RemoteUser user = (RemoteUser) listUser.getSelectionModel().getSelectedItem();
            ISession session = this.findSession(user);
            tabpane.getSelectionModel().selectNext();

            if (session == null) {
                try {
                    session = new Session(user);

                    this.service.serviceSendSession(session);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.selectedSession = session;
            this.updateConversation();
            this.listSession.getSelectionModel().select(session);
        });
    }

    public void updatePseudo(String pseudo) {
        this.service.setPseudo(pseudo);
    }

    public void onSendMessage() throws IOException {
        System.out.println("test : " + this.selectedSession);
        this.selectedSession.send(new MessageChat(input_text.getText()));
        this.input_text.clear();
        this.updateConversation();
    }
    public void onChangePseudo(Event event) throws IOException {
        FXMLLoader loaderPopup = new FXMLLoader(getClass().getResource("popup-view.fxml"));
        Parent root = loaderPopup.load();
        PopupController popup = loaderPopup.getController();
        popup.setService(this.service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void updateConversation() {
        Platform.runLater(() -> {

            this.conversation.getChildren().clear();

            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (MessageChat message : this.selectedSession.getMessages()) {
                Label label = new Label(message.getData());
                Label meta;
                label.setMaxWidth(Double.MAX_VALUE);
                System.out.println("src : " + message.getSource());
                if (message.getSource() == null) {
                    meta = new Label(s.format(message.getDate()) + " vous : ");
                }
                else {
                    meta = new Label(s.format(message.getDate()) + " " + this.selectedSession.getUser().getPseudo() + " : ");
                }
                label.setAlignment(Pos.BASELINE_RIGHT);
                HBox box = new HBox();
                box.getChildren().addAll(meta, label);
                this.conversation.getChildren().add(box);
            }
        });
    }

    @Override
    public void updateListRemoteUsers() {

        Platform.runLater(() -> {
            this.observableListRemoteUsers = FXCollections.observableList(service.getRemoteUsers());
            this.listUser.setItems(this.observableListRemoteUsers);
        });
    }

    @Override
    public void updateListSession() {
        Platform.runLater(() -> {
            this.observableListSession = FXCollections.observableList(service.getSessions());
            this.listSession.setItems(this.observableListSession);
        });
    }

    @Override
    public void updateMessage(String id) {
        Platform.runLater(() -> {
            if (this.selectedSession != null) {
                if (this.selectedSession.getId().equals(id)) {
                    this.updateConversation();
                }
            }
        });
    }

    // Tools

    // Tools

    public ISession findSession(RemoteUser user) {
        for (ISession session : this.service.getSessions()) {
            System.out.println("session : " + session.getUser().getPseudo());
            System.out.println("user : " + user.getPseudo());
            if (session.getUser().getPseudo().equals(user.getPseudo())) {
                return session;
            }
        }
        return null;
    }
}
