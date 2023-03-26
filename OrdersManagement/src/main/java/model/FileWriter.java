package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {

    public void writeInFile(String s) throws IOException {
        File file = new File("Bill.txt");
        BufferedWriter output = null;
        try {
            java.io.FileWriter write = new java.io.FileWriter("C:\\Users\\alexi\\Documents\\Mihaila_Alexia_30229_tema_3\\Bill.txt", true);
            output = new BufferedWriter(write);
            output.append(s);
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                output.close();
            }
        }
    }

}
