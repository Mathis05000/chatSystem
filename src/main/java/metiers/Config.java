package metiers;

import models.LocalUser;
import models.RemoteUser;
import models.User;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Config {

    private LocalUser myUser = new LocalUser();
    private List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
    private String idSetup = UUID.randomUUID().toString();

    private List<String> reservedPseudos = new ArrayList<String>();

    private boolean connected;

    public Config() {
        connected = false;
    }

    // Getters myUser
    public String getPseudo() {
        return this.myUser.getPseudo();
    }

    public InetAddress getAddr() {
        return this.myUser.getAddr();
    }

    // Setters myUser

    public void setPseudo(String pseudo) {
        this.myUser.setPseudo(pseudo);
    }

    public void setAddr(InetAddress addr) {
        this.myUser.setAddr(addr);
    }

    ////

    public void addRemoteUser(RemoteUser user) {
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

    public List<RemoteUser> getRemoteUsers() {
        return this.remoteUsers;
    }

    public String getIdSetup() {
        return idSetup;
    }

    public List<String> getReservedPseudos() {
        return reservedPseudos;
    }

    public void addReservedPseudos(String pseudo) {
        this.reservedPseudos.add(pseudo);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
