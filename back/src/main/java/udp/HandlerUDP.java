package udp;

import metiers.IService;
import metiers.Service;
import models.*;

import java.io.IOException;

public class HandlerUDP {

    private IService service;
    private UDPServer server;

    public HandlerUDP() {
        this.server = new UDPServer(this);
        this.server.start();
    }

    void messageHandler(Message o) throws IOException {

        Message m = (Message) o;

        if (!m.getSource().getHostAddress().equals(this.service.getLocalAddr())) {

            if (m instanceof MessageSetup) {
                this.service.processMessageSetup((MessageSetup) m);
            }

            if (m instanceof MessageSetupAck) {
                this.service.processMessageSetupAck((MessageSetupAck) m);
            }

            if (m instanceof MessageConnect) {
                this.service.processMessageConnect((MessageConnect) m);
            }

            if (m instanceof MessageConnectAck) {
                this.service.processMessageConnectAck((MessageConnectAck) m);
            }

            if (m instanceof MessageDisconnect) {
                this.service.processMessageDisconnect((MessageDisconnect) m);
            }

            if (m instanceof MessageSession) {
                this.service.processMessageSession((MessageSession) m);
            }

            if (m instanceof MessagePseudo) {
                this.service.processMessagePseudo((MessagePseudo) m);
            }
        }
    }

    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }
}
