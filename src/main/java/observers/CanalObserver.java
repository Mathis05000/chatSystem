package observers;

import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;

public interface CanalObserver {

    void processMessageConnect(MessageConnect message);

    void processMessageConnectAck(MessageConnectAck message);

    void processMessageDisconnect(MessageDisconnect message);
}
