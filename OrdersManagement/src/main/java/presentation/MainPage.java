package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{

    private JButton btnNewButton_1;
    private JButton btnNewButton_1_1;
    private JButton btnNewButton_1_2;

    public MainPage() {
        setBounds(100,100,1050,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Orders Management");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 53));
        lblNewLabel.setBounds(225, 38, 545, 70);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Principal Menu");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 39));
        lblNewLabel_1.setBounds(362, 112, 260, 51);
        getContentPane().add(lblNewLabel_1);

        btnNewButton_1 = new JButton("CLIENT OPERATIONS");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBounds(417, 201, 169, 51);
        getContentPane().add(btnNewButton_1);

        btnNewButton_1_1 = new JButton("PRODUCT OPERATIONS");
        btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton_1_1.setBounds(417, 286, 169, 51);
        getContentPane().add(btnNewButton_1_1);

        btnNewButton_1_2 = new JButton("CREATE ORDER");
        btnNewButton_1_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnNewButton_1_2.setBounds(417, 368, 169, 51);
        getContentPane().add(btnNewButton_1_2);
    }


    public void addClientBtnListener(ActionListener e){
        this.btnNewButton_1.addActionListener(e);
    }

    public void addProductBtnListener(ActionListener e){
        this.btnNewButton_1_1.addActionListener(e);
    }

    public void addOrderBtnListener(ActionListener e){
        this.btnNewButton_1_2.addActionListener(e);
    }

}
