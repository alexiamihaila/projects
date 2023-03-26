package Presenter;

import Model.Percistency.ManagerPersistency;
import View.IManagerView;

import javax.swing.*;
import java.sql.SQLException;

public class ManagerPresenter {
    IManagerView iManagerView;
    ManagerPersistency managerPersistency;

    public ManagerPresenter(IManagerView iManagerView){
        this.iManagerView = iManagerView;
        try {
            this.managerPersistency = new ManagerPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchByName(){

        String s = "";
        String name = iManagerView.getSearchByNameTextField().getText();
        if(name.equals("")){
            iManagerView.searchNameError();
        }else{
        try {
            s = managerPersistency.searchPerfumeByName(name);
            iManagerView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

    }

    public void filterPerfumeByProducer(){
        String s = "";
        try {
            s = managerPersistency.filterByProducer();
            iManagerView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void filterByDisponibility(){
        String s = "";
        String id = iManagerView.getStoreIdTf().getText();

        if(id.equals(""))
            iManagerView.getErrorMessage();
        else{
            try {
                s = managerPersistency.filterByStock(id);
                iManagerView.getTextArea().setText(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void filterByPrice(){
        String s = "";
        try {
           s =  managerPersistency.filterByPrice();
            iManagerView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void filterByPrice1(){
        String s = "";
        String id = iManagerView.getStoreIdTf().getText();
        if(id.equals(""))
            iManagerView.getErrorMessage();
        else{
            try {
                s = managerPersistency.filterByPrice(id);
                iManagerView.getTextArea().setText(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void filterByName(){

        String s = "";
        String id = iManagerView.getStoreIdTf().getText();
        if(id.equals(""))
            iManagerView.getErrorMessage();
        else{
            try {
                s = managerPersistency.filterByName(id);
                iManagerView.getTextArea().setText(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
