package Model.Percistency;

import View.AdminPage;
import View.EmployeePage;
import View.ManagerPage;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInPersistency {
    public LogInPersistency(){

    }

    public void logIn(String username, String password, EmployeePage employeePage, AdminPage adminPage, ManagerPage managerPage){
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

                    employeePage.setVisible();
                    employeePage.setStoreId(storeID);
                }
                else if(roleDB.equals("MANAGER")) {
                    managerPage.setVisible();

                }
                else {

                    adminPage.setVisible();
                }

                JOptionPane.showMessageDialog(null,"You have successfully logged in");
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username & Password");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }
}
