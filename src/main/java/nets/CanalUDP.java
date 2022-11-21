package nets;

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

public class CanalUDP implements CanalObservable {
    private UDPServer UDPServ;
    private UDPSender UDPClient;
    private InetAddress broadcast;

    public CanalUDP() throws UnknownHostException, SocketException {
        this.broadcast = InetAddress.getByName("255.255.255.255");
        this.UDPServ = new UDPServer(this);
        this.UDPServ.start();
        this.UDPClient = new UDPSender();
    }


    public void sendConnect(String pseudo) throws IOException {
        Message message = new MessageConnect(pseudo);
        this.UDPClient.send(message, this.broadcast);
    }

    public void sendConnectAck(String pseudo, boolean valide, InetAddress address) throws IOException {
        Message message = new MessageConnectAck(pseudo, valide);
        this.UDPClient.send(message, address);
    }

    public void messageHandler(Object o) throws UnknownHostException {

        Message m = (Message) o;

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

}
