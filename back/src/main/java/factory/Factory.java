package factory;

import metiers.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tcp.HandlerTCP;
import udp.HandlerUDP;

public class Factory {
    public static Service getService() {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Service service = context.getBean("service", Service.class);
        HandlerUDP handlerUDP = context.getBean("handlerUDP", HandlerUDP.class);
        HandlerTCP handlerTCP = context.getBean("handlerTCP", HandlerTCP.class);

        return service;
    }
}
