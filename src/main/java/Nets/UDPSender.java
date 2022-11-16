package Nets;

import Models.Message;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSender {

    private DatagramSocket dgramSocket;
    private int portUDP = 15000;

    public UDPSender() throws SocketException {
        this.dgramSocket = new DatagramSocket();
    }

    public void send(Message data, InetAddress host) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
        ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(byteStream));

        outStream.flush();
        outStream.writeObject(data);
        outStream.flush();

        byte[] sendBuf = byteStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, host, this.portUDP);

        this.dgramSocket.send(packet);
        outStream.close();
    }
}
