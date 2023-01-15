package tcp;

import metiers.IService;
import metiers.Service;
import models.Message;
import models.MessageChat;
import observers.CanalTCPObservable;

import java.io.IOException;

public class HandlerTCP {

    private int portTCP = 15000;
    private TCPServer TCPServer;
    private IService service;

    public HandlerTCP() throws IOException {
        this.TCPServer = new TCPServer(this);
        this.TCPServer.start();
    }

    public int getPortTCP() {
        return portTCP;
    }

    public void messageHandler(Message m) throws IOException {
        if (m instanceof MessageChat) {
            service.processMessageChat((MessageChat) m);
        }
    }

    // Spring
    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }
    //
}
