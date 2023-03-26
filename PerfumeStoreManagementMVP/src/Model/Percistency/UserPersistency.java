package Model.Percistency;

import Model.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserPersistency {

    public UserPersistency()  {

    }

    private com.mysql.jdbc.Connection connection;

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
}
