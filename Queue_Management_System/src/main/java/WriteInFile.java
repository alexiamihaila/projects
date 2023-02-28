import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {

    public void writeInFile(String s) throws IOException {
        File file = new File("out.txt");
        BufferedWriter output = null;
        try {
            FileWriter write = new FileWriter("C:\\Users\\alexi\\Documents\\Mihaila_Alexia_30229_tema_2\\out.txt", true);
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


