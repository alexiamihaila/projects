package presentation;

import business.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClientView extends JFrame {

    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_11;
    private JTextField textField_9;
    private JButton btnNewButton_1;
    private JButton btnNewButton_1_1;
    private JButton btnNewButton_1_2;
    private JButton btnNewButton_1_3;
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Client> client;
    private GenerateTable generateTable;

    public ClientView(){
        client = new ArrayList<Client>();
        generateTable = new GenerateTable();

        setBounds(100,100,1050,500);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setBackground(new Color(102, 153, 255));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("CLIENT");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblNewLabel.setBounds(445, 23, 130, 39);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Id: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1.setBounds(98, 135, 114, 21);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Name:");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1_1.setBounds(98, 167, 65, 21);
        getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Address:");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_1_1_1.setBounds(98, 199, 88, 21);
        getContentPane().add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_2 = new JLabel("Email:");
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

        btnNewButton_1 = new JButton("Add Client");
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton_1.setBounds(63, 333, 153, 44);
        getContentPane().add(btnNewButton_1);

        btnNewButton_1_1 = new JButton("Delete Client");
        btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton_1_1.setBounds(238, 333, 153, 44);
        getContentPane().add(btnNewButton_1_1);

        btnNewButton_1_2 = new JButton("Modify Client");
        btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton_1_2.setBounds(63, 401, 153, 44);
        getContentPane().add(btnNewButton_1_2);

        btnNewButton_1_3 = new JButton("View All Clients");
        btnNewButton_1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("abc");
                ClientBLL clientBLL = new ClientBLL();
                List<Client> clientList = new ArrayList<Client>();


                try {
                    clientList = clientBLL.findAll();
                } catch (Exception ex) {

                }

                generateTable.generateTable(clientList, table, model);
            }
        });
        btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_1_3.setBounds(238, 401, 153, 44);
        getContentPane().add(btnNewButton_1_3);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(492, 135, 447, 310);
        getContentPane().add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel();


       // generateTable.generateTable(client,table, model);

    }

    public void showActionListener(ActionListener e){
        btnNewButton_1_3.addActionListener(e);
    }
}
