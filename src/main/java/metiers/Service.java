package metiers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import udp.CanalUDP;
import observers.CanalObserver;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Service implements CanalObserver {

    CanalUDP myCanalUDP;
    public Service() throws SocketException, UnknownHostException {
        this.myCanalUDP = new CanalUDP();
        this.myCanalUDP.subscribe(this);
    }


    // Observer
    @Override
    public void processMessageConnect(MessageConnect message) {
        // TODO
    }

    @Override
    public void processMessageConnectAck(MessageConnectAck message) {
        // TODO
    }

    @Override
    public void processMessageDisconnect(MessageDisconnect message) {
        // TODO
    }
}
