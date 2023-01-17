package metiers;

import db.IDao;
import models.*;
import commun.ConfigObservable;
import commun.ConfigObserver;
import session.ISession;
import session.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Config implements ConfigObservable, IConfig {

    private LocalUser myUser = new LocalUser();
    private String idSetup = UUID.randomUUID().toString();
    private List<ISession> sessions =  new ArrayList<ISession>();
    private List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
    private List<String> reservedPseudos = new ArrayList<String>();
    private boolean connected;
    private IDao dao;

    public Config() throws IOException {

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
        this.notifyChangeRemoteUsers();

        // Load bind session if exist
        try {
            String idSession = dao.getSession(user);
            if (idSession != null) {
                List<MessageChat> messages = dao.getMessages(idSession);
                this.addStoredSession(new Session(user, idSession, messages));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

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

    public RemoteUser getUserByAddr(InetAddress addr) {
        for (RemoteUser user : this.remoteUsers) {
            if (user.getAddr().getHostAddress().equals(addr.getHostAddress())) {
                return user;
            }
        }
        return null;
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

    public List<ISession> getSessions() {
        return sessions;
    }

    public void addSession(ISession session) {
        this.sessions.add(session);
        this.notifyChangeSessions();

        // add session to Database
        dao.insertSession(session);
    }

    public void addStoredSession(Session session) {
        this.sessions.add(session);
        this.notifyChangeSessions();
    }

    public void delSession(RemoteUser user) {
        this.sessions.forEach(session -> {
            if (session.getUser().getPseudo().equals(user.getPseudo())) {
                this.sessions.remove(session);
            }
        });

        this.notifyChangeSessions();
    }

    public boolean checkPseudo(String pseudo) {
        return !this.reservedPseudos.contains(pseudo);
    }

    @Override
    public void subscribe(ConfigObserver observer) {
        System.out.println("subscribe : " + observer);
        this.observers.add(observer);
    }

    @Override
    public void notifyChangeRemoteUsers() {
        System.out.println("notify change user");
        this.observers.forEach(configObserver -> {
            configObserver.updateListRemoteUsers();
        });
    }

    @Override
    public void notifyChangeSessions() {
        System.out.println("notify change session");
        this.observers.forEach(configObserver -> {
            configObserver.updateListSession();
        });
    }

    // Spring
    public IDao getDao() {
        return dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
    //
}