package BusinessLogic;

import Model.Observer;
import Model.Percistency.AdminPersistency;
import Model.User;
import Model.UserType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController implements Subject {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private AdminPersistency adminPersistency;

    private ArrayList<Observer> observers;
    private User user1 = new User(1, "alexiam", "abcd", UserType.MANAGER);
    private String account = "";

    public AdminController(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;

        try {
            this.adminPersistency = new AdminPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        observers = new ArrayList<>();
        this.register(user1);

    }

    public void viewAllUsers(){
        String s = "";
        try {
            AdminPersistency persistency = new AdminPersistency();
            s = persistency.selectAllUsers();
            objectOutputStream.writeObject(s);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(){
        String s = "";
        try {
            String function = (String) objectInputStream.readObject();

            AdminPersistency adminPersistency = new AdminPersistency();
            s = adminPersistency.filterByCommand(function);
            objectOutputStream.writeObject(s);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(){
        int i;

        try {
            String username = (String) objectInputStream.readObject();
            String password =  (String)objectInputStream.readObject();
            String userType = (String) objectInputStream.readObject();

            if (username.equals("") || password.equals("") || userType.equals("")) {
                objectOutputStream.writeObject("Please complete fields: username, password and function!");
            } else {

                String managerId =  (String)objectInputStream.readObject();
                String storeId = (String) objectInputStream.readObject();
                try {
                    AdminPersistency persistency = new AdminPersistency();
                    i = persistency.insertUser(username, password, userType.toUpperCase(), managerId, storeId);

                    objectOutputStream.writeObject(i);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(){

        try {
            String username = (String) objectInputStream.readObject();
            int i;
            if(!username.equals("")){
                try {
                    AdminPersistency adminPersistency = new AdminPersistency();

                    i = adminPersistency.deleteUser(username);
                    objectOutputStream.writeObject(i);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(){

        try {
            String s = "";
            String username = (String) objectInputStream.readObject();
            String password = (String) objectInputStream.readObject();
            String userType = (String) objectInputStream.readObject();
            String managerId = (String) objectInputStream.readObject();
            String storeId = (String) objectInputStream.readObject();

            this.account = username;
            AdminPersistency adminPersistency = new AdminPersistency();
            s = adminPersistency.updateUser(username, password, userType, managerId, storeId);
            objectOutputStream.writeObject(s);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        notifyObserver();
    }

    public void selectUser(){

        try {
            String s = "";
            String username = (String) objectInputStream.readObject();

            if (!username.equals("")) {

                AdminPersistency adminPersistency = new AdminPersistency();
                s = adminPersistency.selectUser(username);

               objectOutputStream.writeObject(s);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer: observers){
            observer.setAccount(account);
            observer.update();

        }

    }
}
