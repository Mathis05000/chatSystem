package Models;

import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable {

    private String data;
    private InetAddress src;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public InetAddress getSource() {
        return this.src;
    }

    public void setSource(InetAddress address) {
        this.src = address;
    }
}