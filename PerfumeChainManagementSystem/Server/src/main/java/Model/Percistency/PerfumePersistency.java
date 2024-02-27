package Model.Percistency;

import Model.Perfume;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public String selectPerfumeByName(String name) throws SQLException {
        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes WHERE name = ?");


        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            s = rs.getString("name");

        }
        System.out.println(s);
        // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }



    public String selectAfterNameAndProducer(String name, String producer) throws SQLException {
        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes WHERE producer = ? AND name = ?");


        st.setString(1, producer);
        st.setString(2, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {

            s = rs.getString("producer");

        }
        System.out.println(s);
        // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }

    public String selectAllPerfumes() throws SQLException {
        String s = "";
        String name = "";
        String id = "";
        String producer = "";
        String price = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes");

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
             id = rs.getString("perfumeID");
             name = rs.getString("name");
             producer = rs.getString("producer");
             price = rs.getString("price");

            s += "Id: " + id + ", Name:  " + name + ", Producer: " + producer + ", Price: " + price + "\n";
        }

        // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }


    public ArrayList<Perfume> allPerfumesList() throws SQLException {
        ArrayList<Perfume> perfumeList = new ArrayList<>();

        String s = "";
        String name = "";
        String id = "";
        String producer = "";
        String price = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes");

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            id = rs.getString("perfumeID");
            name = rs.getString("name");
            producer = rs.getString("producer");
            price = rs.getString("price");

            s += "Id: " + id + ", Name:  " + name + ", Producer: " + producer + ", Price: " + price + "\n";

            perfumeList.add(new Perfume(name, producer, Integer.parseInt(price)));
        }

        return perfumeList;

    }


}
