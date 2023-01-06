package metiers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServiceTest {

    private Service myService;
    @BeforeEach
    void begin() throws IOException {
        this.myService = new Service();
    }

    @Test
    void test() {

    }
}
