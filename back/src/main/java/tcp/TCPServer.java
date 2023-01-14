package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private ServerSocket servSocket;
    private CanalTCP myCanalTCP;

    public TCPServer(CanalTCP canalTCP) throws IOException {
        this.myCanalTCP = canalTCP;
        this.servSocket = new ServerSocket(this.myCanalTCP.getPortTCP());
    }

    private void TCPRecv() throws IOException, ClassNotFoundException {
        Socket link = servSocket.accept();
        (new TCPThread(link, this.myCanalTCP)).start();

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
