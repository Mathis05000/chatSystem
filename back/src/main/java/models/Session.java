package models;

import commun.MessageObservable;
import commun.MessageObserver;
import tcp.TCPSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {

    private String id;
    private List<MessageChat> messages = new ArrayList<MessageChat>();
    private RemoteUser user;
    private TCPSender myTCPSender;

    public Session(RemoteUser user) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.myTCPSender = new TCPSender(this.user);
    }

    public Session(RemoteUser user, String id) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void addMessage(MessageChat message) {
        this.messages.add(message);
    }

    public RemoteUser getUser() {
        return user;
    }

    public void send(MessageChat message) throws IOException {
        message.setIdSession(this.id);
        this.messages.add(message);
        this.myTCPSender.send(message);
    }

    public List<MessageChat> getMessages() {
        return messages;
    }
}
