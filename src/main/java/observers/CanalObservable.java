package observers;

import models.MessageConnect;
import metiers.Service;
import models.MessageConnectAck;
import models.MessageDisconnect;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public interface CanalObservable {

    List<Service> observers = new ArrayList<Service>();
    void subscribe(Service service);
    void notifyMessageConnect(MessageConnect m);
    void notifyMessageConnectAck(MessageConnectAck m);
    void notifyMessageDisconnect(MessageDisconnect m);
}
