package Presenter;

import Model.Percistency.PerfumeFromStorePercistency;
import View.IEmployeeView;
import View.ILoginView;

import java.sql.SQLException;

public class EmployeePresenter {
    private IEmployeeView iEmployeeView;
    PerfumeFromStorePercistency perfumePersistency;

    public EmployeePresenter(IEmployeeView iEmployeeView){
        this.iEmployeeView = iEmployeeView;
        this.perfumePersistency = new PerfumeFromStorePercistency();
    }


    public void filterBYprice(){
        String s ="";
        String storeId = iEmployeeView.getTextField().getText();
        try {
            s= perfumePersistency.filterByPrice(storeId);
            iEmployeeView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void filterBYProducer(){
        String s = "";
        String storeId = iEmployeeView.getTextField().getText();
        try {
            s = perfumePersistency.filterByProducer(storeId);
            iEmployeeView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void filterByDisponibility(){
        String storeId = iEmployeeView.getTextField().getText();
        String s = "";

        try {
            s = perfumePersistency.filterByStock(storeId);
            iEmployeeView.getTextArea().setText(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectPerfume(){
        String storeId = iEmployeeView.getTextField().getText();
        String perfumeNAme = iEmployeeView.getNameTextField().getText();

        String s = "";
        iEmployeeView.getTextArea().setText("");

        if(perfumeNAme.equals("")){
            iEmployeeView.selectErrorMEssage();
        }else{
        try{
            s = perfumePersistency.selectQuery(storeId, perfumeNAme);
            iEmployeeView.getTextArea().append(s);

        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }

    public void insertPerfume(){

        String storeId = iEmployeeView.getTextField().getText();
        String perfumeNAme = iEmployeeView.getNameTextField().getText();
        String quantity = iEmployeeView.getStockTextField().getText();
        if(storeId.equals("") || perfumeNAme.equals("") || quantity.equals("")){
            iEmployeeView.insertErrorMEssage();
        }else{
        try{
            perfumePersistency.insertQuery(storeId, perfumeNAme, quantity);

        } catch (SQLException e) {
            e.printStackTrace();
        }}

    }

    public void deletePerfume(){
        String storeId = iEmployeeView.getTextField().getText();
        String perfumeNAme = iEmployeeView.getNameTextField().getText();

        if(perfumeNAme.equals("")){
            iEmployeeView.deleteErrorMEssage();
        }else{
        try{
            perfumePersistency.deleteQuery(storeId, perfumeNAme);

        } catch (SQLException e) {
            e.printStackTrace();
        }}
    }

    public void updatePerfume(){

        String storeId = iEmployeeView.getTextField().getText();
        String perfumeNAme = iEmployeeView.getNameTextField().getText();
        String quantity = iEmployeeView.getStockTextField().getText();

        if(storeId.equals("") || perfumeNAme.equals("") || quantity.equals("")){
            iEmployeeView.updateErrorMessage();
        }else{
        try{
            perfumePersistency.updateQuery(storeId, perfumeNAme,quantity);
        }catch (SQLException e){
            e.printStackTrace();
        }}
    }

    public void viewAllPerfumes(){
        String storeId = iEmployeeView.getTextField().getText();

        String s = "";

        iEmployeeView.getTextArea().setText("");
        try {
            s = perfumePersistency.selectAllPerfumes(storeId);
            System.out.println(s);
            iEmployeeView.getTextArea().append(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
