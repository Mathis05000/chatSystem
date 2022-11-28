package metiers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import models.User;
import udp.CanalUDP;
import observers.CanalObserver;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Service implements CanalObserver {

    private CanalUDP myCanalUDP;
    private Config myConfig;

    public Service() throws SocketException, UnknownHostException {
        this.myCanalUDP = new CanalUDP();
        this.myCanalUDP.subscribe(this);
        this.myConfig = new Config();
    }

    public void serviceSendConnect() throws IOException {
        this.myCanalUDP.sendConnect(this.myConfig.getPseudo());
    }

    // Observer
    @Override
    public void processMessageConnect(MessageConnect message) throws IOException {
        this.myCanalUDP.sendConnectAck(this.myConfig.getPseudo(), true, message.getSource());
        this.myConfig.addRemoteUser(new User(message.getData(), message.getSource()));
    }

    @Override
    public void processMessageConnectAck(MessageConnectAck message) {
        this.myConfig.addRemoteUser(new User(message.getData(), message.getSource()));
    }

    @Override
    public void processMessageDisconnect(MessageDisconnect message) {
        this.myConfig.delRemoteUser(message.getSource());
    }

    // Getters

    public List<User> getRemoteUsers() {
        return this.myConfig.getRemoteUsers();
    }

    // Setters

    public void setPseudo(String newPseudo) {
        this.myConfig.setPseudo(newPseudo);
    }
}
