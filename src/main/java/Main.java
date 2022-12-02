import metiers.Service;
import models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Service myService = new Service();

        System.out.println("enter pseudo");
        Scanner sc = new Scanner(System.in);
        String pseudo = sc.next();

        myService.setPseudo(pseudo);
        myService.serviceSendSetup();

        System.out.println("connected");

        sc.next();

        System.out.println("Remote users :");

        for (User user : myService.getRemoteUsers()) {
            System.out.println(user);
        }

    }
}
