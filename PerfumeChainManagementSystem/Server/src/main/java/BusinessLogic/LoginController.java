package BusinessLogic;

import Model.Percistency.LogInPersistency;
import Model.Percistency.UserPersistency;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class LoginController {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private LogInPersistency logInPersistency;

    public LoginController(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        this.objectOutputStream=objectOutputStream;
        this.objectInputStream=objectInputStream;

        this.logInPersistency = new LogInPersistency();
    }

    public void login() {

        try {
            String username = (String) objectInputStream.readObject();
            String password = (String) objectInputStream.readObject();


            if(username.equals("") || password.equals("")){
                String message = "Username or password field is empty";
                objectOutputStream.writeObject(message);
            }else{
                UserPersistency userPersistency = new UserPersistency();
                String passw = userPersistency.findByUsername(username);

                objectOutputStream.writeObject(passw);
                if(passw.equals("null")){
                    String messag = "User does not exist in the database";
                    objectOutputStream.writeObject(messag);
                }
                else {
                    if(passw.equals(password)){
                        int i =  logInPersistency.logIn(username,password);
                        objectOutputStream.writeObject(i);

                        if(i == 1){
                            int id = logInPersistency.getStoreId(username);
                            objectOutputStream.writeObject(id);
                        }
                    }
                    else{
                        String messa = "Incorrect password";
                        objectOutputStream.writeObject(messa);
                    }

                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
