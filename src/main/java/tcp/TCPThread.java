package tcp;

import models.Message;
import models.MessageChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TCPThread extends Thread {

    private Socket link;
    private CanalTCP myCanalTCP;
    public TCPThread(Socket link, CanalTCP canalTCP) {
        this.link = link;
        this.myCanalTCP = canalTCP;
    }

    private Message TCPRecv() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(link.getInputStream());
        MessageChat message = (MessageChat) inputStream.readObject();

        return message;
    }

    public void run() {
        try {
            this.myCanalTCP.messageHandler(this.TCPRecv());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
