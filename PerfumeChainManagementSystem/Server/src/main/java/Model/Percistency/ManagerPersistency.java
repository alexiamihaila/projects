package Model.Percistency;

import Model.Perfume;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        while(rs.next()){
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

            s += "Id: " + id + ", Name: " + name + ", Producer: " + producer +  "\n";
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

            s += "Id: " +id + ", Name:  " + name + ", Price:  " + price + "\n";

        }
        return s;
    }

    public String filterByStock() throws SQLException {
        String s = "";
        String producer = "";
        String name = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, quantity, storeID FROM stocks ORDER BY quantity");

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String storeID = rs.getString("storeID");
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

            s += "Store Id: " + storeID + " ,Id: " + pfID + ", Name:  " + namep + ", Quantity: " + quantity + "\n";
        }
        return s;
    }

    public String filterByStore() throws SQLException {
        String s = " ";
        PreparedStatement st1 = (PreparedStatement) connection
                .prepareStatement("SELECT storeID, perfumeID FROM stocks ORDER BY storeID");
        ResultSet rs1 = st1.executeQuery();

        while(rs1.next()){
            String storeId = rs1.getString("storeID");
            String perfumeId = rs1.getString("perfumeID");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("SELECT name FROM perfumes WHERE  perfumeId = ?");
            //st.setString(1, storeId);
            st.setString(1, perfumeId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                s += "Store id: " + storeId + ", Perfume id:  " + perfumeId + ", Name: " + name + "\n";
            }
        }
        return s;
    }

    public List<Perfume> generatePerfumeList() throws SQLException {

        List<Perfume> perfumeList = new ArrayList<>();
        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes");

        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String perfumeId = rs.getString("perfumeID");
            String name = rs.getString("name");
            String producer = rs.getString("producer");
            int price = Integer.parseInt(rs.getString("price"));

            Perfume perfume = new Perfume(Integer.parseInt(perfumeId), name, producer, price);
            perfumeList.add(perfume);
        }

        return perfumeList;

    }
}


