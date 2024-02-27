package Model.Percistency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreareBazaDeDateTabele {

    private String DBURL = "jdbc:mysql://localhost:3306/";
    private String USER = "root";
    private String PASS = "Wahn9tg8!";
    private String DBNAME = "perfumesDataBase";
    private String completeDBURL = "jdbc:mysql://localhost:3306/perfumesDataBase?autoReconnect=true&useSSL=false";
    Connection connection = null;
    Statement statement = null;

    public CreareBazaDeDateTabele(){}

    public  String creareBazaDate() throws SQLException {
        String s = "";
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "CREATE DATABASE " + DBNAME;
            statement.executeUpdate(sql);
            System.out.println("Database created successfully!");
            s = "Database created successfully!";

        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
            s = "Error creating database!";
        }
        return s;

    }


    public void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }
    public String creareTabelUser(){
        String s = "";
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
            s = "Table created successfully!!!";
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            // Handle errors for JDBC
            System.out.println("Error!!!");
            s = "Error!!!";
        }
        return s;
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

    public void dropSchema(){
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "DROP DATABASE " + DBNAME;
            statement.executeUpdate(sql);
            System.out.println("Schema dropped successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTableStocks(){
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "DROP TABLE perfumesDataBase.stocks";
            statement.executeUpdate(sql);
            System.out.println("Table stocks dropped successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTableUsers(){
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "DROP TABLE perfumesDataBase.users";
            statement.executeUpdate(sql);
            System.out.println("Table users dropped successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTablePerfumes(){
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "DROP TABLE perfumesDataBase.perfumes";
            statement.executeUpdate(sql);
            System.out.println("Table perfumes dropped successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTableStores(){
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(DBURL, USER, PASS);

            statement = connection.createStatement();
            String sql = "DROP TABLE perfumesDataBase.stores";
            statement.executeUpdate(sql);
            System.out.println("Table stores dropped successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
