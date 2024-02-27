package Connection;

public interface ServerConnection {
    void sendObject(Object obj);
    Object receiveData();
    void closeConnection();
}
