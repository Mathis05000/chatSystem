package models;

import java.net.InetAddress;

public class User {

    private String pseudo;
    private InetAddress addr;

    public User(String pseudo, InetAddress addr) {
        this.pseudo = pseudo;
        this.addr = addr;
    }

    public String getPseudo() {
        return pseudo;
    }

    public InetAddress getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", addr=" + addr +
                '}';
    }
}
