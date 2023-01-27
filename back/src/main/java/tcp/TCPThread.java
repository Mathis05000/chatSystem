package tcp;

import models.Message;
import models.MessageChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TCPThread extends Thread {

    private Socket link;
    private HandlerTCP myHandlerTCP;
    private boolean run = true;
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
        while (run) {
            try {
                this.myHandlerTCP.messageHandler(this.TCPRecv());
            } catch (IOException e) {
                System.out.println("link closed");
                this.run = false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutDown() {
        this.run = false;
        try {
            this.link.close();
        } catch (IOException e) {
            System.out.println("socket closed");
        }
    }
}
