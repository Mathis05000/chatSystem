package udp;

import models.Message;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

class UDPServer extends Thread {

    private DatagramSocket dgramSocket;
    private byte[] buffer;
    private int sizeBuf = 500;
    private CanalUDP myCanalUDP;

    UDPServer(CanalUDP canalUDP) throws SocketException {
        this.dgramSocket = new DatagramSocket(canalUDP.getPortUDP());
        this.buffer = new byte[this.sizeBuf];
        this.myCanalUDP = canalUDP;
    }

    private Message UDPRecv() throws IOException, ClassNotFoundException {

        DatagramPacket inPacket = new DatagramPacket(this.buffer, this.buffer.length);

        dgramSocket.receive(inPacket);

        InetAddress address = inPacket.getAddress();

        ByteArrayInputStream byteStream = new ByteArrayInputStream(this.buffer);
        ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(byteStream));
        Message message = (Message) inStream.readObject();
        inStream.close();

        message.setSource(address);

        return message;
    }

    public void run() {
        while (true) {
            try {
                this.myCanalUDP.messageHandler(this.UDPRecv());
            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.dgramSocket.close();
    }
}