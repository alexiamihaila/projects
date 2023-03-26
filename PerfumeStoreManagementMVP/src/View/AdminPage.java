package View;

import Model.Percistency.AdminPersistency;
import Presenter.AdminPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminPage implements IAdminView, ActionListener{

    private JFrame frame;
    private JTextField nameTextField;
    private JTextField producerTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JTextField textField;
    private JButton btnPrice;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnSelect;
    private JTextArea textArea;


    public AdminPage(){
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 500);

        frame.getContentPane().setLayout(null);

        JLabel pageTitle = new JLabel("Perfume Store Management App");
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(168, 23, 485, 41);
        frame.getContentPane().add(pageTitle);

        JLabel lblNewLabel = new JLabel("ADMIN");
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(353, 75, 96, 22);
        frame.getContentPane().add(lblNewLabel);

        JLabel nameLabel = new JLabel("Password");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nameLabel.setBounds(46, 153, 105, 28);
        frame.getContentPane().add(nameLabel);

        JLabel producerLabel = new JLabel("Function");
        producerLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        producerLabel.setBounds(46, 208, 87, 28);
        frame.getContentPane().add(producerLabel);

        JLabel priceLabel = new JLabel("Manager id");
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        priceLabel.setBounds(46, 258, 125, 28);
        frame.getContentPane().add(priceLabel);

        JLabel stockLabel = new JLabel("Store id");
        stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        stockLabel.setBounds(46, 297, 87, 28);
        frame.getContentPane().add(stockLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(196, 158, 191, 29);
        frame.getContentPane().add(nameTextField);
        nameTextField.setColumns(10);

        producerTextField = new JTextField();
        producerTextField.setColumns(10);
        producerTextField.setBounds(196, 213, 191, 29);
        frame.getContentPane().add(producerTextField);

        priceTextField = new JTextField();
        priceTextField.setColumns(10);
        priceTextField.setBounds(196, 257, 191, 29);
        frame.getContentPane().add(priceTextField);

        stockTextField = new JTextField();
        stockTextField.setColumns(10);
        stockTextField.setBounds(196, 302, 191, 29);
        frame.getContentPane().add(stockTextField);

        btnInsert = new JButton("INSERT");
        btnInsert.addActionListener(this);
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnInsert.setBounds(46, 351, 162, 29);
        frame.getContentPane().add(btnInsert);

        btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(this);
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDelete.setBounds(225, 351, 162, 29);
        frame.getContentPane().add(btnDelete);

        btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(this);
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnUpdate.setBounds(46, 404, 162, 29);
        frame.getContentPane().add(btnUpdate);

        btnSelect = new JButton("SELECT");
        btnSelect.addActionListener(this);
        btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSelect.setBounds(225, 404, 162, 29);
        frame.getContentPane().add(btnSelect);

        btnPrice = new JButton("View all users");
        btnPrice.addActionListener(this);
        btnPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPrice.setBounds(531, 366, 162, 41);
        frame.getContentPane().add(btnPrice);


        textArea = new JTextArea();
        textArea.setBounds(434, 108, 342, 223);
        //frame.getContentPane().add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(434, 108, 342, 223);
        frame.getContentPane().add(scrollPane);


        JLabel nameLabel_1 = new JLabel("Username");
        nameLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nameLabel_1.setBounds(46, 110, 125, 28);
        frame.getContentPane().add(nameLabel_1);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(196, 108, 191, 29);
        frame.getContentPane().add(textField);
        frame.getContentPane().setLayout(null);

    }

    public void setVisible(){
        frame.setVisible(true);
    }


    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JTextField getProducerTextField() {
        return producerTextField;
    }

    public void setProducerTextField(JTextField producerTextField) {
        this.producerTextField = producerTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public void setPriceTextField(JTextField priceTextField) {
        this.priceTextField = priceTextField;
    }

    public JTextField getStockTextField() {
        return stockTextField;
    }

    public void setStockTextField(JTextField stockTextField) {
        this.stockTextField = stockTextField;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }


    public JButton getBtnPrice() {
        return btnPrice;
    }

    public void setBtnPrice(JButton btnPrice) {
        this.btnPrice = btnPrice;
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public void setBtnInsert(JButton btnInsert) {
        this.btnInsert = btnInsert;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(JButton btnSelect) {
        this.btnSelect = btnSelect;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void insertErrorMessage(){
        JOptionPane.showMessageDialog(null,"Please fill name, password or function");
    }

    public void deleteErrorMEssage(){
        JOptionPane.showMessageDialog(null, "Please enter user to be deleted");
    }
    public void selectErrorMEssage(){
        JOptionPane.showMessageDialog(null, "Please fill username field");
    }
    public void updateErrorMEssage(){
        JOptionPane.showMessageDialog(null, "Please fill all fields");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AdminPresenter adminPresenter = new AdminPresenter(this);

        if(e.getSource() == getBtnPrice()){
            adminPresenter.viewAllUsers();
        }
        if(e.getSource() == getBtnSelect()){
            adminPresenter.selectUser();
        }
        if(e.getSource() == getBtnInsert()){
            adminPresenter.insertUser();
        }
        if(e.getSource() == getBtnDelete()){
            adminPresenter.deleteUser();
        }
        if(e.getSource() == getBtnUpdate()){
            adminPresenter.updateUser();
        }
    }


}
