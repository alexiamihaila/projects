package View;

import Model.*;
import Model.Percistency.PerfumeFromStorePercistency;
import Presenter.EmployeePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmployeePage implements IEmployeeView, ActionListener{
    private JFrame frame;
    private JTextField nameTextField;
    private JTextField producerTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JTextField textField;
    private JTextArea textArea;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnSelect;
    private JButton btnProducer;
    private JButton btnDisponibility;
    private JButton btnPrice;


    private JButton btnViewAllUsers;

    private PerfumeFromStorePercistency persistency;

    public EmployeePage(){
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 618);

        frame.getContentPane().setLayout(null);

        JLabel pageTitle = new JLabel("Perfume Store Management App");
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(168, 23, 485, 41);
        frame.getContentPane().add(pageTitle);

        JLabel lblNewLabel = new JLabel("EMPLOYEE");
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(353, 75, 96, 22);
        frame.getContentPane().add(lblNewLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nameLabel.setBounds(46, 153, 62, 28);
        frame.getContentPane().add(nameLabel);

        JLabel producerLabel = new JLabel("Producer");
        producerLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        producerLabel.setBounds(46, 208, 87, 28);
        frame.getContentPane().add(producerLabel);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        priceLabel.setBounds(46, 247, 62, 28);
        frame.getContentPane().add(priceLabel);

        JLabel stockLabel = new JLabel("Stock");
        stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        stockLabel.setBounds(46, 297, 62, 28);
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

        btnProducer = new JButton("Producer");
        btnProducer.addActionListener(this);
        btnProducer.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnProducer.setBounds(434, 351, 162, 29);
        frame.getContentPane().add(btnProducer);

        btnDisponibility = new JButton("Disponibility");
        btnDisponibility.addActionListener(this);
        btnDisponibility.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDisponibility.setBounds(614, 351, 162, 29);
        frame.getContentPane().add(btnDisponibility);

        btnPrice = new JButton("Price");
        btnPrice.addActionListener(this);
        btnPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPrice.setBounds(531, 404, 162, 29);
        frame.getContentPane().add(btnPrice);


        //scrollPane.setBounds(739, 315, -254, -177);
        //frame.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        textArea.setBounds(434, 131, 342, 173);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(434, 131, 342, 173);
        frame.getContentPane().add(scrollPane);

        JLabel filterLabel = new JLabel("FILTER AFTER");
        filterLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        filterLabel.setVerticalAlignment(SwingConstants.TOP);
        filterLabel.setBounds(550, 315, 162, 28);
        frame.getContentPane().add(filterLabel);

        JLabel lblStoreId = new JLabel("Store ID");
        lblStoreId.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblStoreId.setBounds(46, 110, 111, 28);
        frame.getContentPane().add(lblStoreId);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(196, 108, 191, 29);
        frame.getContentPane().add(textField);
        frame.getContentPane().setLayout(null);

        btnViewAllUsers = new JButton("View All Perfumes From The Selected Store");
        btnViewAllUsers.addActionListener(this);
        btnViewAllUsers.setForeground(new Color(51, 102, 153));
        btnViewAllUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
        //btnViewAllUsers.setBackground(new Color(51, 102, 153));
        btnViewAllUsers.setBounds(140, 489, 390, 41);
        frame.getContentPane().add(btnViewAllUsers);

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

    public void setVisible(){
        frame.setVisible(true);
    }

    public void setStoreId(String storeId){
        getTextField().setText(storeId);
    }

    public void updateTextArea(String s, JTextArea tA){
     tA.add(new JTextArea(s));

    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
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

    public JButton getBtnProducer() {
        return btnProducer;
    }

    public void setBtnProducer(JButton btnProducer) {
        this.btnProducer = btnProducer;
    }

    public JButton getBtnDisponibility() {
        return btnDisponibility;
    }

    public void setBtnDisponibility(JButton btnDisponibility) {
        this.btnDisponibility = btnDisponibility;
    }

    public JButton getBtnPrice() {
        return btnPrice;
    }

    public void setBtnPrice(JButton btnPrice) {
        this.btnPrice = btnPrice;
    }


    public JButton getBtnViewAllUsers() {
        return btnViewAllUsers;
    }

    public void setBtnViewAllUsers(JButton btnViewAllUsers) {
        this.btnViewAllUsers = btnViewAllUsers;
    }

    public void selectErrorMEssage(){
        JOptionPane.showMessageDialog(null, "Please fill username field");
    }
    public void deleteErrorMEssage(){
        JOptionPane.showMessageDialog(null, "Please enter user to be deleted");
    }
    public void insertErrorMEssage(){
        JOptionPane.showMessageDialog(null,"Please fill store id, perfume name and stock");
    }
    public void updateErrorMessage(){
        JOptionPane.showMessageDialog(null, "Please fill all fields");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EmployeePresenter employeePresenter = new EmployeePresenter(this);
        if(e.getSource() == getBtnPrice()){
            employeePresenter.filterBYprice();
        }
        if(e.getSource() == getBtnProducer()){
            employeePresenter.filterBYProducer();
        }
        if(e.getSource() == getBtnDisponibility()){
            employeePresenter.filterByDisponibility();
        }

        if(e.getSource() == btnSelect){
            employeePresenter.selectPerfume();
        }

        if(e.getSource() == btnInsert){
            employeePresenter.insertPerfume();
        }

        if(e.getSource() == btnDelete){
            employeePresenter.deletePerfume();
        }

        if(e.getSource() == btnUpdate){
            employeePresenter.updatePerfume();
        }
        if(e.getSource() == btnViewAllUsers){
            employeePresenter.viewAllPerfumes();
        }
    }
}
