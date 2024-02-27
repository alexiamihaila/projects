package Connection;

public class ProxyServer implements ServerConnection{
    private Server server;
    private static ProxyServer instance;

    public static ProxyServer getInstance(){
        if(instance == null){
            instance = new ProxyServer();
        }
        return instance;
    }

    public ProxyServer(){
        this.server=Server.getInstance();
    }

    @Override
    public void sendObject(Object obj) {
        server.sendObject(obj);
    }

    @Override
    public Object receiveData() {
        return server.receiveData();
    }

    @Override
    public void closeConnection() {
        server.closeConnection();
    }
}
