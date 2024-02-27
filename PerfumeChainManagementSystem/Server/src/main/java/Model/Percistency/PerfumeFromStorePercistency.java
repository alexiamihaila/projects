package Model.Percistency;

import Model.Perfume;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfumeFromStorePercistency {

    private int storeId;
    private Connection connection;

    {
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/perfumesdatabase?autoReconnect=true&useSSL=false",
                    "root", "Wahn9tg8!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String perfumeId;

    public PerfumeFromStorePercistency() {
    }

    public String selectQuery(String storeId, String name) throws SQLException {
        String s = "";
        Perfume perfume;
        String perfumeName = "";
        String producer1 = "";
        String price1 = "";

        //System.out.println(storeId);
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes WHERE name = ?");


        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            producer1 = rs.getString("producer");
            price1 = rs.getString("price");
            perfumeId = rs.getString("perfumeId");
            //System.out.println(perfumeId);
            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeId, quantity FROM stocks WHERE storeId = ?");
            st1.setString(1, storeId);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()) {
                String perfumeId1 = rs1.getString("perfumeId");
                String quantity1 = rs1.getString("quantity");
                if(perfumeId.equals(perfumeId1)){
                    s += "Id: " + perfumeId + ", Name:  " + name + ", Producer:  " + producer1 + ", Price: " + price1 + ", Quantity: " + quantity1 + "\n";
                }
            }

        }
        System.out.println(s);
       // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }

    public String insertQuery(String storeId, String name, String producer, String quantity) throws SQLException {

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID FROM perfumes WHERE name=? AND producer=?");


        String s = "";
        st.setString(1, name);
        st.setString(2,producer);
        ResultSet rs = st.executeQuery();
        System.out.println("ok");
        if (rs.next()) {
            perfumeId = rs.getString("perfumeID");
            String s1 = "INSERT INTO stocks(storeID, perfumeID, quantity) VALUES(?, ?, ?)";


            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement(s1);

            st1.setString(1, storeId);
            st1.setString(2, perfumeId);
            st1.setString(3, quantity);

            st1.executeUpdate();

            s = "You have successfuly added the perfume into your store";
        } else {
            s = "The perfume does not exist in the data base";
        }
        return s;
    }

    public String deleteQuery(String storeId, String name) throws SQLException {

        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID FROM perfumes WHERE name=?");

        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            perfumeId = rs.getString("perfumeID");
            String s1 = "DELETE FROM stocks WHERE perfumeID=?";

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement(s1);

            st1.setString(1, perfumeId);
            st1.executeUpdate();

            s = "You have successfully deleted the perfume from the store!";
           // JOptionPane.showMessageDialog(null, "You have successfuly deleted the perfume from your store");
        } else {
            s = "The perfume does not exist in the store";
            //JOptionPane.showMessageDialog(null, "The perfume does not exist in the data base");
        }
        return s;
    }

    public String updateQuery(String storeId, String name, String quantity) throws SQLException {

        String s = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID FROM perfumes WHERE name=?");

        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            perfumeId = rs.getString("perfumeID");
            String s1 = "UPDATE stocks SET quantity = ? WHERE perfumeID = ?";

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement(s1);

            st1.setString(1, quantity);
            st1.setString(2, perfumeId);
            st1.executeUpdate();

            s = "You have successfuly updated the perfume from your store";
           // JOptionPane.showMessageDialog(null, "You have successfuly updated the perfume from your store");
        } else {
            s = "The perfume does not exist in the data base";
            //JOptionPane.showMessageDialog(null, "The perfume does not exist in the data base");
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

    public String filterByProducer(String storeId) throws SQLException {
        String s = "";
        String perfumeId1 = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, producer FROM perfumes ORDER BY producer");

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                perfumeId1 = rs.getString("perfumeID");

                PreparedStatement st1 = (PreparedStatement) connection
                        .prepareStatement("SELECT perfumeID FROM stocks WHERE storeID = ?");

                st1.setString(1, storeId);
                ResultSet rs1 = st1.executeQuery();

                while(rs1.next()){
                    if(rs1.getString("perfumeID").equals(rs.getString("perfumeID"))) {
                        String perfumeID1 = rs.getString("perfumeID");
                        String name1 = rs.getString("name");
                        String producer1 = rs.getString("producer");

                    s += "Id: " + perfumeID1 + ", Name: " + name1 + ", Producer: " + producer1 +"\n";
                    }
                }

            }

        return s;
    }

    public String filterByPrice(String storeId) throws SQLException {
        String s = "";
        String perfumeId1 = "";
        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, price FROM perfumes ORDER BY price");

        ResultSet rs = st.executeQuery();

        while(rs.next()){

            perfumeId1 = rs.getString("perfumeID");

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID FROM stocks WHERE storeID = ?");

            st1.setString(1, storeId);
            ResultSet rs1 = st1.executeQuery();

            while(rs1.next()){
                if(rs1.getString("perfumeID").equals(rs.getString("perfumeID"))) {
                    String perfumeID1 = rs.getString("perfumeID");
                    String name1 = rs.getString("name");
                    String price1 = rs.getString("price");

                    s += "Id: " + perfumeID1 + ", Name: " + name1 + ", Price: " + price1 + "\n";
                }
            }

        }

    return s;
    }

    public String selectAllPerfumes(String storeId) throws SQLException {

        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID, name, producer, price FROM perfumes");


        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String perfumeId = rs.getString("perfumeID");
            String name = rs.getString("name");
            String producer = rs.getString("producer");
            String price = rs.getString("price");

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID, quantity FROM stocks WHERE storeID = ?");
            st1.setString(1,storeId);

            ResultSet rs1 = st1.executeQuery();

            while(rs1.next()){
                String perfumeId1 = rs1.getString("perfumeID");
                String quantity = rs1.getString("quantity");

                if(perfumeId.equals(perfumeId1)){
                    s += "Id: " + perfumeId + ", Name: " + name + ", Producer: " + producer + ", Price: " + price + ", Quantity: " + quantity + " \n";
                }
            }

        }

        return s;
    }


    public String filterByNameAndPrice(String storeId) throws SQLException {

        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name, price FROM perfumes ORDER BY name, price");


        ResultSet rs = st.executeQuery();

        while(rs.next()){
            String perfumeId = rs.getString("perfumeID");
            String name = rs.getString("name");
            String price = rs.getString("price");

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID, quantity FROM stocks WHERE storeID = ?");
            st1.setString(1,storeId);

            ResultSet rs1 = st1.executeQuery();

            while(rs1.next()){
                String perfumeId1 = rs1.getString("perfumeID");
                String quantity = rs1.getString("quantity");

                if(perfumeId.equals(perfumeId1)){
                    s += "Id: " + perfumeId + ", Name: " + name + ", " + "Price: " + price + " \n";
                }
            }

        }

        return s;
    }


    public List<Perfume> generatePerfumeList(String storeId) throws SQLException {

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

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeID, quantity FROM stocks WHERE storeID = ?");
            st1.setString(1,storeId);

            ResultSet rs1 = st1.executeQuery();

            while(rs1.next()){
                String perfumeId1 = rs1.getString("perfumeID");
                String quantity = rs1.getString("quantity");
                //int price = Integer.parseInt(rs1.getString("price"));

                if(perfumeId.equals(perfumeId1)){
                    int perfumeID = Integer.parseInt(perfumeId1);
                    s += "Id: " + perfumeId + ", Name: " + name + ", Producer: " + producer + ", Price: " + price + ", Quantity: " + quantity + " \n";
                    //System.out.println(s);

                    Perfume perfume = new Perfume(perfumeID, name, producer, price);
                    perfumeList.add(perfume);
                }
            }

        }

       return perfumeList;

    }

    public String selectPerfumeByName(String storeId, String name) throws SQLException {
        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT perfumeID, name FROM perfumes WHERE name = ?");

        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            perfumeId = rs.getString("perfumeId");
            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT perfumeId, quantity FROM stocks WHERE storeId = ?");
            st1.setString(1, storeId);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()) {
                String perfumeId1 = rs1.getString("perfumeId");
                if(perfumeId.equals(perfumeId1)){
                    s += name ;
                }
            }

        }
        // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }


    public String selectAfterNameAndProducer(String storeId, String name, String producer) throws SQLException {
        String s = "";

        PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("SELECT * FROM perfumes WHERE producer = ? AND name = ?");


        st.setString(1, producer);
        st.setString(2, name);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            String perfumeID = rs.getString("perfumeID");

            PreparedStatement st1 = (PreparedStatement) connection
                    .prepareStatement("SELECT * FROM stocks WHERE perfumeID = ? AND storeID = ?");

            st1.setString(1, perfumeID);
            st1.setString(2, storeId);
            ResultSet rs1 = st.executeQuery();

            if(rs1.next()){
                s = producer;
            }

        }
        System.out.println(s);
        // return perfume = new Perfume(perfumeName, producer, price);
        return s;
    }

}

