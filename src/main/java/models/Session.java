package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Session {

    private String id;
    private List<MessageChat> messages = new ArrayList<MessageChat>();
    private RemoteUser user;

    public Session(RemoteUser user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
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
}
