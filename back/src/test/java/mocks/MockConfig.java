package mocks;

import commun.ConfigObserver;
import metiers.IConfig;
import models.MessageChat;
import models.RemoteUser;
import session.ISession;
import session.Session;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockConfig implements IConfig {

    private String pseudo;
    private String idSetup = "idSetupTest";
    private boolean connected;
    private InetAddress localAddress;
    private List<String> reservedPseudo = new ArrayList<>();
    private List<RemoteUser> remoteUsers = new ArrayList<>();
    private List<ISession> sessions = new ArrayList<>();

    @Override
    public String getPseudo() {
        return this.pseudo;
    }

    @Override
    public InetAddress getAddr() {
        return this.localAddress;
    }

    @Override
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public void setAddr(InetAddress addr) {
        this.localAddress = addr;
    }

    @Override
    public void addRemoteUser(RemoteUser user) {
        this.remoteUsers.add(user);
    }

    @Override
    public void delRemoteUser(InetAddress addr) {
        RemoteUser tmpUser = null;
        for (RemoteUser user : this.remoteUsers) {
            if (user.getAddr().equals(addr)) {
                tmpUser = user;
            }
        }
        if (tmpUser != null) {
            this.remoteUsers.remove(tmpUser);
        }
    }

    @Override
    public RemoteUser getUserByAddr(InetAddress addr) {
        for (RemoteUser user : this.remoteUsers) {
            if (user.getAddr().getHostAddress().equals(addr.getHostAddress())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<RemoteUser> getRemoteUsers() {
        return remoteUsers;
    }

    @Override
    public String getIdSetup() {
        return this.idSetup;
    }

    @Override
    public List<String> getReservedPseudos() {
        return this.reservedPseudo;
    }

    @Override
    public void addReservedPseudos(String pseudo) {
        this.reservedPseudo.add(pseudo);
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public List<ISession> getSessions() {
        return this.sessions;
    }

    @Override
    public void addSession(ISession session) {
        this.sessions.add(session);
    }

    @Override
    public void addStoredSession(Session session) {

    }

    @Override
    public void delSession(RemoteUser user) {

    }

    @Override
    public void addMessage(MessageChat message) {

    }

    @Override
    public boolean checkPseudo(String pseudo) {
        return true;
    }

    @Override
    public void changePseudoRemoteUser(String oldPseudo, String newPseudo) {

    }

    @Override
    public void subscribe(ConfigObserver observer) {

    }

    @Override
    public void notifyChangeRemoteUsers() {

    }

    @Override
    public void notifyChangeSessions() {

    }
}
