package metiers;

import mocks.MockCanalUDP;
import mocks.MockConfig;
import mocks.MockSession;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    private Service service;
    private MockCanalUDP mockCanalUDP;
    private MockConfig mockConfig;
    @BeforeEach
    void begin() throws IOException {
        this.service = new Service();
        this.mockCanalUDP = new MockCanalUDP();
        this.mockConfig = new MockConfig();
        this.service.setMyCanalUDP(mockCanalUDP);
        this.service.setMyConfig(mockConfig);
    }

    @Test
    void testServiceSendSetup() throws IOException {
        String expecetd = this.service.getIdSetup();
        this.service.serviceSendSetup();
        assertEquals(expecetd, this.mockCanalUDP.stringBouchon);
    }

    @Test
    void testServiceSendConnect() throws IOException {
        String expected = "testPseudo";
        this.service.setPseudo("testPseudo");
        this.service.serviceSendConnect();
        assertEquals(expected, this.mockCanalUDP.stringBouchon);
        assertEquals(true, this.mockConfig.isConnected());
    }

    @Test
    void testServiceSendDisconnect() throws IOException {
        String expected = "testPseudo";
        this.service.setPseudo("testPseudo");
        this.service.serviceSendDisconnect();
        assertEquals(this.mockCanalUDP.stringBouchon, expected);
        assertEquals(false, this.mockConfig.isConnected());
    }

    @Test
    void testServiceSendSession() throws IOException {
        InetAddress mockAddress = InetAddress.getByName("10.20.30.40");
        RemoteUser mockUser = new RemoteUser("Thomas", mockAddress);
        MockSession mockSession = new MockSession(mockUser, "idTest");
        this.service.serviceSendSession(mockSession);

        String stringExpected = "idTest";
        String addressExpected = mockAddress.getHostAddress();

        assertEquals(mockCanalUDP.stringBouchon, stringExpected);
        assertEquals(mockCanalUDP.inetBouchon.getHostAddress(), addressExpected);
    }

    @Test
    void testProcessMessageSetup1() throws IOException {
        MessageSetup mockMessage = new MessageSetup("idSetupTest");
        InetAddress mockAddress = InetAddress.getByName("10.20.30.40");
        mockMessage.setSource(mockAddress);
        service.processMessageSetup(mockMessage);
        assertEquals(mockConfig.getAddr().getHostAddress(), mockAddress.getHostAddress());
    }

    @Test
    void testProcessMessageSetup2() throws IOException {
        // message
        MessageSetup mockMessage = new MessageSetup("otherIdSetupTest");
        InetAddress mockAddress = InetAddress.getByName("10.20.30.40");
        mockMessage.setSource(mockAddress);
        //

        mockConfig.setPseudo("testPseudo");
        mockConfig.setConnected(true);

        service.processMessageSetup(mockMessage);
        assertEquals(mockCanalUDP.stringBouchon, "testPseudo");
        assertEquals(mockCanalUDP.inetBouchon.getHostAddress(), "10.20.30.40");
    }
    @Test
    void testProcessMessageSetupAck() throws IOException {
        // message
        MessageSetupAck message = new MessageSetupAck("remotePseudo");
        //
        service.processMessageSetupAck(message);
        assertEquals(mockConfig.getReservedPseudos().get(0), "remotePseudo");
    }

    @Test
    void processMessageConnect() throws IOException {
        // message
        MessageConnect message = new MessageConnect("remotePseudo");
        message.setSource(InetAddress.getByName("10.20.30.40"));
        //
        mockConfig.setConnected(true);
        mockConfig.setPseudo("localPseudo");
        service.processMessageConnect(message);

        assertEquals("localPseudo", mockCanalUDP.stringBouchon);
        assertEquals("10.20.30.40", mockCanalUDP.inetBouchon.getHostAddress());

        assertEquals("remotePseudo", mockConfig.getRemoteUsers().get(0).getPseudo());
        assertEquals("10.20.30.40", mockConfig.getRemoteUsers().get(0).getAddr().getHostAddress());
    }

    @Test
    void processMessageConnectAck() throws UnknownHostException {
        // message
        MessageConnectAck message = new MessageConnectAck("remoteUser", true);
        message.setSource(InetAddress.getByName("10.20.30.40"));
        //

        service.processMessageConnectAck(message);
        assertEquals("remoteUser", mockConfig.getRemoteUsers().get(0).getPseudo());
        assertEquals("10.20.30.40", mockConfig.getRemoteUsers().get(0).getAddr().getHostAddress());
    }

    @Test
    void processMessageDisconnect() throws UnknownHostException, InterruptedException {
        mockConfig.addRemoteUser(new RemoteUser("remotePseudo", InetAddress.getByName("10.20.30.40")));

        // message
        MessageDisconnect message = new MessageDisconnect("remotePseudo");
        message.setSource(InetAddress.getByName("10.20.30.40"));
        //

        service.processMessageDisconnect(message);
        assertEquals(0, mockConfig.getRemoteUsers().size());
    }

    @Test
    void processMessageChat() throws IOException {
        // message
        MessageChat message = new MessageChat("data", "idSession", new Date(1));
        //
        MockSession mockSession = new MockSession(new RemoteUser("remotePseudo", InetAddress.getByName("10.20.30.40")), "idSession");
        mockConfig.addSession(mockSession);
        service.processMessageChat(message);

        assertEquals("idSession", mockSession.getMessages().get(0).getIdSession());
        assertEquals("data", mockSession.getMessages().get(0).getData());

    }
}
