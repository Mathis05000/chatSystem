package models;

import models.Message;

public class MessageConnectAck extends Message {

    boolean valide;

    public MessageConnectAck(String data, boolean valide) {
        super(data);
        this.valide = valide;
    }

    public boolean isValide() {
        return this.valide;
    }
}
