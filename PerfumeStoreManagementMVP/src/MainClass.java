import Model.Perfume;
import Model.*;
import View.*;
import Model.Percistency.*;

import java.sql.SQLException;

public class MainClass {
    public static CreareBazaDeDateTabele database;
    public static LogInPage logInPage;
    public static AdminPage adminPage;
    public static EmployeePage employeePage;
    public static ManagerPage managerPage;
    public static UserPersistency userPersistency;
    public static StoresPersistency storesPersistency;
    public static PerfumePersistency perfumePersistency;
    public static StocksPersistency stocksPersistency;


    public static void main(String[] args) throws SQLException {
          database = new CreareBazaDeDateTabele();
          adminPage = new AdminPage();
          employeePage = new EmployeePage();
          managerPage = new ManagerPage();
          logInPage = new LogInPage(adminPage, managerPage, employeePage);
          //database.creareBazaDate();
          //database.createTabelPerfume();
          //database.creareTabelUser();
          //database.creareTabelStock();
          //database.creareTabelStores();


          User user = new User(1, "alexiam", "abcd", UserType.MANAGER, 0, 0);
          User user1 = new User(2,"mariapopa", "abcd", UserType.EMPLOYEE, 1, 1);
          User user2 = new User(3,"andreeaB", "abcd", UserType.ADMIN, 0, 0);

          userPersistency = new UserPersistency();

           //userPersistency.insertIntoUserTable(user);
           //userPersistency.insertIntoUserTable(user1);
          //userPersistency.insertIntoUserTable(user2);
          storesPersistency = new StoresPersistency();
          Store store = new Store(1, 1);
          //storesPersistency.insertIntoStoresTable(store);


        Perfume perfume = new Perfume(1, "L Interdit", "Givenchy", 72);
        Perfume perfume1 = new Perfume(2, "Libre", "YSL", 80);
        Perfume perfume2 = new Perfume(3, "J Adore", "Dior", 65);
        Perfume perfume3 = new Perfume(4, "Good Girl", "Carolina Herrera", 73);


        perfumePersistency = new PerfumePersistency();
        //perfumePersistency.insertIntoPerfumesTable(perfume1);
        //perfumePersistency.insertIntoPerfumesTable(perfume2);
       //perfumePersistency.insertIntoPerfumesTable(perfume3);
        //perfumePersistency.insertIntoPerfumesTable(perfume);


        Stock stocks1 = new Stock(1, 1, 70);
        Stock stock2 = new Stock(1, 2, 45);
        Stock stock3 = new Stock(1, 4, 100);
        Stock stock4 = new Stock(2, 3, 39);

        stocksPersistency = new StocksPersistency();
        //stocksPersistency.insertIntoStocksTable(stocks1);
        //stocksPersistency.insertIntoStocksTable(stock2);
        //stocksPersistency.insertIntoStocksTable(stock3);
        //stocksPersistency.insertIntoStocksTable(stock4);

        //Store store1 = new Store(1, 1);
        Store store2 = new Store(2, 1);
        //storesPersistency.insertIntoStoresTable(store1);
        //storesPersistency.insertIntoStoresTable(store2);

    }
}
