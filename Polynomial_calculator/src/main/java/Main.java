
import javax.swing.JPanel;

public class Main extends JPanel {
    public static void main(String[] args) {
        View v = new View();

        Polynomial m = new Polynomial();

        Controller controller = new Controller(m, v);

    }
}





