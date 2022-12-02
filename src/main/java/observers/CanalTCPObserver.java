package observers;

import models.MessageChat;
import models.MessageSetup;

import java.io.IOException;

public interface CanalTCPObserver {
    void processMessageChat(MessageChat message) throws IOException;
}
