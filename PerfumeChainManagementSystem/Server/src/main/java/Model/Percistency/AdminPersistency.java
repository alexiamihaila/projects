package Model.Percistency;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPersistency {

    private com.mysql.jdbc.Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
            "root", "Wahn9tg8!");

    public AdminPersistency() throws SQLException {

    }

    public String selectAllUsers() throws SQLException {
        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM users");

        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String username = rs.getString("username");
            String password = rs.getString("password");
            String function = rs.getString("userType");
            String storeId = rs.getString("storeId");
            String managerId = rs.getString("managerId");

            s += username + " " + password + " " + function + " " + storeId + " "+ managerId + "\n";

        }
        return s;
    }


    public String getUserByName(String name) throws SQLException {
        String s = "";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) connection
                    .prepareStatement("SELECT * FROM users WHERE username = ? ");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()){
            s = rs.getString("username");
        }
        return s;
    }

    public int insertUser(String username, String password, String userType, String managerId, String storeId) throws SQLException {

        String s = getUserByName(username);
        if(s.equals("")){
            if(managerId.equals("") && storeId.equals("")){
                PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("INSERT INTO users(username, password, userType) VALUES(?, ?, ?)");

                st.setString(1, username);
                st.setString(2, password);
                st.setString(3, userType);
                st.executeUpdate();
                return 1;
                //JOptionPane.showMessageDialog(null, "User has been enserted into the table");
            }else {


                PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("INSERT INTO users(username, password, userType, managerId, storeId) VALUES(?, ?, ?, ?, ?)");

                st.setString(1, username);
                st.setString(2, password);
                st.setString(3, userType);
                st.setString(4, managerId);
                st.setString(5, storeId);
                st.executeUpdate();
                //JOptionPane.showMessageDialog(null, "User has been enserted into the table");
                return 1;
            }

    }else{
            //JOptionPane.showMessageDialog(null,"Username already exists");
            return 2;
        }
    }

    public int deleteUser(String name) throws SQLException {
       String s =  getUserByName(name);
       if(s.equals(""))
           return 0;

       PreparedStatement st = (PreparedStatement) connection
               .prepareStatement("DELETE FROM users WHERE username=?");

       st.setString(1, name);
       st.executeUpdate();

       return 1;
    }

    public String selectUser(String name) throws SQLException{
        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM users WHERE username=?");
        st.setString(1,name);
        ResultSet rs = st.executeQuery();
         if(rs.next()){
             String us = rs.getString("username");
             String pas = rs.getString("password");
             String type = rs.getString("userType");
             String mid = rs.getString("managerId");
             String sid = rs.getString ("storeId");

             s += us + " " + pas + " " + type + " " + mid + " " + sid + "\n";
         }
        return s;
    }

    public String updateUser(String username, String password, String userType, String managerId, String storeId) throws SQLException {
        String s = "";
        String ss =  getUserByName(username);

        if(ss.equals(""))
            s = "User does not exist in the database!";
        else{
            PreparedStatement st;
            if(managerId.equals("") || storeId.equals("")) {
               st = (PreparedStatement) connection
                        .prepareStatement("UPDATE users SET password = ?, userType = ? WHERE username = ?");
                st.setString(1, password);
                st.setString(2, userType);
                st.setString(3, username);
            }else{

             st = (PreparedStatement) connection
                .prepareStatement("UPDATE users SET password = ?, userType = ?, managerId = ?, storeId = ? WHERE username = ?");
                st.setString(1, password);
                st.setString(2, userType);
                st.setString(3,managerId);
                st.setString(4,storeId);
                st.setString(5, username);
            }


        st.executeUpdate();
        s = "User has been updated";}
        return s;
        //JOptionPane.showMessageDialog(null, "User has been updated");
    }


    public String filterByCommand(String function) throws SQLException {
        String s = "";
        PreparedStatement st = null;

        st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM users where userType=?");

        st.setString(1,function);
        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            String us = rs.getString("username");
            String pas = rs.getString("password");
            String type = rs.getString("userType");
            String mid = rs.getString("managerId");
            String sid = rs.getString ("storeId");

            s += us + " " + pas + " " + type + " " + mid + " " + sid + "\n";
        }
        return s;
    }

}
