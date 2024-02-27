package Model.Percistency;

import Model.User;
import Model.UserType;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPersistency {
    private com.mysql.jdbc.Connection connection;

    public UserPersistency()  {

    }

    {
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                    "root", "Wahn9tg8!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertIntoUserTable(User user) throws SQLException {
        String userId = Integer.toString(user.getUserId());
        String username = user.getUsername();
        String password = user.getPassword();
        String userType = user.getUserType().toString();
        String managerId = "";
        String storeId = "";
        String sqlQuery = "";



        if(user.getManagerId() == 0 && user.getStoreID() == 0){
            sqlQuery = "INSERT INTO users (username, password, userType) VALUES ('"  + username + "', '" + password + "', '" + userType +  "')";
        }

        else {
            managerId = Integer.toString(user.getManagerId());
            storeId = Integer.toString(user.getStoreID());
            sqlQuery = "INSERT INTO users ( username, password, userType, managerId, storeId) VALUES ('" + username + "', '" + password + "', '" + userType + "', '" + managerId + "', '" + storeId + "')";
        }

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement(sqlQuery);
        st.executeUpdate();
    }

    public String findByUsername(String username) throws SQLException {
        int userId;
        String username1 = "";
        String password = "";
        UserType userType;
        int managerId = 0;
        int storeId = 0;

        User user = null;

        Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                "root", "Wahn9tg8!");

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("Select * from users where username=?");

        st.setString(1, username);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            userId = Integer.parseInt(rs.getString("userId"));
            username1 = rs.getString("username");
            password = rs.getString("password");
            userType = UserType.valueOf(rs.getString("userType"));
            String managerId1 = rs.getString("managerId");


            if(managerId1 != null){
            managerId = Integer.parseInt(rs.getString("managerId"));}

            String storeId1 = rs.getString("storeId");
            if(storeId1 != null){
            storeId = Integer.parseInt(rs.getString("storeId"));}

            if(managerId1 == null && storeId1 == null)
            user = new User(userId, username1, password, userType);
            else
                user = new User(userId, username1, password, userType, managerId,storeId );

        }
        if(user != null)
            return password;

        return "null";

    }


    public String findPassword(int id) throws SQLException {
        String password = "";
        String id1 = Integer.toString(id);

        Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                "root", "Wahn9tg8!");

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("Select password from users where userId=?");

        st.setString(1, id1);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            password = rs.getString("password");

        }
        return password;
    }
}
