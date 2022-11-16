package Observers;

import java.net.UnknownHostException;

public interface Observable {
    void subscribe(Observer o);
    void notify(Object o) throws UnknownHostException;
}
