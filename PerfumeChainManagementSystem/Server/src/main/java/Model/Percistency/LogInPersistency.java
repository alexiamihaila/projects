package Model.Percistency;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInPersistency {
    public LogInPersistency(){
    }

    public int getStoreId(String username){

        String storeID = "";

        try {

            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                    "root", "Wahn9tg8!");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("Select storeId from users where username=?");

            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                storeID = rs.getString("storeId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(storeID);
        if(storeID != null)
        {return Integer.parseInt(storeID);}
        else return 0;
    }


        public int logIn(String username, String password){
        String roleDB = "";
        String storeID = "";

        try {
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                    "root", "Wahn9tg8!");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("Select username, password, usertype, storeId from users where username=? and password=?");

            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();


            if (rs.next()) {
                roleDB = rs.getString("usertype");
                storeID = rs.getString("storeId");
                if(roleDB.equals("EMPLOYEE")) {
                    return 1;
                    //employeePage.setVisible();
                    //employeePage.setStoreId(storeID);
                }
                else if(roleDB.equals("MANAGER")) {
                    //managerPage.setVisible();
                    return 2;
                }

                else if(roleDB.equals("ADMIN")){
                    return 3;
                    //adminPage.setVisible();
                }



            } else {
                return 0;

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return -1;
    }
}
