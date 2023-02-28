import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;


public class View extends JFrame {

    private JTextField timeLimitTF;
    private JTextField maxProcessingTimeTF;
    private JTextField minProcessingTimeTF;
    private JTextField maxArrivalTimeTF;
    private JTextField minArrivalTimeTF;
    private JTextField numberOfQueuesTF;
    private JTextField numberOfCLientsTF;
    private JTextArea textArea;
    private JPanel panel2;
    private JTextPane pane;


    private JLabel timeLimitLABEL;
    private JLabel maxProcessingTimeLABEL;
    private JLabel minProcessingTimeLABEL;
    private JLabel maxArrivalTimeLABEL;
    private JLabel minArrivalTimeLABEL;
    private JLabel numberOfQueuesLABEL;
    private JLabel numberOfCLientsLABEL;

    private JButton buttonRun;

    private JFrame frame;
    private JPanel panel1;


    public View(){

        textArea = new JTextArea();
        frame = new JFrame();
        frame.setTitle("Queue management");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel1 = new JPanel();
        panel2 = new JPanel();

        timeLimitLABEL = new JLabel("Time limit");
        timeLimitTF = new JTextField();
        maxProcessingTimeLABEL = new JLabel("Maximum processing time");
        maxProcessingTimeTF = new JTextField();
        minProcessingTimeLABEL = new JLabel("Minimum processing time");
        minProcessingTimeTF = new JTextField();
        maxArrivalTimeLABEL = new JLabel("Maximum arrival time");
        maxArrivalTimeTF = new JTextField();
        minArrivalTimeLABEL = new JLabel("Minimum arrival time");
        minArrivalTimeTF = new JTextField();
        numberOfQueuesLABEL = new JLabel("Number of queues");
        numberOfQueuesTF = new JTextField();
        numberOfCLientsLABEL = new JLabel("Number of clients");
        numberOfCLientsTF = new JTextField();

        buttonRun = new JButton("Run Simulation");

        panel2.add(numberOfQueuesLABEL);
        panel2.add(numberOfQueuesTF);
        panel2.add(numberOfCLientsLABEL);
        panel2.add(numberOfCLientsTF);
        panel2.add(timeLimitLABEL);
        panel2.add(timeLimitTF);
        panel2.add(maxProcessingTimeLABEL);
        panel2.add(maxProcessingTimeTF);
        panel2.add(minProcessingTimeLABEL);
        panel2.add(minProcessingTimeTF);
        panel2.add(maxArrivalTimeLABEL);
        panel2.add(maxArrivalTimeTF);
        panel2.add(minArrivalTimeLABEL);
        panel2.add(minArrivalTimeTF);
        panel2.setLayout(new GridLayout(8, 2));

        panel2.setSize(250,250);
        frame.add(panel2);

        panel2.add(buttonRun);
        panel1.add(textArea);
        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);


        frame.add(panel1);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }

    public void buttonRunListener(ActionListener e){
        buttonRun.addActionListener(e);
    }

    public String getMaxArrivalTimeTF() {
        return maxArrivalTimeTF.getText();
    }

    public String getMinArrivalTimeTF(){
        return minArrivalTimeTF.getText();
    }

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this.frame, errMessage);
    }

    public String getMinProcessingTimeTF(){
        return minProcessingTimeTF.getText();
    }

    public String getMaxProcessingTimeTF(){
        return maxProcessingTimeTF.getText();
    }

    public String getNumberOfClientsTF(){
        return numberOfCLientsTF.getText();
    }

    public String getNumberOfQueuesTF(){
        return numberOfQueuesTF.getText();
    }

    public String getTimeLimitTF(){
        return timeLimitTF.getText();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    void setPane(String s){
        pane.setText(s);
    }
}
