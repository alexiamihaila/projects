package Controller;

import Connection.ProxyServer;
import Connection.ServerConnection;
import View.EmployeeView;

import java.io.*;
import java.util.Locale;

public class EmployeeController {
    private EmployeeView employeeView;
    private ServerConnection proxyServer;
    private String language;

    public EmployeeController(EmployeeView employeeView){
        this.employeeView = employeeView;
        this.proxyServer = ProxyServer.getInstance();

        initializeActions();
    }

    private void initializeActions(){

        this.employeeView.getBtnSearchByName().addActionListener(e -> searchByName());
        this.employeeView.getBtnInsert().addActionListener(e -> addPerfume());
        this.employeeView.getBtnViewAllPerfumes().addActionListener(e -> viewAllPerfumes());
        this.employeeView.getBtnViewPerfumesFrom().addActionListener(e -> viewAllFromChain());
        this.employeeView.getBtnDelete().addActionListener(e -> deletePerfume());
        this.employeeView.getBtnUpdate().addActionListener(e-> updatePerfume());
        this.employeeView.getBtnSelect().addActionListener(e -> selectPerfume());
        this.employeeView.getBtnFilterByNameAndPrice().addActionListener(e -> filterByNameAndPrice());
        this.employeeView.getBtnPrice().addActionListener(e -> filterPrice());
        this.employeeView.getBtnProducer().addActionListener(e-> filterProducer());
        this.employeeView.getBtnDisponibility().addActionListener((e -> filterDisponibility()));
        this.employeeView.getBtnEN().addActionListener(e -> engleza());
        this.employeeView.getBtnFr().addActionListener(e -> franceza());
        this.employeeView.getBtnRo().addActionListener(e -> romana());
        this.employeeView.getGenerate().addActionListener(e -> generateFile());

    }

    private void engleza(){
        Locale locale = new Locale("en", "US");
        language = "English";
        employeeView.onLocaleChange(locale);

    }
    private void franceza(){
        Locale locale = new Locale("fr", "FR");
        language = "French";
        employeeView.onLocaleChange(locale);

    }

    private void romana(){
        Locale locale = new Locale("ro", "RO");
        language = "Romanian";
        employeeView.onLocaleChange(locale);

    }

    public void searchByName(){
        proxyServer.sendObject("Search perfume by name");

        String name = employeeView.getSearchByNameTF();
        String storeId = employeeView.getTextField();
        String s = "";

        proxyServer.sendObject(name);
        proxyServer.sendObject(storeId);

        if(name.equals(""))
            employeeView.showMessage((String) proxyServer.receiveData());
        else{
           String ss = (String) proxyServer.receiveData();

            if(ss == ""){
                String message = (String) proxyServer.receiveData();
                employeeView.showMessage(message);
            }

            else
                employeeView.setTextArea(ss);
        }


    }

    public void viewAllPerfumes(){
        proxyServer.sendObject("View all perfumes");
        String storeID = employeeView.getTextField();
        proxyServer.sendObject(storeID);

        String s;
        s = (String) proxyServer.receiveData();

        employeeView.setTextArea(s);
    }

    public void viewAllFromChain(){
        proxyServer.sendObject("View all perfumes from chain");
        String s = "";
        s = (String)proxyServer.receiveData();
        employeeView.setTextArea(s);
    }

    public void addPerfume(){
        proxyServer.sendObject("Add perfume");
        String storeId = employeeView.getTextField();
        String name = employeeView.getNameTextField();
        String quantity = employeeView.getStockTextField();
        String producer = employeeView.getProducerTextField();
        String s = "";
        String s1 = "";
        String s3  = "";

        proxyServer.sendObject(storeId);
        proxyServer.sendObject(name);
        proxyServer.sendObject(quantity);
        proxyServer.sendObject(producer);

        if(name.equals("")){
            String message3 = (String) proxyServer.receiveData();
            employeeView.showMessage(message3);
        }else if (quantity.equals("")){
            String message4 = (String) proxyServer.receiveData();
            employeeView.showMessage(message4);
        }
        else if(producer.equals("")){
            String message5 = (String) proxyServer.receiveData();
            employeeView.showMessage(message5);
        }
        else{
            s = (String) proxyServer.receiveData();

            if(!s.equals("")){
                String message1 = (String) proxyServer.receiveData();
                employeeView.showMessage(message1);
            }else{

                s3 = (String) proxyServer.receiveData();
                employeeView.showMessage(s3);

            }

        }
    }

    public void deletePerfume(){
        proxyServer.sendObject("Delete perfume");
        String storeId = employeeView.getTextField();
        String perfumeName = employeeView.getNameTextField();

        proxyServer.sendObject(storeId);
        proxyServer.sendObject(perfumeName);

        if(perfumeName.equals("")){
            employeeView.showMessage("Please insert the name of the perfume you want to delete!");
        }else{
            String s = (String) proxyServer.receiveData();
            employeeView.showMessage(s);
        }

    }

    public void selectPerfume(){
        proxyServer.sendObject("Select perfume");
        String name = employeeView.getNameTextField();
        String storeId = employeeView.getTextField();

        proxyServer.sendObject(name);
        proxyServer.sendObject(storeId);

        String s = "";

        if(name.equals(""))
            employeeView.showMessage("Please fill name text field!");
        else {
            s = (String) proxyServer.receiveData();

            if (s == "")
                employeeView.showMessage("Perfume is not in this store!");
            else{
                employeeView.setTextArea(s);
            }
        }


    }

    public void updatePerfume(){
        proxyServer.sendObject("Update perfume");
        String name = employeeView.getNameTextField();
        String storeId = employeeView.getTextField();
        String quantity = employeeView.getStockTextField();

        proxyServer.sendObject(name);
        proxyServer.sendObject(storeId);
        proxyServer.sendObject(quantity);

        String s = "";
        String name1 = "";

        if (name.equals("")) {
            employeeView.showMessage("Please insert name of the perfume you want to update!");
        }else if (quantity.equals("")) {
            employeeView.showMessage("Please write the quantity you want to update!");
        }
        else{
            name1 = (String) proxyServer.receiveData();

            if (name1.equals("")) {
                employeeView.showMessage("This perfume is not in this store!");
            }
            else{
                s = (String) proxyServer.receiveData();
                employeeView.showMessage(s);
            }
        }

    }

    public void filterByNameAndPrice(){
        proxyServer.sendObject("Filter By Name And Price");
        String id = employeeView.getTextField();
        String s = "";

        proxyServer.sendObject(id);

        s = (String) proxyServer.receiveData();
        employeeView.setTextArea(s);
    }

    public void filterPrice(){
        proxyServer.sendObject("Filter price");

        String storeID = employeeView.getTextField();
        String s = "";
        proxyServer.sendObject(storeID);
        s = (String) proxyServer.receiveData();
        employeeView.setTextArea(s);

    }

    public void filterProducer(){
        proxyServer.sendObject("Filter producer");

        String storeID = employeeView.getTextField();
        String s = "";

        proxyServer.sendObject(storeID);

        s = (String) proxyServer.receiveData();

        employeeView.setTextArea(s);
    }

    public void filterDisponibility(){

       proxyServer.sendObject("Filter disponibility");

        String storeID = employeeView.getTextField();
        String s = "";

        proxyServer.sendObject(storeID);

        s = (String) proxyServer.receiveData();
        employeeView.setTextArea(s);
    }

    public void generateFile(){
        proxyServer.sendObject("Generate");
        String id = employeeView.getTextField();

        String fileType = employeeView.getSelectedItem();
        proxyServer.sendObject(fileType);
        proxyServer.sendObject(id);

        if (fileType.equals("CSV")) {

            try {
                File csvFile = new File("perfumesFromStore.csv");
                PrintWriter out = new PrintWriter(csvFile);
                String s = (String) proxyServer.receiveData();
                out.print(s);
                out.close();
                employeeView.showMessage("CSV file has been generated!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("TXT")) {

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("perfumesFromStore.txt"));
                String s = (String) proxyServer.receiveData();
                writer.write(s);
                writer.close();

                employeeView.showMessage("TXT file has been generated!");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("JSON")) {
            try {
                FileWriter file = new FileWriter("perfumesFromStore.json");
                file.write((String) proxyServer.receiveData());
                file.close();
                employeeView.showMessage("JSON file has been generated!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("XML")) {

            String xml = (String) proxyServer.receiveData();
            String filePath = "perfumesFromStore.xml";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(xml);
                System.out.println("XML written to file: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
