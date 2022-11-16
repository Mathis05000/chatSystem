package Nets;

import Models.Message;
import Models.MessageConnect;
import Models.MessageConnectAck;
import Models.MessageDisconnect;
import Observers.CanalObservable;
import Observers.CanalObserver;
import Observers.Observable;
import Observers.Observer;
import metiers.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Canal implements Observer, CanalObservable {
    private UDPServer UDPServ;
    private UDPSender UDPClient;
    private InetAddress broadcast;
    private List<Service> observers = new ArrayList<Service>();

    public Canal() throws UnknownHostException, SocketException {
        this.broadcast = InetAddress.getByName("255.255.255.255");
        this.UDPServ = new UDPServer();
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

    @Override
    public void update(Object o) throws UnknownHostException {

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
