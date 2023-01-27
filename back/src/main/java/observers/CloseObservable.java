package observers;

import java.util.ArrayList;
import java.util.List;

public interface CloseObservable {

    List<CloseObserver> observers = new ArrayList<>();
    default void subscribe(CloseObserver observer) {
        observers.add(observer);
    }

    default void closeNotify() {
        observers.forEach(o -> {
            o.update();
        });
    }
}
