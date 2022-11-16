package Observers;

public interface Observable {
    void subscribe(Observer o);
    void notify(Object o);
}
