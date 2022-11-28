package metiers;

import models.User;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

class Config {

    private String pseudo;
    private List<User> remoteUsers = new ArrayList<User>();

    public Config() {

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void addRemoteUser(User user) {
        this.remoteUsers.add(user);
    }

    public void delRemoteUser(InetAddress addr) {
        for (User user : this.remoteUsers) {
            if (user.getAddr().getHostAddress().equals(addr)) {
                // test
                System.out.println("Disconnect : " + user);
                //
                this.remoteUsers.remove(user);
            }
        }
    }

    public List<User> getRemoteUsers() {
        return this.remoteUsers;
    }
}
