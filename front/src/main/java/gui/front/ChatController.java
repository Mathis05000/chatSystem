package gui.front;

import commun.MessageObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metiers.Service;
import models.MessageChat;
import models.RemoteUser;
import session.Session;
import commun.ConfigObserver;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ChatController implements Initializable, ConfigObserver, MessageObserver {

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
    @FXML
    private Button input_button;
    private Service service;
    private Session selectedSession;
    private ObservableList<RemoteUser> observableListRemoteUsers;
    private ObservableList<Session> observableListSession;



    public ChatController() throws IOException {

    }

    public void setService(Service service) throws IOException {
        this.service = service;
        this.service.subscribeConfig(this);
        this.service.subscribe(this);
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
            Session session = this.findSession(user);
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
        System.out.println(service.getRemoteUsers().size());
        this.observableListRemoteUsers = FXCollections.observableList(service.getRemoteUsers());
        this.listUser.setItems(this.observableListRemoteUsers);
        System.out.println("listener");
    }

    @Override
    public void updateListSession() {
        this.observableListSession = FXCollections.observableList(service.getSessions());
        this.listSession.setItems(this.observableListSession);
        System.out.println("listener");
    }

    // Tools

    // Tools

    public Session findSession(RemoteUser user) {
        for (Session session : this.service.getSessions()) {
            System.out.println("session : " + session.getUser().getPseudo());
            System.out.println("user : " + user.getPseudo());
            if (session.getUser().getPseudo().equals(user.getPseudo())) {
                return session;
            }
        }
        return null;
    }

    @Override
    public void updateMessage(String id) {
        if (this.selectedSession != null) {
            if (this.selectedSession.getId().equals(id)) {
                this.updateConversation();
            }
        }

    }

    // test

    public void addSession() throws IOException, SQLException {
        System.out.println(this.observableListSession.size());
        RemoteUser user = new RemoteUser("rocky", null);
        this.service.addRemoteUser(user);
        this.service.addSession(new Session(user));
        System.out.println("addSession");
        System.out.println(this.observableListSession.size());
    }

    public void printUsers() {
        System.out.println(this.service.getRemoteUsers().size());
    }


}
