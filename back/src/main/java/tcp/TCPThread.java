package tcp;

import models.Message;
import models.MessageChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TCPThread extends Thread {

    private Socket link;
    private HandlerTCP myHandlerTCP;
    public TCPThread(Socket link, HandlerTCP handlerTCP) {
        this.link = link;
        this.myHandlerTCP = handlerTCP;
    }

    private Message TCPRecv() throws IOException, ClassNotFoundException {

        ObjectInputStream inputStream = new ObjectInputStream(link.getInputStream());
        MessageChat message = (MessageChat) inputStream.readObject();
        message.setSource(link.getInetAddress());

        return message;
    }

    public void run() {
        while (true) {
            try {
                this.myHandlerTCP.messageHandler(this.TCPRecv());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutDown() {
        try {
            this.link.close();
        } catch (IOException e) {
            System.out.println("socket closed");
        }
    }
}
