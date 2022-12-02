package models;

import java.net.InetAddress;

public class RemoteUser extends User{
    public RemoteUser(String pseudo, InetAddress addr) {
        super(pseudo, addr);
    }
}
