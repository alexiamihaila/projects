package Presenter;

import Model.Percistency.AdminPersistency;
import View.AdminPage;
import View.IAdminView;

import java.sql.SQLException;

public class AdminPresenter {
    private IAdminView iAdminView;
    private AdminPersistency adminPersistency;

    public AdminPresenter(IAdminView iAdminView){
        this.iAdminView = iAdminView;
        try {
            this.adminPersistency = new AdminPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewAllUsers(){
        String s = "";
        iAdminView.getTextArea().setText("");
        try {
            s = adminPersistency.selectAllUsers();
            iAdminView.getTextArea().append(s);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectUser(){
        String s = "";
        String username = iAdminView.getTextField().getText();

        iAdminView.getTextArea().setText("");
        if(username.equals("")){
            iAdminView.selectErrorMEssage();
        }else{
        try {
            s = adminPersistency.selectUser(username);
            iAdminView.getTextArea().append(s);

        } catch (SQLException e) {
            e.printStackTrace();
        }}

    }
    public void insertUser(){

        String username = iAdminView.getTextField().getText();
        String password = iAdminView.getNameTextField().getText();
        String function = iAdminView.getProducerTextField().getText();
        String managerid = iAdminView.getPriceTextField().getText();
        String storeid = iAdminView.getStockTextField().getText();

        if(username.equals("") || password.equals("") || function.equals(""))
        {
            iAdminView.insertErrorMessage();
        }else{
        try {
            adminPersistency.insertUser(username, password, function, managerid, storeid);
        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }

    public void deleteUser(){
        String username = iAdminView.getTextField().getText();
        if(username.equals("")){
            iAdminView.deleteErrorMEssage();
        }else{
        try {
            adminPersistency.deleteUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }

    public void updateUser(){
        String username = iAdminView.getTextField().getText();
        String password = iAdminView.getNameTextField().getText();
        String function = iAdminView.getProducerTextField().getText();
        String managerid = iAdminView.getPriceTextField().getText();
        String storeid = iAdminView.getStockTextField().getText();

        if(username.equals("") || password.equals("") || function.equals("") || managerid.equals("") || storeid.equals(""))
        {
            iAdminView.updateErrorMEssage();
        }else{
        try {
            adminPersistency.updateUser(username, password, function,managerid, storeid );
        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }
}
