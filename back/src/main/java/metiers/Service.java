package metiers;

import commun.MessageObservable;
import commun.MessageObserver;
import models.*;
import observers.CanalTCPObserver;
import commun.ConfigObserver;
import session.ISession;
import session.Session;
import tcp.HandlerTCP;
import udp.ICanalUDP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Service implements MessageObservable, IService {

    private ICanalUDP myCanalUDP;
    private IConfig myConfig;

    public Service() throws IOException {
    }

    // Functions call in front
    public void serviceSendSetup() throws IOException {
        this.myCanalUDP.sendSetup(this.myConfig.getIdSetup());
    }

    public void serviceSendConnect() throws IOException {
        this.myCanalUDP.sendConnect(this.myConfig.getPseudo());
        this.myConfig.setConnected(true);
    }

    public void serviceSendDisconnect() throws IOException {
        this.myCanalUDP.sendDisconnect(this.myConfig.getPseudo());
        this.myConfig.setConnected(false);
    }

    public void serviceSendSession(ISession session) throws IOException {
        this.myConfig.addSession(session);
        this.myCanalUDP.sendSession(session.getId(), session.getUser().getAddr());
    }
    ///////////

    // Interface IService for IOS
    @Override
    public void processMessageSetup(MessageSetup message) throws IOException {
        if (message.getData().equals(this.myConfig.getIdSetup())) {
            this.myConfig.setAddr(message.getSource());
        }
        else {
            if (this.myConfig.isConnected()) {
                this.myCanalUDP.sendSetupAck(this.myConfig.getPseudo(), message.getSource());
            }
        }
    }

    @Override
    public void processMessageSetupAck(MessageSetupAck message) throws IOException {
        this.myConfig.addReservedPseudos(message.getData());
    }

    @Override
    public void processMessageConnect(MessageConnect message) throws IOException {
        if (this.myConfig.isConnected()) {
            this.myCanalUDP.sendConnectAck(this.myConfig.getPseudo(), true, message.getSource());
            this.myConfig.addRemoteUser(new RemoteUser(message.getData(), message.getSource()));
        }
    }

    @Override
    public void processMessageConnectAck(MessageConnectAck message) {
        this.myConfig.addRemoteUser(new RemoteUser(message.getData(), message.getSource()));
    }

    @Override
    public void processMessageDisconnect(MessageDisconnect message) {
        this.myConfig.delRemoteUser(message.getSource());
    }

    @Override
    public void processMessageSession(MessageSession message) throws IOException{
        RemoteUser user = this.myConfig.getUserByAddr(message.getSource());
        this.myConfig.addSession(new Session(user, message.getData()));
    }

    @Override
    public void processMessageChat(MessageChat message) throws IOException {
        for (ISession session : this.myConfig.getSessions()) {
            if (session.getId().equals(message.getIdSession())) {
                session.addMessage(message);
                this.notifyChangeMessage(session.getId());
            }
        }
    }

    //////////


    // Getters

    public List<RemoteUser> getRemoteUsers() {
        return this.myConfig.getRemoteUsers();
    }
    public List<ISession> getSessions() {
        return this.myConfig.getSessions();
    }
    public String getLocalAddr() {
        if (this.myConfig.getAddr() == null) {
            return null;
        }
        return this.myConfig.getAddr().getHostAddress();
    }

    public String getIdSetup() {
        return this.myConfig.getIdSetup();
    }

    //////////

    // Setters call in front

    public boolean setPseudo(String newPseudo) {

        if (this.checkPseudo(newPseudo) == false) {
            return false;
        }
        this.myConfig.setPseudo(newPseudo);
        return true;
    }
    public void addRemoteUser(RemoteUser user) {
        this.myConfig.addRemoteUser(user);
    }

    public void addSession(Session session) throws SQLException {
        this.myConfig.addSession(session);
    }

    //////////

    // Tools
    public boolean checkPseudo(String pseudo) {
        return this.myConfig.checkPseudo(pseudo);
    }
    //



    //////////

    // Observable for front
    public void subscribeConfig(ConfigObserver observer) {
        this.myConfig.subscribe(observer);
    }
    @Override
    public void subscribe(MessageObserver observer) {
        this.messageObservers.add(observer);
    }

    @Override
    public void notifyChangeMessage(String id) {
        for (MessageObserver observer : this.messageObservers) {
            observer.updateMessage(id);
        }
    }
    //

    // Spring
    public ICanalUDP getMyCanalUDP() {
        return myCanalUDP;
    }

    public void setMyCanalUDP(ICanalUDP myCanalUDP) {
        this.myCanalUDP = myCanalUDP;
    }

    public IConfig getMyConfig() {
        return myConfig;
    }

    public void setMyConfig(IConfig myConfig) {
        this.myConfig = myConfig;
    }
    //
}