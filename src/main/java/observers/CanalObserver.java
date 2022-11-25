package observers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;

import java.io.IOException;

public interface CanalObserver {

    void processMessageConnect(MessageConnect message) throws IOException;

    void processMessageConnectAck(MessageConnectAck message);

    void processMessageDisconnect(MessageDisconnect message);
}
