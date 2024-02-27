package BusinessLogic;

import Model.Percistency.*;
import Model.Perfume;
import Model.PerfumeList;
import com.thoughtworks.xstream.XStream;
import org.jfree.data.json.impl.JSONArray;
import org.jfree.data.json.impl.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private PerfumeFromStorePercistency percistency;

    public EmployeeController(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;

        this.percistency = new PerfumeFromStorePercistency();
    }

    public void searchByName() {

        try {
            String name = (String) objectInputStream.readObject();
            String storeId = (String) objectInputStream.readObject();

            if (name.equals("")) {
                String message = "Please fill search text field!";
                objectOutputStream.writeObject(message);
            } else {

                String s = percistency.selectQuery(storeId, name);
                objectOutputStream.writeObject(s);

                if (s.equals("")) {
                    String message = "Perfume is not in this store!";
                    objectOutputStream.writeObject(message);
                } else {
                    System.out.println("Searched perfume was found!");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewAllPerfumes() {
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();
        String storeID;
        try {
            storeID = (String) objectInputStream.readObject();
            String s = "";
            try {
                s = percistency.selectAllPerfumes(storeID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            objectOutputStream.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void viewAllFromChain() {
        String s = "";
        PerfumePersistency persistency = new PerfumePersistency();
        try {
            s = persistency.selectAllPerfumes();
            objectOutputStream.writeObject(s);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void addPerfume() {
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();
        try {
            String storeId = (String) objectInputStream.readObject();
            String name = (String) objectInputStream.readObject();
            String quantity = (String) objectInputStream.readObject();
            String producer = (String) objectInputStream.readObject();
            String s = "";
            String s1 = "";
            String s3 = "";

            if (name.equals("")) {
                String message2 = "Name text field is empty!\nPlease fill it!";
                objectOutputStream.writeObject(message2);
            } else if (quantity.equals("")) {
                String message3 = "Quantity text field is empty!\nPlease fill it!";
                objectOutputStream.writeObject(message3);
            } else if (producer.equals("")) {
                String message4 = "Producer text field is empty!\nPlease fill it!";
                objectOutputStream.writeObject(message4);
            } else {

                try {
                    s = percistency.selectPerfumeByName(storeId, name);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                objectOutputStream.writeObject(s);
                if (!s.equals("")) {
                    String message1 = "This perfume already exists in store";
                    objectOutputStream.writeObject(message1);
                } else {
                    try {
                        s3 = percistency.insertQuery(storeId, name, producer, quantity);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    objectOutputStream.writeObject(s3);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePerfume() {
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();
        try {
            String storeId = (String) objectInputStream.readObject();
            String perfumeName = (String) objectInputStream.readObject();

            try {
                if (!perfumeName.equals("")) {
                    String s = percistency.deleteQuery(storeId, perfumeName);
                    objectOutputStream.writeObject(s);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void selectPerfume(){
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

        try{
        String name = (String) objectInputStream.readObject();
        String storeId = (String) objectInputStream.readObject();
        String s = "";

        if(!name.equals("")) {
            try {
                s = percistency.selectQuery(storeId, name);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (s != ""){
                objectOutputStream.writeObject(s);
            }
        }} catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void updatePerfume(){
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

        try {
            String name = (String) objectInputStream.readObject();
            String storeId = (String) objectInputStream.readObject();
            String quantity = (String) objectInputStream.readObject();

            String s = "";
            String name1 = "";

            if (!name.equals("") && !quantity.equals("")) {
                try {
                    name1 = percistency.selectPerfumeByName(storeId, name);
                    objectOutputStream.writeObject(name1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (!name1.equals("")){
                    try {
                        s = percistency.updateQuery(storeId, name, quantity);
                        objectOutputStream.writeObject(s);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void filterByNameAndPrice(){
        try{
            String id = (String) objectInputStream.readObject();
            String s = "";

            PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();
            try {
                s = percistency.filterByNameAndPrice(id);

               objectOutputStream.writeObject(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void filterPrice(){
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

        try {
            String storeID = (String) objectInputStream.readObject();
            String s = "";

            try {
                s = percistency.filterByPrice(storeID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            objectOutputStream.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void filterProducer(){
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

        try{
        String storeID = (String) objectInputStream.readObject();
        String s = "";

        s = percistency.filterByProducer(storeID);

        objectOutputStream.writeObject(s);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void filterDisponibility(){
        PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();

        try{
        String storeID = (String) objectInputStream.readObject();
        String s = "";

        try {
            s = percistency.filterByStock(storeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        objectOutputStream.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateFiles() {
        try {
            String fileType = (String) objectInputStream.readObject();
            String id = (String)objectInputStream.readObject();
            PerfumeFromStorePercistency percistency = new PerfumeFromStorePercistency();
            List<Perfume> perfumeList = new ArrayList<>();
            perfumeList = percistency.generatePerfumeList(id);

            if (fileType.equals("CSV")) {
                String s = "";

                for (Perfume perfume : perfumeList) {
                    s += ("Id: " + perfume.getPerfumeId() + ", Name: " + perfume.getName() + ", Producer: " + perfume.getProducer() + ", Price: " + perfume.getPrice() + "\n");
                }
                objectOutputStream.writeObject(s);
            } else if (fileType.equals("TXT")) {
                String s = "";
                List<Perfume> perfumeList1 = new ArrayList<>();
                for (Perfume perfume : perfumeList) {
                    Perfume p1 = perfume.clone();
                    perfumeList1.add(p1);
                }

                for (Perfume perfume : perfumeList1) {
                    s += "Id: " + perfume.getPerfumeId() + ", Name: " + perfume.getName() + ", Producer: " + perfume.getProducer() + ", Price: " + perfume.getPrice() + " \n";
                }
                objectOutputStream.writeObject(s);

            } else if (fileType.equals("JSON")) {

                JSONArray jsonArray = new JSONArray();

                for (Perfume perfume : perfumeList) {
                    JSONObject obj = new JSONObject();
                    obj.put("Perfume id: ", perfume.getPerfumeId());
                    obj.put("Name: ", perfume.getName());
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
