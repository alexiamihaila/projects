package Model.Percistency;

import java.sql.*;

public class CreareBazaDeDateTabele {

    private String DBURL = "jdbc:mysql://localhost:3306/";
    private String USER = "root";
    private String PASS = "Wahn9tg8!";
    private String DBNAME = "perfumesDataBase";
    private String completeDBURL = "jdbc:mysql://localhost:3306/perfumesDataBase?autoReconnect=true&useSSL=false";

    public  void creareBazaDate() {

        try {

            Connection connection = DriverManager.getConnection(DBURL, USER, PASS);

            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE " + DBNAME;
            statement.executeUpdate(sql);
            System.out.println("Database created successfully!");

            // inchidem conexiunea cu baza de date
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    public void creareTabelUser(){

        try {
            Connection conn = DriverManager.getConnection(completeDBURL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE users   " +
                    "(userId INTEGER not NULL AUTO_INCREMENT, " +
                    " username VARCHAR(255), " +
                    " password VARCHAR(255), " +
                    " userType VARCHAR(255), " +
                    "managerId INTEGER, " +
                    "storeId INTEGER, " +
                    " PRIMARY KEY ( userId ))";

            stmt.executeUpdate(sql);
            System.out.println("Table created successfully!!!");

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            // Handle errors for JDBC
            System.out.println("Error!!!");
        }
    }

    public void createTabelPerfume(){
        try {
            Connection conn = DriverManager.getConnection(completeDBURL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE perfumes " +
                    "(perfumeID INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " producer VARCHAR(255), " +
                    " price INTEGER, " +
                    " PRIMARY KEY ( perfumeID ))";

            stmt.executeUpdate(sql);
            System.out.println("Table created successfully!!!");

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            // Handle errors for JDBC
            System.out.println("Error!!!");
        }

    }
    public void creareTabelStock(){
        try{
            Connection conn = DriverManager.getConnection(completeDBURL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE stocks " +
                    "(perfumeID INTEGER, " +
                    " storeID INTEGER, " +
                    " quantity INTEGER )";

            stmt.executeUpdate(sql);
            System.out.println("Table created successfully!!!");

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
             // Handle errors for JDBC
            System.out.println("Error!!!");
         }
    }

    public void creareTabelStores() {

        try {
            Connection conn = DriverManager.getConnection(completeDBURL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE stores " +
                    "(storeId INTEGER  not NULL, " +
                    " managerId INTEGER not NULL," +
                    " PRIMARY KEY ( storeId ))";

            stmt.executeUpdate(sql);
            System.out.println("Table created successfully!!!");

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
                // Handle errors for JDBC
                System.out.println("Error!!!");
        }
    }

}
