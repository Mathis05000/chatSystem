package commun;

public interface ConfigObserver {
    void updateListRemoteUsers();
    void updateListSession();
    void updateMessage(String id);
}
