package tcp;

import models.MessageChat;
import models.RemoteUser;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPSender {

    private Socket link;
    public TCPSender(RemoteUser user) throws IOException {
        System.out.println("send link");
        this.link = new Socket(user.getAddr(), 15000);
    }

    public void send(MessageChat message) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(this.link.getOutputStream());
        outputStream.writeObject(message);
    }
}
