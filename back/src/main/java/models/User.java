package models;

import java.net.InetAddress;
import java.util.Objects;

public class User {

    private String pseudo;
    private InetAddress addr;

    public User(String pseudo, InetAddress addr) {
        this.pseudo = pseudo;
        this.addr = addr;
    }

    public User() {

    }

    public String getPseudo() {
        return pseudo;
    }

    public InetAddress getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }
}
