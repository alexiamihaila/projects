package presentation;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {

    private JTextField textField_7;
    private JTextField textField_1;
    private JTextField textField;

    public OrderView(){
        setBounds(100,100,1050,600);
        getContentPane().setBackground(new Color(204, 204, 255));
        getContentPane().setLayout(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        JLabel lblNewLabel = new JLabel("ORDER");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 38));
        lblNewLabel.setBounds(410, 23, 157, 39);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("CLIENTS");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel_1.setBounds(109, 140, 113, 31);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("PRODUCTS");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel_2.setBounds(370, 140, 140, 31);
        getContentPane().add(lblNewLabel_2);

        textField_7 = new JTextField();
        textField_7.setBounds(901, 233, 96, 39);
        getContentPane().add(textField_7);
        textField_7.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Product Quantity");
        lblNewLabel_3.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
        lblNewLabel_3.setBounds(881, 182, 145, 40);
        getContentPane().add(lblNewLabel_3);

        JButton btnNewButton_1 = new JButton("Place order\r\n");
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnNewButton_1.setBounds(869, 311, 157, 54);
        getContentPane().add(btnNewButton_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(323, 162, -270, 300);
        getContentPane().add(scrollPane_1);

        JTextPane textPane = new JTextPane();
        textPane.setBounds(49, 182, 234, 310);
        getContentPane().add(textPane);

        JTextPane textPane_1 = new JTextPane();
        textPane_1.setBounds(323, 182, 234, 310);
        getContentPane().add(textPane_1);

        textField = new JTextField();
        textField.setBounds(711, 242, 96, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(711, 298, 96, 20);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Client id");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_4.setBounds(603, 239, 81, 26);
        getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_4_1 = new JLabel("Product id");
        lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_4_1.setBounds(603, 295, 81, 26);
        getContentPane().add(lblNewLabel_4_1);



    }
}
