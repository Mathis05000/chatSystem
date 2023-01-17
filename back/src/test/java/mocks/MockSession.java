package mocks;

import db.IDao;
import models.MessageChat;
import models.RemoteUser;
import session.ISession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockSession implements ISession {

    private String id;
    private List<MessageChat> messages = new ArrayList<>();
    private RemoteUser user;

    public MockSession(RemoteUser user, String id) {
        this.user = user;
        this.id = id;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void addMessage(MessageChat message) {
        this.messages.add(message);
    }

    @Override
    public RemoteUser getUser() {
        return this.user;
    }

    @Override
    public void send(MessageChat message) throws IOException {

    }

    @Override
    public List<MessageChat> getMessages() {
        return messages;
    }

    @Override
    public void setDao(IDao dao) {

    }
}
