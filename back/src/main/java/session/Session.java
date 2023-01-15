package session;

import db.Dao;
import models.MessageChat;
import models.RemoteUser;
import tcp.TCPSender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session implements ISession{

    private String id;
    private List<MessageChat> messages = new ArrayList<MessageChat>();
    private RemoteUser user;
    private TCPSender myTCPSender;

    public Session(RemoteUser user) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.myTCPSender = new TCPSender(this.user);
    }

    public Session(RemoteUser user, String id) throws IOException {
        this.id = id;
        this.user = user;
        this.myTCPSender = new TCPSender(this.user);
    }

    public Session(RemoteUser user, String id, List<MessageChat> messages) throws IOException {
        this.id = id;
        this.user = user;
        this.messages = messages;
        this.myTCPSender = new TCPSender(this.user);
    }

    public String getId() {
        return id;
    }

    public void addMessage(MessageChat message) {
        this.messages.add(message);

        // add Message to Database
        Dao.getInstance().insertMessage(message);

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

    @Override
    public String toString() {
        return user.getPseudo();
    }
}
