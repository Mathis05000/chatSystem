package gui.front;

import commun.MessageObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import metiers.Service;
import models.MessageChat;
import models.RemoteUser;
import models.Session;
import commun.ConfigObserver;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
        this.service = new Service();
        this.service.subscribeConfig(this);
        this.service.subscribe(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize Remote User List and Session List
        this.updateListRemoteUsers();
        this.updateListSession();

        // Session selected on this.selectedSession
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
                    this.service.serviceSendSession(new Session(user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            else {
                this.selectedSession = session;
                this.updateConversation();
            }
        });
    }

    public void updatePseudo(String pseudo) {
        this.service.setPseudo(pseudo);
    }

    public void onSendMessage() throws IOException {
        this.selectedSession.send(new MessageChat(input_text.getText()));
    }

    public void updateConversation() {
        this.conversation.getChildren().clear();
        for (MessageChat message : this.selectedSession.getMessages()) {
            Label label = new Label(message.getData());
            label.setMaxWidth(Double.MAX_VALUE);
            if (message.getSource() == null) {
                label.setAlignment(Pos.BASELINE_RIGHT);
            }
            else {
                label.setAlignment(Pos.BASELINE_LEFT);
            }
            this.conversation.getChildren().add(label);
        }
    }

    @Override
    public void updateListRemoteUsers() {
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
        if (this.selectedSession.getId().equals(id)) {
            this.updateConversation();
        }
    }
    // test

    public void addSession() throws IOException {
        System.out.println(this.observableListSession.size());
        RemoteUser user = new RemoteUser("rocky", null);
        this.service.addRemoteUser(user);
        this.service.addSession(new Session(user));
        System.out.println("addSession");
        System.out.println(this.observableListSession.size());
    }


}
