package tcp;

import models.Message;
import models.MessageChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private ServerSocket servSocket;
    private CanalTCP myCanalTCP;

    public TCPServer(CanalTCP canalTCP) throws IOException {
        this.myCanalTCP = canalTCP;
        this.servSocket = new ServerSocket(this.myCanalTCP.getPortTCP());
    }

    private Message TCPRecv() throws IOException, ClassNotFoundException {
        Socket link = servSocket.accept();
        ObjectInputStream inputStream = new ObjectInputStream(link.getInputStream());
        MessageChat message = (MessageChat) inputStream.readObject();

        return message;
    }

    public void run() {
        while(true) {
            try {
                this.myCanalTCP.messageHandler(this.TCPRecv());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
