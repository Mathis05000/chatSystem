package metiers;

import commun.ConfigObserver;
import db.Dao;
import models.MessageChat;
import models.RemoteUser;
import session.ISession;
import session.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public interface IConfig {

    String getPseudo();
    void setPseudo(String pseudo);
    InetAddress getAddr();
    void setAddr(InetAddress addr);
    void addRemoteUser(RemoteUser user);
    void delRemoteUser(InetAddress addr);
    RemoteUser getUserByAddr(InetAddress addr);
    List<RemoteUser> getRemoteUsers();
    String getIdSetup();
    List<String> getReservedPseudos();
    void addReservedPseudos(String pseudo);
    boolean isConnected();
    void setConnected(boolean connected);
    List<ISession> getSessions();
    void addSession(ISession session);
    void addStoredSession(Session session);
    void delSession(RemoteUser user);
    boolean checkPseudo(String pseudo);
    void subscribe(ConfigObserver observer);
    void notifyChangeRemoteUsers();
    void notifyChangeSessions();
}
