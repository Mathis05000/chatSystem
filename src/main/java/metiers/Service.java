package metiers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import udp.CanalUDP;
import observers.CanalObserver;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Service implements CanalObserver {

    private CanalUDP myCanalUDP;
    private Config myConfig;

    public Service() throws SocketException, UnknownHostException {
        this.myCanalUDP = new CanalUDP();
        this.myCanalUDP.subscribe(this);
        this.myConfig = new Config();
    }

    public void ServiceSendConnect() throws IOException {
        this.myCanalUDP.sendConnect(this.myConfig.getPseudo());
    }

    // Observer
    @Override
    public void processMessageConnect(MessageConnect message) throws IOException {
        this.myCanalUDP.sendConnectAck(this.myConfig.getPseudo(), true, message.getSource());
    }

    @Override
    public void processMessageConnectAck(MessageConnectAck message) {

    }

    @Override
    public void processMessageDisconnect(MessageDisconnect message) {
        // TODO
    }
}
