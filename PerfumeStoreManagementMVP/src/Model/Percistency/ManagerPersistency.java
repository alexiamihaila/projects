package Model.Percistency;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerPersistency {

    private com.mysql.jdbc.Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
            "root", "Wahn9tg8!");

    public ManagerPersistency() throws SQLException{
    }

    public String searchPerfumeByName(String name) throws SQLException {
        String s  = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes WHERE name=?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if(rs.next()){
            String producer = rs.getString("producer");
            String price = rs.getString("price");
            String id = rs.getString("perfumeID");

            s += "Id: " + id + ", Name: " + name + ", Producer: " + producer + ", Price: " + price + "\n";
        }
        return s;
    }


    public String filterByProducer() throws SQLException {
        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes ORDER BY producer");

        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String name = rs.getString("name");
            String producer = rs.getString("producer");
            String price = rs.getString("price");
            String id = rs.getString("perfumeID");

            s += "Id: " + id + ", Name: " + name + ", Producer: " + producer + ", Price: " + price + "\n";
        }
        return s;
    }

    public String filterByPrice() throws SQLException {
        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes ORDER BY price");

        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String name = rs.getString("name");
            String producer = rs.getString("producer");
            String price = rs.getString("price");
            String id = rs.getString("perfumeID");

            s += "Id: " +id + ", Name:  " + name + ", Price:  " + price + ", Producer: " + producer + "\n";

        }
        return s;
    }

    public String filterByStock(String storeId) throws SQLException {
        String s = "";
        String producer = "";
        String name = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, quantity FROM stocks WHERE storeId = ? ORDER BY quantity");

        st.setString(1, storeId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String pfID = rs.getString("perfumeID");
            String quantity = rs.getString("quantity");

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT name FROM perfumes WHERE perfumeID = ?");
            st1.setString(1, pfID);
            ResultSet rs1 = st1.executeQuery();
            String namep = "";
            if(rs1.next()){
                namep = rs1.getString("name");
            }

            s += "Id: " + pfID + ", Name:  " + namep + ", Quantity: " + quantity + "\n";
        }
        return s;
    }

    public String filterByPrice(String storeId) throws SQLException {
            String s = "";
            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes ORDER BY price");
            ResultSet rs1 = st1.executeQuery();

            while(rs1.next()){

                String name = rs1.getString("name");
                String producer = rs1.getString("producer");
                String price = rs1.getString("price");
                String id = rs1.getString("perfumeID");

                PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("SELECT storeID FROM stocks WHERE  perfumeId = ?");
                //st.setString(1, storeId);
                st.setString(1,id);

                ResultSet rs = st.executeQuery();

                if(rs.next()) {
                    if(rs.getString("storeId").equals(storeId))
                       s += "Id: " + id + ", Name: " + name + ", Price:  " + price + ", Producer:  " + producer + "\n";
                }
            }

        return s;
    }

    public String filterByName(String storeId) throws SQLException {
        String s = " ";
        PreparedStatement st1 = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes ORDER BY name");
        ResultSet rs1 = st1.executeQuery();

        while(rs1.next()){

            String name = rs1.getString("name");
            String producer = rs1.getString("producer");
            String price = rs1.getString("price");
            String id = rs1.getString("perfumeID");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("SELECT storeID FROM stocks WHERE  perfumeId = ?");
            //st.setString(1, storeId);
            st.setString(1,id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                if(rs.getString("storeId").equals(storeId))
                    s += "Id: " + id + ", Name:  " + name + ", Price:  " + price + ", Producer: " + producer + "\n";

            }
        }
        return s;

    }
}


