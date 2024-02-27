
import BusinessLogic.AdminController;
import BusinessLogic.EmployeeController;
import BusinessLogic.LoginController;
import BusinessLogic.ManagerController;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainClass {


    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket serverSocket = null;
        ObjectInputStream objectInputStream=null;
        ObjectOutputStream objectOutputStream=null;


        try {
            serverSocket = new ServerSocket(1234);
            socket = serverSocket.accept();

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Server started... waiting for commands");

            LoginController login = new LoginController(objectOutputStream, objectInputStream);
            EmployeeController employeeCommands = new EmployeeController(objectOutputStream, objectInputStream);
            ManagerController managerCommands = new ManagerController(objectOutputStream, objectInputStream);
            AdminController adminCommands = new AdminController(objectOutputStream, objectInputStream);

            while(true){
                String command= (String) objectInputStream.readObject();
                System.out.println("The command in process is: " + command);
                if(command.equals("LOGIN")){
                    login.login();
                    System.out.println("Login command finished!");
                }
                if(command.equals("Search perfume by name")){
                    employeeCommands.searchByName();
                    System.out.println("Search by name command finished!");
                }
                if(command.equals("View all perfumes")){
                    employeeCommands.viewAllPerfumes();
                    System.out.println("View all perfumes command finished!");
                }
                if(command.equals("View all perfumes from chain")){
                    employeeCommands.viewAllFromChain();
                    System.out.println("View all perfumes from chain command finished!");
                }
                if(command.equals("Add perfume")){
                    employeeCommands.addPerfume();
                    System.out.println("Add perfume command finished!");
                }
                if(command.equals("Delete perfume")){
                    employeeCommands.deletePerfume();
                    System.out.println("Delete perfume command finished!");
                }
                if(command.equals("Select perfume")){
                    employeeCommands.selectPerfume();
                    System.out.println("Select perfume command finished!");
                }
                if(command.equals("Update perfume")){
                    employeeCommands.updatePerfume();
                    System.out.println("Update perfume command finished!");
                }
                if(command.equals("Filter By Name And Price")){
                    employeeCommands.filterByNameAndPrice();
                    System.out.println("Filter By Name and Price command finished!");
                }
                if(command.equals("Filter producer")){
                    employeeCommands.filterProducer();
                    System.out.println("Filter producer command finished!");
                }
                if(command.equals("Filter disponibility")){
                    employeeCommands.filterDisponibility();
                    System.out.println("Filter disponibility command finished!");
                }
                if(command.equals("Filter price")){
                    employeeCommands.filterPrice();
                    System.out.println("Filter price command finished!");
                }
                if(command.equals("Generate")){
                    employeeCommands.generateFiles();
                    System.out.println("Generate files command finished!");
                }
                if(command.equals("Generate1")){
                    managerCommands.generateFiles1();
                    System.out.println("Generate files command finished!");
                }
                if(command.equals("Search by name manager")){
                    managerCommands.searchByName();
                    System.out.println("Search by name command finished!");
                }
                if(command.equals("Filter After")){
                    managerCommands.filterAfter();
                    System.out.println("Filter after command finished!");
                }
                if(command.equals("Filter by name and price manager")){
                    managerCommands.filterByNameAndPrice();
                    System.out.println("Filter by name and price command finished!");
                }
                if(command.equals("Generate charts")){
                    managerCommands.generateCharts();
                    System.out.println("Filter by name and price command finished!");
                }
                if(command.equals("View all users")){
                    adminCommands.viewAllUsers();
                    System.out.println("View all users command finished!");
                }
                if (command.equals("Filter Admin")){
                    adminCommands.filter();
                    System.out.println("Filter admin command finished!");
                }
                if(command.equals("Delete user")){
                    adminCommands.deleteUser();
                    System.out.println("Delete user command finished!");
                }
                if(command.equals("Insert user")){
                    adminCommands.insertUser();
                    System.out.println("Insert user command finished!");
                }
                if(command.equals("Update user")){
                    adminCommands.updateUser();
                    System.out.println("Update user command finished!");
                }
                if(command.equals("Select user")){
                    adminCommands.selectUser();
                    System.out.println("Select user command finished!");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
