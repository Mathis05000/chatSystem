package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private int portTCP = 15000;
    private ServerSocket servSocket;
    private HandlerTCP myHandlerTCP;

    public TCPServer(HandlerTCP handlerTCP) throws IOException {
        this.myHandlerTCP = handlerTCP;
        this.servSocket = new ServerSocket(this.portTCP);
    }

    private void TCPRecv() throws IOException, ClassNotFoundException {
        Socket link = servSocket.accept();
        (new TCPThread(link, this.myHandlerTCP)).start();

    }

    public void run() {
        while(true) {
            try {
                this.TCPRecv();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
