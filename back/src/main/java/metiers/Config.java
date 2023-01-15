package metiers;

import db.DAO;
import models.*;
import commun.ConfigObservable;
import commun.ConfigObserver;
import session.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Config implements ConfigObservable {

    private LocalUser myUser = new LocalUser();
    private String idSetup = UUID.randomUUID().toString();
    private List<Session> sessions =  new ArrayList<Session>();
    private List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
    private List<String> reservedPseudos = new ArrayList<String>();
    private boolean connected;

    public Config() throws IOException {

        connected = false;
        // Mocks
        /*RemoteUser user1 = new RemoteUser("thomas", null);
        RemoteUser user2 = new RemoteUser("basile", null);
        remoteUsers.add(user1);
        remoteUsers.add(user2);

        Session session1 = new Session(user1);
        Session session2 = new Session(user2);
        session1.addMessage(new MessageChat("salut session 1"));
        session1.addMessage(new MessageChat("ça va 1 ?"));
        session2.addMessage(new MessageChat("salut session 2"));
        session2.addMessage(new MessageChat("ça va 2 ?"));
        sessions.add(session1);
        sessions.add(session2);*/
        //
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
            String idSession = DAO.getInstance().getSession(user);
            if (idSession != null) {
                List<MessageChat> messages = DAO.getInstance().getMessages(idSession);
                this.addStoredSession(new Session(user, idSession, messages));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void delRemoteUser(InetAddress addr) {
        for (RemoteUser user : this.remoteUsers) {
            if (user.getAddr().getHostAddress().equals(addr)) {
                // test
                System.out.println("Disconnect : " + user);
                //
                this.remoteUsers.remove(user);

                this.delSession(user);
            }
        }
        this.notifyChangeRemoteUsers();
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

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
        this.notifyChangeSessions();

        // add session to Database
        try {
            DAO.getInstance().insertSession(session);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        return this.reservedPseudos.contains(pseudo);
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
}