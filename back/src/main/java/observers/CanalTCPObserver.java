package observers;

import models.MessageChat;

import java.io.IOException;

public interface CanalTCPObserver {
    void processMessageChat(MessageChat message) throws IOException;
}
