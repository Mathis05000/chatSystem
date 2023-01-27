package udp;

import models.Message;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

class UDPServer extends Thread {

    private HandlerUDP handler;
    private DatagramSocket dgramSocket;
    private byte[] buffer;
    private int sizeBuf = 500;
    private int portUDP = 14000;

    private boolean run = true;

    UDPServer(HandlerUDP handlerUDP) {
        try {
            this.dgramSocket = new DatagramSocket(this.portUDP);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.buffer = new byte[this.sizeBuf];
        this.handler = handlerUDP;
    }

    private Message UDPRecv() throws ClassNotFoundException, IOException {

        DatagramPacket inPacket = new DatagramPacket(this.buffer, this.buffer.length);

        try {
            dgramSocket.receive(inPacket);
        } catch (IOException e) {
            System.out.println("UDP socket closed");
            return null;
        }

        InetAddress address = inPacket.getAddress();

        ByteArrayInputStream byteStream = new ByteArrayInputStream(this.buffer);
        ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(byteStream));
        Message message = (Message) inStream.readObject();
        inStream.close();

        message.setSource(address);

        return message;
    }

    public void run() {
        while (run) {
            try {
                this.handler.messageHandler(this.UDPRecv());
            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        System.out.println("shutdown UDPServer");
        this.dgramSocket.close();
        this.run = false;
    }
}