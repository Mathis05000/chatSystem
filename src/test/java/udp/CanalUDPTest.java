package udp;

import models.Message;
import models.MessageConnect;
import models.MessageConnectAck;
import models.MessageDisconnect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CanalUDPTest {

    private CanalUDP myCanal;
    private String pseudo;

    @BeforeEach
    void begin() throws SocketException, UnknownHostException {
        this.myCanal = new CanalUDP();
        this.pseudo = "toto";
    }

    @AfterEach
    void end() {
        this.myCanal.shutDown();
    }

    @Test
    public void testConnect() throws IOException, InterruptedException {
        this.myCanal.sendConnect(this.pseudo);
        Thread.sleep(100);
        Message recvMessage = this.myCanal.getBufferMessagesRecv().get(0);

        assertTrue(recvMessage instanceof MessageConnect);
        assertEquals(recvMessage.getData(), this.pseudo);
    }

    @Test
    public void testConnectAck() throws IOException, InterruptedException {
        this.myCanal.sendConnectAck(this.pseudo, true, InetAddress.getLocalHost());
        Thread.sleep(100);
        Message recvMessage = this.myCanal.getBufferMessagesRecv().get(0);

        assertTrue(recvMessage instanceof MessageConnectAck);
        assertEquals(recvMessage.getData(), this.pseudo);
        assertTrue(((MessageConnectAck) recvMessage).isValide());
    }

    @Test
    public void testDisconnect() throws IOException, InterruptedException {
        this.myCanal.sendDisconnect(this.pseudo);
        Thread.sleep(100);
        Message recvMessage = this.myCanal.getBufferMessagesRecv().get(0);

        assertTrue(recvMessage instanceof MessageDisconnect);
        assertEquals(recvMessage.getData(), this.pseudo);
    }
}
