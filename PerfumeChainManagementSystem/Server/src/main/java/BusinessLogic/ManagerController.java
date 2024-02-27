package BusinessLogic;

import Model.Percistency.*;
import Model.Perfume;
import Model.PerfumeList;
import com.thoughtworks.xstream.XStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.json.impl.JSONArray;
import org.jfree.data.json.impl.JSONObject;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ManagerPersistency persistency;

    public ManagerController(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        try {
            this.persistency = new ManagerPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void generateFiles1(){

        try {
            String fileType = (String) objectInputStream.readObject();
            ManagerPersistency percistency = new ManagerPersistency();
            List<Perfume> perfumeList = new ArrayList<>();
            perfumeList = percistency.generatePerfumeList();

            if (fileType.equals("CSV")) {
                String s = "";

                for(Perfume perfume: perfumeList){
                   s += ("Id: " + perfume.getPerfumeId() + ", Name: " + perfume.getName() + ", Producer: " + perfume.getProducer() + ", Price: " + perfume.getPrice() + "\n");
                }
                objectOutputStream.writeObject(s);
            } else if (fileType.equals("TXT")) {
                String s = "";
                for(Perfume perfume: perfumeList){
                    s += "Id: " + perfume.getPerfumeId() + ", Name: " + perfume.getName() + ", Producer: " + perfume.getProducer() + ", Price: " + perfume.getPrice() +  " \n";
                }
               objectOutputStream.writeObject(s);

            } else if (fileType.equals("JSON")) {


                JSONArray jsonArray = new JSONArray();

                for(Perfume perfume: perfumeList){
                    JSONObject obj = new JSONObject();
                    obj.put("Perfume id: ",perfume.getPerfumeId());
                    obj.put("Name: ",  perfume.getName());
                    obj.put("Producer: ", perfume.getProducer());
                    obj.put("Price: ", perfume.getPrice());
                    jsonArray.add(obj);
                }

               objectOutputStream.writeObject(jsonArray.toJSONString());
            } else if (fileType.equals("XML")) {

                PerfumeList perfumeListWrapper = new PerfumeList(perfumeList);
                // Create an instance of XStream
                XStream xstream = new XStream();
                // Convert the perfumeListWrapper object to XML
                String xml = xstream.toXML(perfumeListWrapper);

                objectOutputStream.writeObject(xml);
                // Print the generated XML
                System.out.println(xml);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchByName(){
        try {
            String name = (String)objectInputStream.readObject();
            String s = "";
            if (name.equals("")) {
                objectOutputStream.writeObject("Please fill name text field!");
            } else {

                try {
                    ManagerPersistency managerPersistency = new ManagerPersistency();
                    s = managerPersistency.searchPerfumeByName(name);
                    objectOutputStream.writeObject(s);

                    if (s.equals("")) {
                        objectOutputStream.writeObject("Perfume is not in database!");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void filterAfter(){
        try {
            String filterBy = (String) objectInputStream.readObject();
            String s = "";

            ManagerPersistency persistency = new ManagerPersistency();

            if (filterBy.equals("Price")) {
                s = persistency.filterByPrice();
            } else if (filterBy.equals("Disponibility")) {
                s = persistency.filterByStock();
            } else if (filterBy.equals("Producer")) {
                s = persistency.filterByProducer();
            } else if (filterBy.equals("Store")) {
                s = persistency.filterByStore();
            }

            objectOutputStream.writeObject(s);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void filterByNameAndPrice(){

        try {
            String id = (String) objectInputStream.readObject();
            String s = "";
            if (id.equals("")) {
                objectOutputStream.writeObject("Please insert store id!");
            } else {

                PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

                s = percistency.filterByNameAndPrice(id);
                objectOutputStream.writeObject(s);

             }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateCharts(){

        //chart1
        var dataset = new DefaultPieDataset();
        List<Perfume> perfumeList = new ArrayList<>();
        ManagerPersistency persistency = null;
        try {
            persistency = new ManagerPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            perfumeList = persistency.generatePerfumeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Perfume perfume : perfumeList)
            dataset.setValue(perfume.getName(), perfume.getPrice());

        JFreeChart chart = ChartFactory.createPieChart("Price of perfumes", dataset,true,true,false);

        ChartPanel chartPanel = new ChartPanel(chart);

        try{
            objectOutputStream.writeObject(chartPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //chart2

        var dataset1 = new DefaultCategoryDataset();
        List<Perfume> perfumeList1 = new ArrayList<>();
        ManagerPersistency persistency1 = null;
        try {
            persistency1 = new ManagerPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            perfumeList1 = persistency1.generatePerfumeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Perfume perfume : perfumeList)
            dataset1.addValue(perfume.getPrice(),"Price",perfume.getName() );

        JFreeChart chart1 = ChartFactory.createBarChart (
                "Price of perfumes",
                "",
                "Price",                 // legend?
                dataset1,                 // tooltips?
                PlotOrientation.VERTICAL,
                false,true,false// URLs?
        );

        ChartPanel chartPanel1 = new ChartPanel(chart1);
        try{
            objectOutputStream.writeObject(chartPanel1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //chart3

        DefaultPieDataset dataset2=new DefaultPieDataset<>();
        List<Perfume> perfumeList2 = new ArrayList<>();
        ManagerPersistency persistency2 = null;
        try {
            persistency2 = new ManagerPersistency();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            perfumeList2 = persistency2.generatePerfumeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Perfume perfume : perfumeList)
            dataset2.setValue(perfume.getName(),perfume.getPrice());

        JFreeChart chart2 = ChartFactory.createRingChart (
                "Price of perfumes",
                dataset,
                true,
                true,false
        );
        ChartPanel chartPanel2 = new ChartPanel(chart2);

        try{
            objectOutputStream.writeObject(chartPanel2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
