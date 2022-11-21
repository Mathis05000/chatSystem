package metiers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import nets.CanalUDP;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Service {

    CanalUDP myCanalUDP;
    public Service() throws SocketException, UnknownHostException {
        this.myCanalUDP = new CanalUDP();
        this.myCanalUDP.subscribe(this);
    }

    public void processMessageConnect(MessageConnect message) {
        System.out.println("processMessageConnect");
        // TODO
    }

    public void processMessageConnectAck(MessageConnectAck message) {
        System.out.println("processMessageConnectAck");
        // TODO
    }

    public void processMessageDisconnect(MessageDisconnect message) {
        System.out.println("processMessageDisconnect");
        // TODO
    }
}
