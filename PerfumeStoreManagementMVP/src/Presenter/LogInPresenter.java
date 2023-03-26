package Presenter;

import Model.Percistency.LogInPersistency;
import Model.Percistency.UserPersistency;
import View.AdminPage;
import View.EmployeePage;
import View.ILoginView;
import View.ManagerPage;

import java.sql.SQLException;

public class LogInPresenter {
    private ILoginView loginView;
    private UserPersistency userPersistency;
    private LogInPersistency logInPersistency;

    public LogInPresenter(ILoginView loginView)   {
        this.loginView = loginView;
        this.userPersistency = new UserPersistency();
        this.logInPersistency = new LogInPersistency();
    }

    public void logIn(){
        EmployeePage emplyeeView = new EmployeePage();
        AdminPage adminView = new AdminPage();
        ManagerPage managerView = new ManagerPage();

        String username = loginView.getUsernameTextField().getText();
        String password = loginView.getPasswordTextField().getText();
        logInPersistency.logIn(username, password, emplyeeView, adminView, managerView);
    }

}
