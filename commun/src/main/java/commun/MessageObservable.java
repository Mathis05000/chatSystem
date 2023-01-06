package commun;

import java.util.ArrayList;
import java.util.List;

public interface MessageObservable {
    List<MessageObserver> messageObservers = new ArrayList<MessageObserver>();
    void subscribe(MessageObserver observer);
    void notifyChangeMessage(String id);
}
