package commun;

import java.util.ArrayList;
import java.util.List;

public interface ConfigObservable {

    List<ConfigObserver> observers = new ArrayList<ConfigObserver>();
    void subscribe(ConfigObserver observer);
    void notifyChangeRemoteUsers();
    void notifyChangeSessions();
}
