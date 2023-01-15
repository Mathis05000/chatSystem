package udp;

import models.*;

import java.io.IOException;
import java.net.InetAddress;

public interface ICanalUDP {
    void sendSetup(String id) throws IOException;
    void sendSetupAck(String pseudo, InetAddress addr) throws IOException;
    void sendConnect(String pseudo) throws IOException;
    void sendConnectAck(String pseudo, boolean valide, InetAddress address) throws IOException;
    void sendDisconnect(String pseudo) throws IOException;
    void sendSession(String id, InetAddress addr) throws IOException;
}
