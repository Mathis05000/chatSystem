package tcp;

import metiers.IService;
import metiers.Service;
import models.Message;
import models.MessageChat;
import observers.CanalTCPObservable;
import observers.CloseObserver;

import java.io.IOException;

public class HandlerTCP implements CloseObserver {

    private TCPServer TCPServer;
    private IService service;

    public HandlerTCP() throws IOException {
        this.TCPServer = new TCPServer(this);
        this.TCPServer.start();
    }

    public void messageHandler(Message m) throws IOException {
        if (m instanceof MessageChat) {
            service.processMessageChat((MessageChat) m);
        }
    }

    // Close socket

    public void shutDown() {
        this.TCPServer.shutDown();
    }
    // Spring
    public IService getService() {
        return service;
    }

    public void setService(IService service) {

        this.service = service;
        this.service.subscribe(this);
    }

    @Override
    public void update() {
        this.shutDown();
    }
    //
}
