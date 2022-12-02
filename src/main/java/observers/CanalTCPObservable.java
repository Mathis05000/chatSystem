package observers;

import metiers.Service;
import models.MessageChat;
import models.MessageSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface CanalTCPObservable {

    List<Service> observers = new ArrayList<Service>();

    void notifyMessageChat(MessageChat m) throws IOException;
    void subscribe(Service service);
}
