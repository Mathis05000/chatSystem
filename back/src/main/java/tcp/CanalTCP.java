package tcp;

import metiers.Service;
import models.Message;
import models.MessageChat;
import observers.CanalTCPObservable;

import java.io.IOException;

public class CanalTCP implements CanalTCPObservable {

    private int portTCP = 15000;
    private TCPServer TCPServer;

    public CanalTCP() throws IOException {
        this.TCPServer = new TCPServer(this);
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
