package metiers;

import mocks.MockCanalUDP;
import mocks.MockConfig;
import mocks.MockDao;
import models.RemoteUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    private Config config;
    private MockDao mockDao;
    @BeforeEach
    void begin() throws IOException {
        this.config = new Config();
        this.mockDao = new MockDao();
        this.config.setDao(mockDao);
    }

    @Test
    void testPseudo() throws IOException {
        config.setPseudo("pseudo");
        assertEquals("pseudo", config.getPseudo());
    }

    @Test
    void testAddress() throws UnknownHostException {
        config.setAddr(InetAddress.getByName("10.20.30.40"));
        assertEquals("10.20.30.40", config.getAddr().getHostAddress());
    }

    @Test
    void testAddRemoteUser() throws UnknownHostException {
        config.addRemoteUser(new RemoteUser("userPseudo", InetAddress.getByName("10.20.30.40")));
        assertEquals("userPseudo", config.getRemoteUsers().get(0).getPseudo());
        assertEquals("10.20.30.40", config.getRemoteUsers().get(0).getAddr().getHostAddress());
    }

    @Test
    void testDelRemoteUser() throws UnknownHostException {
        RemoteUser user = new RemoteUser("userPseudo", InetAddress.getByName("10.20.30.40"));
        config.addRemoteUser(user);

        config.delRemoteUser(user.getAddr());

        assertEquals(0, config.getRemoteUsers().size());
    }

    @Test
    void testGetUserById() throws UnknownHostException {
        RemoteUser user = new RemoteUser("userPseudo", InetAddress.getByName("10.20.30.40"));
        config.addRemoteUser(user);
        RemoteUser user2 = config.getUserByAddr(user.getAddr());
        assertEquals(user, user2);
    }

    @Test
    void testConnected() {
        config.setConnected(false);
        assertEquals(false, config.isConnected());

        config.setConnected(true);
        assertEquals(true, config.isConnected());
    }

    @Test
    void testReservedPseudo() {
        config.addReservedPseudos("reservedPseudo1");
        config.addReservedPseudos("reservedPseudo2");
        assertEquals(false, config.checkPseudo("reservedPseudo1"));
        assertEquals(false, config.checkPseudo("reservedPseudo2"));
        assertEquals(true, config.checkPseudo("notReservedPseudo"));
    }
}
