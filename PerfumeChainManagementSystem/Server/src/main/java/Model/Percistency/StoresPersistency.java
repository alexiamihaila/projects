package Model.Percistency;

import Model.Store;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class StoresPersistency
{
    public StoresPersistency() throws SQLException {

    }

    private com.mysql.jdbc.Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
            "root", "Wahn9tg8!");

    public void insertIntoStoresTable(Store store) throws SQLException {
        String managerId = Integer.toString(store.getManagerId());
        String storeId = Integer.toString(store.getStoreId());
        String sqlQuery = "";

       sqlQuery = "INSERT INTO stores (storeId, managerId) VALUES ('" +storeId + "', '" + managerId  + "')";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement(sqlQuery);
        st.executeUpdate();
    }
}
