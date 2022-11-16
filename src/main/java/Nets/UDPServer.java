package Nets;

import Models.Message;
import Observers.Observable;
import Observers.Observer;
import Observers.UDPServerObserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UDPServer extends Thread implements Observable {

    private DatagramSocket dgramSocket;
    private byte[] buffer;
    private int sizeBuf = 500;
    private List<Observer> listObserver = new ArrayList<Observer>();
    private int portUDP = 15000;

    public UDPServer() throws SocketException {
        System.out.println("Server");
        this.dgramSocket = new DatagramSocket(this.portUDP);
        this.buffer = new byte[this.sizeBuf];
    }

    public Message UDPRecv() throws IOException, ClassNotFoundException {

        DatagramPacket inPacket = new DatagramPacket(this.buffer, this.buffer.length);
        dgramSocket.receive(inPacket);

        InetAddress address = inPacket.getAddress();

        ByteArrayInputStream byteStream = new ByteArrayInputStream(this.buffer);
        ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(byteStream));
        Message message = (Message) inStream.readObject();
        inStream.close();

        message.setSource(address);

        return (message);
    }

    public void run() {
        while (true) {
            try {
                this.notify(UDPRecv());
            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void subscribe(Observer o) {
        this.listObserver.add(o);
    }

    @Override
    public void notify(Object o) {
        for (Observer observer : this.listObserver) {
            observer.update(o);
        }
    }
}