package metiers;

import models.*;

import java.io.IOException;

public interface IService {
    String getLocalAddr();
    void processMessageSetup(MessageSetup m) throws IOException;
    void processMessageSetupAck(MessageSetupAck m) throws IOException;
    void processMessageConnect(MessageConnect m) throws IOException;
    void processMessageConnectAck(MessageConnectAck m);
    void processMessageDisconnect(MessageDisconnect m);
    void processMessageSession(MessageSession m) throws IOException;
    void processMessageChat(MessageChat m) throws IOException;

}
