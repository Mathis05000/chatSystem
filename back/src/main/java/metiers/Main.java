package metiers;

import metiers.Service;
import models.MessageChat;
import models.RemoteUser;
import models.Session;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("last version");
        Scanner sc = new Scanner(System.in);
        Service service = new Service();

        service.serviceSendSetup();

        System.out.println("enter pseudo");
        String pseudo = sc.nextLine();

        service.setPseudo(pseudo);

        service.serviceSendConnect();

        sc.nextLine();

        for (RemoteUser user : service.getRemoteUsers()) {
            System.out.println(user.getPseudo());
        }

        String tmp = sc.nextLine();

        if (tmp.equals("s")) {
            service.serviceSendSession(new Session(service.getRemoteUsers().get(0)));
        }

        if (tmp.equals("r")) {
            System.out.println(service.getSessions().get(0).getUser());
        }

        tmp = sc.nextLine();

        if (tmp.equals("s")) {
            System.out.println("send chat");
            service.serviceSendChat(new MessageChat("salut"), service.getSessions().get(0));
        }

        if (tmp.equals("r")) {
            System.out.println(service.getSessions().get(0).getMessages().get(0).getData());
        }
    }
}
