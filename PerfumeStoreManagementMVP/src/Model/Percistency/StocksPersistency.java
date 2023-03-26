package Model.Percistency;

import Model.Stock;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class StocksPersistency {

    public StocksPersistency() throws SQLException{

    }

    private com.mysql.jdbc.Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
            "root", "Wahn9tg8!");

    public void insertIntoStocksTable(Stock stocks) throws SQLException {
        String perfumeId = Integer.toString(stocks.getPerfumeID());
        String storeId = Integer.toString(stocks.getStoreID());
        String quantity = Integer.toString(stocks.getQuantity());
        String sqlQuery = "";

        sqlQuery = "INSERT INTO stocks (perfumeID, storeID, quantity) VALUES ('" +perfumeId + "', '" + storeId + "',' " + quantity + "')";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement(sqlQuery);
        st.executeUpdate();
    }
}
