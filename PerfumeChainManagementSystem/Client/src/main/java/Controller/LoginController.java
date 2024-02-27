package Controller;

import Connection.ProxyServer;
import Connection.ServerConnection;
import View.*;

import java.util.Locale;

public class LoginController {

    private LoginView loginView;
    private String language = "English";
    private ServerConnection proxyServer;

    public LoginController(LoginView loginView){
        this.loginView = loginView;

        this.proxyServer = ProxyServer.getInstance();
        this.loginView.getBtnLogIn().addActionListener(e -> login());
        this.loginView.getBtnEN().addActionListener(e -> engleza());
        this.loginView.getBtnFR().addActionListener(e -> franceza());
        this.loginView.getBtnRO().addActionListener(e -> romana());

    }



    private void engleza(){
        Locale locale = new Locale("en", "US");
        loginView.onLocaleChange(locale);
        language = "English";

    }
    private void franceza(){
        Locale locale = new Locale("fr", "FR");
        loginView.onLocaleChange(locale);
        language = "French";

    }
    private void romana(){
        Locale locale = new Locale("ro", "RO");
        loginView.onLocaleChange(locale);
        language = "Romanian";

    }

    public void login(){
        String username = loginView.getUsernameTextField();
        String password = loginView.getPasswordTextField();

        proxyServer.sendObject("LOGIN");

        proxyServer.sendObject(username);
        proxyServer.sendObject(password);

        loginView.setLanguage(language);

        if(username.equals("") || password.equals("")){
            String message = (String) proxyServer.receiveData();
            loginView.showMessage(message);
        }
        else {

            String passw =(String) proxyServer.receiveData();

            if(passw.equals("null")){
                String messag = (String)proxyServer.receiveData();
                loginView.showMessage(messag);
            }
            else{

                if(passw.equals(password)){

                    int i = (int) proxyServer.receiveData();
                    int id;


                    FactoryView factoryView = new FactoryView(language);
                    if(i == 1){
                        id =(int) proxyServer.receiveData();
                        factoryView.setId(id);
                        ViewType employeeEn = factoryView.getView("EMPLOYEE");

                    }
                    else if (i == 2){
                        ViewType manager = factoryView.getView("MANAGER");
                    }
                    else if(i == 3){
                        ViewType admin = factoryView.getView("ADMIN");
                    }
                }
                else{
                    String messa = (String)proxyServer.receiveData();
                    loginView.showMessage(messa);
                }
            }
        }

    }

}
