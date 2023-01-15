package mocks;

import udp.ICanalUDP;

import java.io.IOException;
import java.net.InetAddress;

public class MockCanalUDP implements ICanalUDP {

    public String stringBouchon;
    public InetAddress inetBouchon;
    public boolean boolBouchon;
    @Override
    public void sendSetup(String id) throws IOException {
        this.stringBouchon = id;
    }

    @Override
    public void sendSetupAck(String pseudo, InetAddress addr) throws IOException {
        this.stringBouchon = pseudo;
        this.inetBouchon = addr;
    }

    @Override
    public void sendConnect(String pseudo) throws IOException {
        this.stringBouchon = pseudo;
    }

    @Override
    public void sendConnectAck(String pseudo, boolean valide, InetAddress address) throws IOException {
        this.stringBouchon = pseudo;
        this.boolBouchon = valide;
        this.inetBouchon = address;
    }

    @Override
    public void sendDisconnect(String pseudo) throws IOException {
        this.stringBouchon = pseudo;
    }

    @Override
    public void sendSession(String id, InetAddress addr) throws IOException {
        this.stringBouchon = id;
        this.inetBouchon = addr;
    }


}
