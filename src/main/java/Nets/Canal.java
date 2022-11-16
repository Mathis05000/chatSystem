package Nets;

import Models.Message;
import Models.MessageConnect;
import Models.MessageConnectAck;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Canal {
    private UDPServer UDPServ;
    private UDPSender UDPClient;
    private InetAddress broadcast;

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
}
