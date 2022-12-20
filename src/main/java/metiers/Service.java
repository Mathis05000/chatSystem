package metiers;

import models.*;
import observers.CanalTCPObservable;
import observers.CanalTCPObserver;
import udp.CanalUDP;
import observers.CanalUDPObserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Service implements CanalUDPObserver, CanalTCPObserver {

    private CanalUDP myCanalUDP;
    private Config myConfig;

    public Service() throws SocketException, UnknownHostException {
        this.myCanalUDP = new CanalUDP();

        // Observers
        this.myCanalUDP.subscribe(this);
        this.subscribe(this.myCanalUDP);
        //

        this.myConfig = new Config();
    }

    // Service
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

    public void serviceSendSession(Session session) throws IOException {
        this.myCanalUDP.sendSession(session.getId(), session.getUser().getAddr());
    }

    public void serviceSendChat(MessageChat message, Session session) throws IOException {
        session.send(message);
    }
    ///////////

    // Observer
    @Override
    public void processMessageSetup(MessageSetup message) throws IOException {
        if (message.getData().equals(this.myConfig.getIdSetup())) {
            this.myConfig.setAddr(message.getSource());
            this.notify(this.myConfig.getAddr().getHostAddress());
        }
        else {
            if (this.myConfig.isConnected()) {
                this.myCanalUDP.sendSetupAck(this.myConfig.getPseudo(), message.getSource());
            }
        }
    }

    @Override
    public void processMessageSetupAck(MessageSetupAck message) throws IOException {
        if (this.myConfig.isConnected()) {
            this.myConfig.addReservedPseudos(message.getData());
        }
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
    public void processMessageSession(MessageSession message) {
        RemoteUser user = this.myConfig.getUserByAddr(message.getSource());
        this.myConfig.addSession(new Session(user, message.getData()));
    }

    @Override
    public void processMessageChat(MessageChat message) throws IOException {
        for (Session session : this.myConfig.getSessions()) {
            if (session.getId().equals(message.getIdSession())) {
                session.addMessage(message);
            }
        }
    }

    //////////

    // Observable

    @Override
    public void subscribe(CanalUDP canalUDP) {
        this.observers.add(canalUDP);
    }

    @Override
    public void notify(String addr) {
        for (CanalUDP c : this.observers) {
            c.update(addr);
        }
    }

    //////////

    // Getters

    public List<RemoteUser> getRemoteUsers() {
        return this.myConfig.getRemoteUsers();
    }

    //////////

    // Setters

    public void setPseudo(String newPseudo) {
        this.myConfig.setPseudo(newPseudo);
    }

    //////////

    // Test methodes

    public InetAddress getAddr() {
        return this.myConfig.getAddr();
    }

    public List<RemoteUser> getUsers() {
        return this.myConfig.getRemoteUsers();
    }

    public List<Session> getSessions() {
        return this.myConfig.getSessions();
    }


}
