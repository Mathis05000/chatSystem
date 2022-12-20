package observers;

import models.*;
import metiers.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public interface CanalUDPObservable {

    // Observable
    List<Service> observers = new ArrayList<Service>();
    void subscribe(Service service);
    void notifyMessageSetup(MessageSetup m) throws IOException;
    void notifyMessageSetupAck(MessageSetupAck m) throws IOException;
    void notifyMessageConnect(MessageConnect m) throws IOException;
    void notifyMessageConnectAck(MessageConnectAck m);
    void notifyMessageDisconnect(MessageDisconnect m);
    void notifyMessageSession(MessageSession m);

    // Observer

    void update(String addr);
}
