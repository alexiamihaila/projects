package Controller;

import Connection.ProxyServer;
import Connection.ServerConnection;
import View.ManagerView;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.io.*;
import java.util.Locale;

public class ManagerController {

    private ManagerView managerView;
    private ServerConnection proxyServer;
    private String language;

    public ManagerController(ManagerView managerView) {
        this.managerView = managerView;
        proxyServer = ProxyServer.getInstance();

        initializeActions();
    }

    private void initializeActions() {
        this.managerView.getBtnFR().addActionListener(e -> franceza());
        this.managerView.getBtnEn().addActionListener(e -> engleza());
        this.managerView.getBtnRo().addActionListener(e -> romana());
        this.managerView.getBtnGenerate().addActionListener(e -> generateFiles());
        this.managerView.getChartButton().addActionListener(e -> getCharts());
        this.managerView.getBtnSearchByName().addActionListener(e -> searchByName());
        this.managerView.getFilterBy().addActionListener(e -> filterAfter());
        this.managerView.getBtnFilter().addActionListener(e -> filterBYNamePrice());
    }

    private void engleza() {
        Locale locale = new Locale("en", "US");
        managerView.onLocaleChange(locale);
        language = "English";
    }

    private void franceza() {
        Locale locale = new Locale("fr", "FR");
        managerView.onLocaleChange(locale);
        language = "French";
    }

    private void romana() {
        Locale locale = new Locale("ro", "RO");
        managerView.onLocaleChange(locale);
        language = "Romanian";
    }

    public void generateFiles() {
        proxyServer.sendObject("Generate1");

        String fileType = managerView.getSelectedItem();

        proxyServer.sendObject(fileType);

        if (fileType.equals("CSV")) {

            try {
                File csvFile = new File("perfumes.csv");
                PrintWriter out = new PrintWriter(csvFile);
                String s = (String) proxyServer.receiveData();
                out.print(s);
                out.close();
                managerView.showMessage("CSV file has been generated!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("TXT")) {

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("perfumes.txt"));
                String s = (String) proxyServer.receiveData();
                writer.write(s);
                writer.close();

                managerView.showMessage("TXT file has been generated!");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("JSON")) {
            try {
                FileWriter file = new FileWriter("perfumes.json");
                file.write((String) proxyServer.receiveData());
                file.close();
                managerView.showMessage("JSON file has been generated!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (fileType.equals("XML")) {

            String xml = (String) proxyServer.receiveData();
            String filePath = "perfumes.xml";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(xml);
                System.out.println("XML written to file: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void searchByName() {
        proxyServer.sendObject("Search by name manager");

        String name = managerView.getSearchByNameTextField();
        proxyServer.sendObject(name);

        String s = "";

        if (name.equals("")) {
            String message = (String) proxyServer.receiveData();
            ManagerView.showMessage(message);
        } else {

            try {
                s = (String) proxyServer.receiveData();

                if (s.equals("")) {
                    String message1 = (String) proxyServer.receiveData();
                    managerView.showMessage(message1);
                } else {
                    managerView.setTextArea(s);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void filterAfter() {
        proxyServer.sendObject("Filter After");
        String filterBy = managerView.getSelectedItem1();
        String s = "";

        proxyServer.sendObject(filterBy);

        s = (String) proxyServer.receiveData();
        managerView.setTextArea(s);

    }

    public void filterBYNamePrice(){
    proxyServer.sendObject("Filter by name and price manager");

        String id = managerView.getStoreIdTf();
        proxyServer.sendObject(id);

        String s = "";
        if (id.equals("")) {
            String message = (String) proxyServer.receiveData();
            ManagerView.showMessage(message);
        } else {
            s = (String) proxyServer.receiveData();
            managerView.setTextArea(s);
        }

    }

    public void getCharts(){

        proxyServer.sendObject("Generate charts");

        ChartPanel chartPanel = (ChartPanel) proxyServer.receiveData();
        JFrame frame = new JFrame("Pie Chart");
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setSize(400,500);
        frame.setVisible(true);


        ChartPanel chartPanel1 = (ChartPanel) proxyServer.receiveData();
        JFrame frame1 = new JFrame("Bar Chart ");
        frame1.getContentPane().add(chartPanel1);
        frame1.pack();
        frame1.setSize(400,500);
        frame1.setVisible(true);


        ChartPanel chartPanel2 = (ChartPanel) proxyServer.receiveData();
        JFrame frame2 = new JFrame("Bar Chart ");
        frame2.getContentPane().add(chartPanel2);
        frame2.pack();
        frame2.setSize(400,500);
        frame2.setVisible(true);

    }


}
