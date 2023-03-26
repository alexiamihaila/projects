package Model.Percistency;

import Model.Perfume;
import Model.Store;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class PerfumePersistency {
    private com.mysql.jdbc.Connection connection;

    {
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                    "root", "Wahn9tg8!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoPerfumesTable(Perfume perfume) throws SQLException {
        String perfumeId = Integer.toString(perfume.getPerfumeId());
        String name = perfume.getName();
        String producer = perfume.getProducer();
        String price = Integer.toString(perfume.getPrice());
        String sqlQuery = "";
        System.out.println(perfumeId);

        sqlQuery = "INSERT INTO perfumes (perfumeId, name, producer, price) VALUES ('" + perfumeId + "', '" + name +  "', '" + producer +  "', '" + price  + "')";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement(sqlQuery);
        st.executeUpdate();
    }
}
