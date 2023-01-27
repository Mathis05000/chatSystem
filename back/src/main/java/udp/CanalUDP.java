package udp;

import models.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class CanalUDP implements ICanalUDP{
    private UDPSender UDPClient;
    private InetAddress broadcast = InetAddress.getByName("255.255.255.255");
    public CanalUDP() throws UnknownHostException, SocketException {
        this.UDPClient = new UDPSender();
    }

    public void sendSetup(String id) throws IOException {
        MessageSetup message = new MessageSetup(id);
        this.UDPClient.send(message, this.broadcast);
    }

    public void sendSetupAck(String pseudo, InetAddress address) throws IOException {
        MessageSetupAck message = new MessageSetupAck(pseudo);
        this.UDPClient.send(message, address);
    }

    public void sendConnect(String pseudo) throws IOException {
        MessageConnect message = new MessageConnect(pseudo);
        this.UDPClient.send(message, this.broadcast);
    }

    public void sendConnectAck(String pseudo, boolean valide, InetAddress address) throws IOException {
        MessageConnectAck message = new MessageConnectAck(pseudo, valide);
        this.UDPClient.send(message, address);
    }

    public void sendDisconnect(String pseudo) throws IOException {
        MessageDisconnect message = new MessageDisconnect(pseudo);
        this.UDPClient.send(message, this.broadcast);
    }

    public void sendSession(String id, InetAddress address) throws IOException {
        MessageSession message = new MessageSession(id);
        this.UDPClient.send(message, address);
    }

    @Override
    public void sendPseudo(String oldPseudo, String newPseudo) throws IOException {
        MessagePseudo message = new MessagePseudo(oldPseudo, newPseudo);
        this.UDPClient.send(message, this.broadcast);
    }
}
