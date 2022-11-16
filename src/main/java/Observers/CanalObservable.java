package Observers;

import Models.Message;
import Models.MessageConnect;
import Models.MessageConnectAck;
import Models.MessageDisconnect;
import metiers.Service;

import java.security.Provider;

public interface CanalObservable {
    void subscribe(Service service);
    void notifyMessageConnect(MessageConnect m);
    void notifyMessageConnectAck(MessageConnectAck m);
    void notifyMessageDisconnect(MessageDisconnect m);
}
