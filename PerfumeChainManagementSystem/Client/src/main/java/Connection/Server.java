package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Server implements Serializable, ServerConnection {
    private Socket socket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private static final Server instance = new Server();


    public static Server getInstance() {
        return instance;
    }

    public Server() {
        try {
            socket = new Socket("localhost", 1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendObject(Object obj)
    {
        try {
            objectOutputStream.writeObject(obj);
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object receiveData() {
        Object data = null;
        try {
            data = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public void closeConnection() {
        try {
            if (socket != null)
                socket.close();
            if (objectInputStream != null)
                objectInputStream.close();
            if(objectOutputStream!=null)
                objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
