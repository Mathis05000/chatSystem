import udp.CanalUDP;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        CanalUDP canalUDP = new CanalUDP();
        canalUDP.sendConnect("mathis");

    }
}
