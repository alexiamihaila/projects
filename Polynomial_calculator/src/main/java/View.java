
import java.util.regex.*;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View {

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;

    private JButton btnMul;
    private JButton btnAdd;
    private JButton btnDiv;
    private JButton btnSubtr;
    private JButton btnDerivative;
    private JButton btnIntegration;

    private JLabel labelTitlu;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    private JTextField tfPol1;
    private JTextField tfPol2;
    private JTextField tfPol3;

    private GridLayout layout1;
    private GridLayout layout2;
    private BoxLayout layout3;

    public View() {
        JFrame frame = new JFrame ("Polynomial Calculator");


        panel1 = new JPanel();
        contentPane = new JPanel();
        panel2 = new JPanel();

        labelTitlu = new JLabel("Polynomial Calculator");
        label1 = new JLabel(" First Polynomial");
        label2 = new JLabel(" Second Polynomial");
        label3 = new JLabel(" Result");

        tfPol1 = new JTextField();
        tfPol2 = new JTextField();
        tfPol3 = new JTextField();

        btnMul = new JButton("Multiplicate");
        btnAdd = new JButton("Add");
        btnDiv = new JButton("Divide");
        btnSubtr = new JButton("Subtract");
        btnDerivative = new JButton("Derivate");
        btnIntegration = new JButton("Integration");

        panel1.add(btnAdd);
        panel1.add(btnMul);
        panel1.add(btnDiv);
        panel1.add(btnSubtr);
        panel1.add(btnDerivative);
        panel1.add(btnIntegration);
        layout1 = new GridLayout(3, 2);
        panel1.setLayout(layout1);


        panel2.add(label1);
        panel2.add(tfPol1);
        panel2.add(label2);
        panel2.add(tfPol2);
        panel2.add(label3);
        panel2.add(tfPol3);
        layout2 = new GridLayout(3,2);
        panel2.setLayout(layout2);

        labelTitlu.setAlignmentX(Component.CENTER_ALIGNMENT);
        layout3 = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.add(labelTitlu);
        contentPane.add(panel2);
        contentPane.add(panel1);

        contentPane.setLayout(layout3);

        frame.setSize(500, 500);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }



    public String getTfPol1()
    {
        return this.tfPol1.getText();
    }

    public String getTfPol2()
    {
        return this.tfPol2.getText();
    }


    public void addListener(ActionListener listenForAddButton) {
        btnAdd.addActionListener(listenForAddButton);
    }

    public void subtractListener(ActionListener listenForSubtractButton) {
        btnSubtr.addActionListener(listenForSubtractButton);
    }

    public void multiplicationListener(ActionListener listenForMultiplyButton) {
        btnMul.addActionListener(listenForMultiplyButton);
    }

    public void derivativeListener(ActionListener listenForDerivativeButton) {
        btnDerivative.addActionListener(listenForDerivativeButton);
    }

    public void integrationListener(ActionListener listenForIntegrationButton) {
        btnIntegration.addActionListener(listenForIntegrationButton);
    }

    public void divisionListener(ActionListener listenForDivisionButton) {
        btnDiv.addActionListener(listenForDivisionButton);
    }

    public JTextField getTfRes() {
        return this.tfPol3;
    }


}
