import metiers.Service;
import models.Session;
import models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Service myService = new Service();

        System.out.println("enter pseudo");
        Scanner sc = new Scanner(System.in);
        String pseudo = sc.next();

        myService.setPseudo(pseudo);
        myService.serviceSendSetup();

        sc.next();

        myService.serviceSendConnect();

        System.out.println("connected");

        sc.next();

        System.out.println("Remote users :");

        for (User user : myService.getRemoteUsers()) {
            System.out.println(user);
        }

        String opt = sc.next();

        if (opt.equals("c")) {
            Session session = new Session(myService.getRemoteUsers().get(0));
            myService.serviceSendSession(session);
            System.out.println("session create " + session.getId());
        }

        else if (opt.equals("a")) {
            for (Session session : myService.getSessions()) {
                System.out.println(session.getId());
            }
        }
    }
}
