package udp;

import metiers.Service;
import models.Message;
import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import observers.CanalObservable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class CanalUDP implements CanalObservable {
    private UDPServer UDPServ;
    private UDPSender UDPClient;
    private InetAddress broadcast;
    private int portUDP;

    // Test attributes
    private List<Message> bufferMessagesRecv = new ArrayList<Message>();

    public CanalUDP() throws UnknownHostException, SocketException {
        this.broadcast = InetAddress.getByName("255.255.255.255");
        this.portUDP = 14000;
        this.UDPServ = new UDPServer(this);
        this.UDPServ.start();
        this.UDPClient = new UDPSender();
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

    void messageHandler(Object o) throws UnknownHostException {

        Message m = (Message) o;

        this.bufferMessagesRecv.add(m);

        if (!m.getSource().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress())) {

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
    public void notifyMessageConnect(MessageConnect m) {
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

    // Test function

    public List<Message> getBufferMessagesRecv() {
        return this.bufferMessagesRecv;
    }

    public void shutDown() {
        this.UDPServ.shutdown();
        this.UDPServ.interrupt();
    }

}
