package session;

import db.Dao;
import models.MessageChat;
import models.RemoteUser;
import tcp.TCPSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ISession {

    String getId();

    void addMessage(MessageChat message);

    RemoteUser getUser();

    void send(MessageChat message) throws IOException;

    List<MessageChat> getMessages();
}
