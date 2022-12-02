package udp;

import metiers.Service;
import models.*;
import observers.CanalUDPObservable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class CanalUDP implements CanalUDPObservable {
    private UDPServer UDPServ;
    private UDPSender UDPClient;
    private InetAddress broadcast;
    private int portUDP = 14000;

    private String localAddr;

    // Test attributes
    private List<Message> bufferMessagesRecv = new ArrayList<Message>();

    public CanalUDP() throws UnknownHostException, SocketException {
        this.broadcast = InetAddress.getByName("255.255.255.255");
        this.UDPServ = new UDPServer(this);
        this.UDPServ.start();
        this.UDPClient = new UDPSender();
    }

    public void sendSetup(String id) throws IOException {
        MessageSetup message = new MessageSetup(id);
        this.UDPClient.send(message, this.broadcast, this.portUDP);
    }

    public void sendSetupAck(String pseudo, InetAddress addr) throws IOException {
        MessageSetupAck message = new MessageSetupAck(pseudo);
        this.UDPClient.send(message, addr, this.portUDP);
    }

    public void sendConnect(String pseudo) throws IOException {
        MessageConnect message = new MessageConnect(pseudo);
        this.UDPClient.send(message, this.broadcast, this.portUDP);
    }

    public void sendConnectAck(String pseudo, boolean valide, InetAddress address) throws IOException {
        MessageConnectAck message = new MessageConnectAck(pseudo, valide);
        this.UDPClient.send(message, address, this.portUDP);
    }

    public void sendDisconnect(String pseudo) throws IOException {
        MessageDisconnect message = new MessageDisconnect(pseudo);
        this.UDPClient.send(message, this.broadcast, this.portUDP);
    }

    void messageHandler(Message o) throws IOException {

        Message m = (Message) o;

        this.bufferMessagesRecv.add(m);

        if (!m.getSource().getHostAddress().equals(this.localAddr)) {

            if (m instanceof MessageSetup) {
                this.notifyMessageSetup((MessageSetup) m);
            }

            if (m instanceof MessageSetupAck) {
                this.notifyMessageSetupAck((MessageSetupAck) m);
            }

            if (m instanceof MessageConnect) {
                this.notifyMessageConnect((MessageConnect) m);
            }

            if (m instanceof MessageConnectAck) {
                this.notifyMessageConnectAck((MessageConnectAck) m);
            }

            if (m instanceof MessageDisconnect) {
                this.notifyMessageDisconnect((MessageDisconnect) m);
            }
        }
    }

    // getters

    int getPortUDP() {
        return this.portUDP;
    }

    // Observable

    @Override
    public void subscribe(Service service) {
        this.observers.add(service);
    }

    @Override
    public void notifyMessageSetup(MessageSetup m) throws IOException {
        for (Service observer : this.observers) {
            observer.processMessageSetup(m);
        }
    }

    @Override
    public void notifyMessageSetupAck(MessageSetupAck m) throws IOException {
        for (Service observer : this.observers) {
            observer.processMessageSetupAck(m);
        }
    }

    @Override
    public void notifyMessageConnect(MessageConnect m) throws IOException {
        for (Service observer : this.observers) {
            observer.processMessageConnect(m);
        }
    }

    @Override
    public void notifyMessageConnectAck(MessageConnectAck m) {
        for (Service observer : this.observers) {
            observer.processMessageConnectAck(m);
        }
    }

    @Override
    public void notifyMessageDisconnect(MessageDisconnect m) {
        for (Service observer : this.observers) {
            observer.processMessageDisconnect(m);
        }
    }

    // Observer
    @Override
    public void update(String addr) {
        this.localAddr = addr;
    }

    // Test function

    public List<Message> getBufferMessagesRecv() {
        return this.bufferMessagesRecv;
    }

    public void shutDown() {
        this.UDPServ.shutdown();
        this.UDPServ.interrupt();
    }

}
