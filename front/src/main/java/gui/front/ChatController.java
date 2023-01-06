package gui.front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import metiers.Service;
import models.RemoteUser;
import models.Session;
import commun.ConfigObserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable, ConfigObserver {

    @FXML
    private Label label;
    @FXML
    private ListView listUser;
    @FXML
    private ListView listSession;
    private Service service;
    private Session selectedSession;
    private ObservableList<RemoteUser> observableListRemoteUsers;
    private ObservableList<Session> observableListSession;



    public ChatController() throws IOException {
        this.service = new Service();
        this.service.subscribe(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        this.observableListRemoteUsers = FXCollections.observableList(service.getRemoteUsers());
        this.listUser.setItems(this.observableListRemoteUsers);
        listUser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.observableListSession = FXCollections.observableList(service.getSessions());
        this.listSession.setItems(this.observableListSession);
        listSession.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.listSession.setOnMouseClicked(event -> {
            Session session = (Session) listSession.getSelectionModel().getSelectedItem();
            this.selectedSession = session;
            this.updatePseudo(this.selectedSession.toString());
        });
    }

    public void updatePseudo(String pseudo) {
        this.service.setPseudo(pseudo);
        this.label.setText(pseudo);
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
}
