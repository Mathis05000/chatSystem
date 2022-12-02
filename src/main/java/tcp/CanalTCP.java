package tcp;

import metiers.Service;
import models.Message;
import models.MessageChat;
import models.MessageSetup;
import observers.CanalTCPObservable;

import java.io.IOException;

public class CanalTCP implements CanalTCPObservable {

    private int portTCP = 15000;

    public CanalTCP() {
    }

    public int getPortTCP() {
        return portTCP;
    }

    public void messageHandler(Message m) throws IOException {
        if (m instanceof MessageChat) {
            this.notifyMessageChat((MessageChat) m);
        }
    }

    @Override
    public void notifyMessageChat(MessageChat m) throws IOException {
        for(Service observer : this.observers) {
            observer.processMessageChat(m);
        }
    }

    @Override
    public void subscribe(Service service) {
        this.observers.add(service);
    }
}
