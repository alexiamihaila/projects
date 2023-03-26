package presentation;

import javax.swing.*;
import java.awt.*;

public class ProductView extends JFrame {

    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_11;
    private JTextField textField_9;
    private JButton btnNewButton_1;
    private JButton btnNewButton_1_1;
    private JButton btnNewButton_1_2;
    private JButton btnNewButton_1_3;


    public ProductView(){
        setBounds(100,100,1050,500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 204, 153));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("PRODUCT");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblNewLabel.setBounds(410, 23, 157, 39);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Id: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1.setBounds(98, 135, 114, 21);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Name:");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1_1.setBounds(98, 167, 65, 21);
        getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Quantity:");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1_1_1.setBounds(98, 199, 88, 21);
        getContentPane().add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_2 = new JLabel("Calories:");
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1_1_2.setBounds(98, 231, 72, 21);
        getContentPane().add(lblNewLabel_1_1_2);

        textField_7 = new JTextField();
        textField_7.setBounds(209, 138, 130, 20);
        getContentPane().add(textField_7);
        textField_7.setColumns(10);

        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(209, 233, 130, 20);
        getContentPane().add(textField_8);

        textField_9 = new JTextField();
        textField_9.setColumns(10);
        textField_9.setBounds(209, 202, 130, 20);
        getContentPane().add(textField_9);

        textField_11 = new JTextField();
        textField_11.setColumns(10);
        textField_11.setBounds(209, 170, 130, 20);
        getContentPane().add(textField_11);

        btnNewButton_1 = new JButton("Add Product");
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton_1.setBounds(63, 333, 153, 44);
        getContentPane().add(btnNewButton_1);

        btnNewButton_1_1 = new JButton("Delete Product");
        btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_1_1.setBounds(238, 333, 153, 44);
        getContentPane().add(btnNewButton_1_1);

        btnNewButton_1_2 = new JButton("Modify Product");
        btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_1_2.setBounds(63, 401, 153, 44);
        getContentPane().add(btnNewButton_1_2);

        btnNewButton_1_3 = new JButton("View All Products");
        btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton_1_3.setBounds(238, 401, 153, 44);
        getContentPane().add(btnNewButton_1_3);

        JTextPane textPane = new JTextPane();
        textPane.setBounds(492, 135, 447, 310);
        getContentPane().add(textPane);


    }
}
