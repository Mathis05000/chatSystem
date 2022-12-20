package models;

import java.util.Date;

public class MessageChat extends Message {

    private Date date;
    private String idSession;

    public MessageChat(String data, String idSession) {
        super(data);
        this.date = new Date();
        this.idSession = idSession;
    }

    public String getIdSession() {
        return idSession;
    }
}
