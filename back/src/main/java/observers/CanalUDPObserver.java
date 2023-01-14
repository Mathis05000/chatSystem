package observers;

import models.*;
import udp.CanalUDP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CanalUDPObserver {

    // Observer
    void processMessageSetup(MessageSetup message) throws IOException;
    void processMessageSetupAck(MessageSetupAck message) throws IOException;
    void processMessageConnect(MessageConnect message) throws IOException;
    void processMessageConnectAck(MessageConnectAck message);
    void processMessageDisconnect(MessageDisconnect message);
    void processMessageSession(MessageSession message) throws IOException, SQLException;

    // Observable

    List<CanalUDP> observers = new ArrayList<CanalUDP>();
    void subscribe(CanalUDP canalUDP);

    void notify(String addr);
}
