package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Thread {

    private int portTCP = 15000;
    private ServerSocket servSocket;
    private HandlerTCP myHandlerTCP;
    private List<TCPThread> threads = new ArrayList<>();
    private boolean run = true;

    public TCPServer(HandlerTCP handlerTCP) throws IOException {
        this.myHandlerTCP = handlerTCP;
        this.servSocket = new ServerSocket(this.portTCP);
    }

    private void TCPRecv() throws ClassNotFoundException {
        Socket link = null;
        try {
            link = servSocket.accept();
            TCPThread thread = new TCPThread(link, this.myHandlerTCP);
            thread.start();
            this.threads.add(thread);

        } catch (IOException e) {
            System.out.println("TCP socket closed");
        }


    }

    public void run() {
        while(run) {
            try {
                this.TCPRecv();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutDown() {
        System.out.println("shutdown TCPServer");
        try {
            this.servSocket.close();
        } catch (IOException e) {
            System.out.println("socket closed");
        }

        for(TCPThread thread : this.threads) {
            thread.shutDown();
        }
        this.run = false;
    }
}
