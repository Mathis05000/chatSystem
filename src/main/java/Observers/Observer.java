package Observers;

import java.net.UnknownHostException;

public interface Observer {
    void update(Object o) throws UnknownHostException;
}
