package Observers;

public interface Observable {
    void subscribe(Object o);
    void notify(Object o);
}
