package View;

import Model.Percistency.ManagerPersistency;
import Presenter.ManagerPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ManagerPage implements IManagerView, ActionListener{
    private JFrame frame;
    private JTextField searchByNameTextField;
    private JTextField storeIdTf;
    private JButton prodButton;
    private JButton dispButton;
    private JButton nameButton;
    private JButton btnSearchByName;
    private JButton priceInStoreButton;
    private JButton priceButton;
    private JTextArea textArea;


    public ManagerPage(){
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 500);

        frame.getContentPane().setLayout(null);

        JLabel pageTitle = new JLabel("Perfume Store Management App");
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(168, 23, 485, 41);
        frame.getContentPane().add(pageTitle);

        JLabel lblNewLabel = new JLabel("MANAGER");
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(353, 75, 96, 22);
        frame.getContentPane().add(lblNewLabel);


        btnSearchByName = new JButton("Search perfume by name");
        btnSearchByName.addActionListener(this);
        btnSearchByName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSearchByName.setBounds(62, 140, 221, 48);
        frame.getContentPane().add(btnSearchByName);

        searchByNameTextField = new JTextField();
        searchByNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        searchByNameTextField.setBounds(62, 206, 221, 41);
        frame.getContentPane().add(searchByNameTextField);
        searchByNameTextField.setColumns(10);

        textArea = new JTextArea();
        textArea.setBounds(377, 140, 356, 107);
        //frame.getContentPane().add(textArea);


        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(377, 140, 356, 107);
        frame.getContentPane().add(scrollPane);

        JLabel filterPerfumeLabel = new JLabel("FILTER PERFUMES BY ");
        filterPerfumeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        filterPerfumeLabel.setBounds(90, 315, 187, 14);
        frame.getContentPane().add(filterPerfumeLabel);

        prodButton = new JButton("Producer");
        prodButton.addActionListener(this);
        prodButton.setBounds(47, 350, 126, 33);
        frame.getContentPane().add(prodButton);

        dispButton = new JButton("Disponibility");
        dispButton.addActionListener(this);
        dispButton.addActionListener(this);
        dispButton.setBounds(183, 350, 126, 33);
        frame.getContentPane().add(dispButton);

        priceButton = new JButton("Price");
        priceButton.addActionListener(this);
        priceButton.setBounds(114, 394, 126, 35);
        frame.getContentPane().add(priceButton);

        JLabel lblNewLabel_2 = new JLabel("View Perfumes in store filtered by");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_2.setBounds(414, 292, 296, 58);
        frame.getContentPane().add(lblNewLabel_2);

        priceInStoreButton = new JButton("Price");
        priceInStoreButton.addActionListener(this);
        priceInStoreButton.setBounds(410, 394, 126, 35);
        frame.getContentPane().add(priceInStoreButton);

        nameButton = new JButton("Name");
        nameButton.addActionListener(this);
        nameButton.setBounds(571, 394, 126, 35);
        frame.getContentPane().add(nameButton);

        JLabel lblNewLabel_3 = new JLabel("Store ID");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(478, 357, 74, 14);
        frame.getContentPane().add(lblNewLabel_3);

        storeIdTf = new JTextField();
        storeIdTf.setBounds(551, 353, 81, 22);
        frame.getContentPane().add(storeIdTf);
        storeIdTf.setColumns(10);

        frame.getContentPane().setLayout(null);

    }

    @Override
    public void setVisible(){
        frame.setVisible(true);
    }

    @Override
    public JTextField getSearchByNameTextField() {
        return searchByNameTextField;
    }

    @Override
    public void setSearchByNameTextField(JTextField searchByNameTextField) {
        this.searchByNameTextField = searchByNameTextField;
    }

    @Override
    public JTextField getStoreIdTf() {
        return storeIdTf;
    }

    @Override
    public void setStoreIdTf(JTextField storeIdTf) {
        this.storeIdTf = storeIdTf;
    }

    @Override
    public JButton getProdButton() {
        return prodButton;
    }

    @Override
    public void setProdButton(JButton prodButton) {
        this.prodButton = prodButton;
    }

    @Override
    public JButton getDispButton() {
        return dispButton;
    }

    @Override
    public void setDispButton(JButton dispButton) {
        this.dispButton = dispButton;
    }

    @Override
    public JButton getNameButton() {
        return nameButton;
    }

    @Override
    public void setNameButton(JButton nameButton) {
        this.nameButton = nameButton;
    }

    @Override
    public JButton getBtnSearchByName() {
        return btnSearchByName;
    }

    @Override
    public void setBtnSearchByName(JButton btnSearchByName) {
        this.btnSearchByName = btnSearchByName;
    }

    @Override
    public JButton getPriceInStoreButton() {
        return priceInStoreButton;
    }

    @Override
    public void setPriceInStoreButton(JButton priceInStoreButton) {
        this.priceInStoreButton = priceInStoreButton;
    }

    @Override
    public JButton getPriceButton() {
        return priceButton;
    }

    @Override
    public void setPriceButton(JButton priceButton) {
        this.priceButton = priceButton;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void getErrorMessage(){
        JOptionPane.showMessageDialog(null,"Please enter store ID");
    }

    public void searchNameError(){
        JOptionPane.showMessageDialog(null,"Please enter name");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ManagerPresenter managerPresenter = new ManagerPresenter(this);
        if(e.getSource() == getBtnSearchByName()){
            managerPresenter.searchByName();

        }

        if(e.getSource() == getProdButton()){
            managerPresenter.filterPerfumeByProducer();
        }

        if(e.getSource() == getDispButton()){
            managerPresenter.filterByDisponibility();
        }
        if(e.getSource() == getPriceButton()){
            managerPresenter.filterByPrice();
        }

        if(e.getSource() == getNameButton()){
            managerPresenter.filterByName();
        }

        if(e.getSource() == getPriceInStoreButton()){
            managerPresenter.filterByPrice1();
        }

    }
}
