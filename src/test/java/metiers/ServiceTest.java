package metiers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

public class ServiceTest {

    private Service myService;
    @BeforeEach
    void begin() throws SocketException, UnknownHostException {
        this.myService = new Service();
    }

    @Test
    void test() {

    }
}
