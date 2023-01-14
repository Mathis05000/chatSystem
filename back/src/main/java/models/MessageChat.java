package models;

import java.util.Date;

public class MessageChat extends Message {

    private Date date;
    private String idSession;

    public MessageChat(String data) {
        super(data);
        this.date = new Date();
    }

    public MessageChat(String data, String idSession, Date date) {
        super(data);
        this.date = date;
        this.idSession = idSession;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String id) {
        this.idSession = id;
    }

    public Date getDate() {
        return date;
    }
}
