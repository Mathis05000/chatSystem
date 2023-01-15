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


}
