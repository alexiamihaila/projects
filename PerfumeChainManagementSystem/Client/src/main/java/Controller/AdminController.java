package Controller;

import Connection.ProxyServer;
import Connection.ServerConnection;
import Helper.SendSMS;
import View.AdminView;

import java.util.Locale;

public class AdminController {

    private AdminView adminView;
    private ServerConnection proxyServer;
    private String language;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
        proxyServer = ProxyServer.getInstance();

        initializeActions();
    }

    private void initializeActions() {

        this.adminView.getBtnFR().addActionListener(e -> franceza());
        this.adminView.getBtnEn().addActionListener(e -> engleza());
        this.adminView.getBtnRo().addActionListener(e -> romana());

        this.adminView.getBtnViewAllUsers().addActionListener(e -> viewAllUsers());
        this.adminView.getBtnDelete().addActionListener(e -> deleteUser());
        this.adminView.getBtnInsert().addActionListener(e -> insertUser());
        this.adminView.getBtnUpdate().addActionListener(e -> updateUser());
        this.adminView.getBtnSelect().addActionListener(e -> selectUser());
        this.adminView.getBtnFilter().addActionListener(e -> filter());

    }

    private void engleza() {
        Locale locale = new Locale("en", "US");
        language = "English";
        adminView.onLocaleChange(locale);
    }

    private void franceza() {
        Locale locale = new Locale("fr", "FR");
        language = "French";
        adminView.onLocaleChange(locale);
    }

    private void romana() {
        Locale locale = new Locale("ro", "RO");
        language = "Romanian";
        adminView.onLocaleChange(locale);
    }

    public void viewAllUsers() {
        proxyServer.sendObject("View all users");
        String s = "";
        s = (String) proxyServer.receiveData();
        adminView.setTextArea(s);
    }

    public void filter() {
        proxyServer.sendObject("Filter Admin");
        String s = "";
        String function = adminView.getSelectedItem();
        proxyServer.sendObject(function);

        s = (String) proxyServer.receiveData();
        adminView.setTextArea(s);

    }

    public void insertUser() {
        proxyServer.sendObject("Insert user");

        int i;
        String username = adminView.getUsernameTextField();
        String password = adminView.getPasswordTextField();
        String userType = adminView.getFunctionTextField();

        proxyServer.sendObject(username);
        proxyServer.sendObject(password);
        proxyServer.sendObject(userType);

        if (username.equals("") || password.equals("") || userType.equals("")) {
            String message = (String) proxyServer.receiveData();
            adminView.showMessage(message);
        } else {
            String managerId = adminView.getManagerIdTextField();
            String storeId = adminView.getStoreIdTextField();

            proxyServer.sendObject(managerId);
            proxyServer.sendObject(storeId);

            i = (int) proxyServer.receiveData();

            if (i == 1) {
                adminView.showMessage("User has been inserted into the table!");
            } else if (i == 2) {
                adminView.showMessage("Username already exists!");
            }
        }
        SendSMS.sendMessage("User: " + username + " was created");
    }

    public void deleteUser(){
        proxyServer.sendObject("Delete user");
        String username = adminView.getUsernameTextField();
        proxyServer.sendObject(username);

        int i;
        if(username.equals("")){
            adminView.showMessage("Username field is empty, please fill it!");
        }else {

            i = (int)proxyServer.receiveData();
            if (i == 1)
                adminView.showMessage("User has been deleted from database!");
            else if (i == 0)
                adminView.showMessage("User does not exist in the database!");

        }
        SendSMS.sendMessage("User: " + username + " was deleted");
    }

    public void updateUser(){
        proxyServer.sendObject("Update user");
        String s = "";
        String username = adminView.getUsernameTextField();
        String password = adminView.getPasswordTextField();
        String userType = adminView.getFunctionTextField();
        String managerId = adminView.getManagerIdTextField();
        String storeId = adminView.getStoreIdTextField();

        proxyServer.sendObject(username);
        proxyServer.sendObject(password);
        proxyServer.sendObject(userType);
        proxyServer.sendObject(managerId);
        proxyServer.sendObject(storeId);

        s = (String) proxyServer.receiveData();
        adminView.showMessage(s);

        SendSMS.sendMessage("User: " + username + " was updated");
    }

    public void selectUser(){
        proxyServer.sendObject("Select user");
        String s = "";
        String username = adminView.getUsernameTextField();
        proxyServer.sendObject(username);

        if(username.equals("")){
            adminView.showMessage("Please fill name text field!");
        }else{
            s = (String) proxyServer.receiveData();

            if(s.equals("")){
                adminView.showMessage("User does not exist in database!");
            }
            else{
                adminView.setTextArea(s);
            }

        }

    }
}
