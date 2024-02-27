import Controller.LoginController;
import View.LoginView;

public class Main {

    public static void main(String[] args) {

        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);

    }
}
