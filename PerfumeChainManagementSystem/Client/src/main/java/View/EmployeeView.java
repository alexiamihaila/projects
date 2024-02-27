package View;

//import Controller.EmployeeController;

import Controller.EmployeeController;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeView implements ViewType {
    ResourceBundle rs = ResourceBundle.getBundle("msg");
    private JFrame frame;
    private JTextField nameTextField;
    private JTextField producerTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JTextField textField;
    private JTextArea textArea;
    private JTextField searchByNameTF;

    private JButton btnSearchByName;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnSelect;
    private JButton btnProducer;
    private JButton btnDisponibility;
    private JButton btnPrice;
    private JButton btnViewPerfumesFrom;
    private JButton btnViewAllPerfumes;
    private JButton btnFilterByNameAndPrice;
    private JButton generate;
    private JComboBox<String> comboBox;

    private JScrollPane scrollPane;
    private EmployeeController employeeController;
    private JButton btnEN;
    private JButton btnRo;
    private JButton btnFr;

    private JLabel lblNewLabel_1;
    private JLabel pageTitle;
    private JLabel lblNewLabel;
    private JLabel nameLabel;
    private JLabel producerLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JLabel filterLabel;
    private JLabel lblStoreId;
    private JLabel getStockLabel;
    private String language;
    private Locale locale;

    public EmployeeView(int storeId, String language){

        this.language = language;
        if(language.equals("English"))
            locale = new Locale("en", "US");

        else if(language.equals("French"))
            locale = new Locale("fr", "FR");

        else if(language.equals("Romanian"))
            locale = new Locale("ro", "RO");

        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 704);


        frame.getContentPane().setLayout(null);

        pageTitle = new JLabel();
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(136, 23, 569, 41);
        frame.getContentPane().add(pageTitle);

        lblNewLabel = new JLabel();
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(326, 75, 162, 22);
        frame.getContentPane().add(lblNewLabel);

        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nameLabel.setBounds(46, 145, 140, 28);
        frame.getContentPane().add(nameLabel);

        producerLabel = new JLabel();
        producerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        producerLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        producerLabel.setBounds(56, 189, 130, 28);
        frame.getContentPane().add(producerLabel);

        priceLabel = new JLabel();
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        priceLabel.setBounds(66, 233, 120, 28);
        frame.getContentPane().add(priceLabel);

        stockLabel = new JLabel();
        stockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        stockLabel.setBounds(56, 277, 130, 28);
        frame.getContentPane().add(stockLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(196, 153, 191, 22);
        frame.getContentPane().add(nameTextField);
        nameTextField.setColumns(10);

        btnInsert = new JButton();
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnInsert.setBounds(46, 331, 162, 29);
        frame.getContentPane().add(btnInsert);

        btnDelete = new JButton();
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDelete.setBounds(225, 331, 162, 29);
        frame.getContentPane().add(btnDelete);

        btnUpdate = new JButton();
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnUpdate.setBounds(46, 371, 162, 29);
        frame.getContentPane().add(btnUpdate);

        btnSelect = new JButton();
        btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSelect.setBounds(225, 371, 162, 29);
        frame.getContentPane().add(btnSelect);

        btnProducer = new JButton();
        btnProducer.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnProducer.setBounds(434, 331, 162, 29);
        frame.getContentPane().add(btnProducer);

        btnDisponibility = new JButton();
        btnDisponibility.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDisponibility.setBounds(606, 331, 162, 29);
        frame.getContentPane().add(btnDisponibility);

        btnPrice = new JButton();
        btnPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPrice.setBounds(531, 371, 162, 29);
        frame.getContentPane().add(btnPrice);


        textArea = new JTextArea();
        textArea.setBounds(434, 107, 332, 154);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(434, 107, 332, 154);
        frame.getContentPane().add(scrollPane);

        filterLabel = new JLabel();
        filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filterLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        filterLabel.setVerticalAlignment(SwingConstants.TOP);
        filterLabel.setBounds(508, 296, 191, 28);
        frame.getContentPane().add(filterLabel);

        lblStoreId = new JLabel();
        lblStoreId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStoreId.setFont(new Font("Tahoma", Font.PLAIN, 21));
        lblStoreId.setBounds(46, 100, 140, 28);
        frame.getContentPane().add(lblStoreId);

        textField = new JTextField(Integer.toString(storeId));
        textField.setColumns(10);
        textField.setBounds(196, 108, 191, 22);
        frame.getContentPane().add(textField);
        frame.getContentPane().setLayout(null);

        btnViewAllPerfumes = new JButton();
        btnViewAllPerfumes.setForeground(new Color(51, 102, 153));
        btnViewAllPerfumes.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        //btnViewAllPerfumes.setBackground(new Color(51, 102, 153));
        btnViewAllPerfumes.setBounds(46, 473, 390, 41);

        btnSearchByName = new JButton();

        btnSearchByName.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSearchByName.setBounds(46, 425, 197, 36);
        frame.getContentPane().add(btnSearchByName);

        searchByNameTF = new JTextField();
        searchByNameTF.setBounds(253, 426, 171, 36);
        frame.getContentPane().add(searchByNameTF);
        searchByNameTF.setColumns(10);

        btnViewPerfumesFrom = new JButton();
        btnViewPerfumesFrom.setForeground(new Color(51, 102, 153));
        btnViewPerfumesFrom.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnViewPerfumesFrom.setBounds(451, 473, 317, 41);
        frame.getContentPane().add(btnViewPerfumesFrom);

        btnFilterByNameAndPrice = new JButton();
        btnFilterByNameAndPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnFilterByNameAndPrice.setBounds(508, 425, 215, 36);
        frame.getContentPane().add(btnFilterByNameAndPrice);
        frame.getContentPane().add(btnViewAllPerfumes);

        lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(46, 525, 261, 28);
        frame.getContentPane().add(lblNewLabel_1);

        String[] type = {"JSON", "XML", "TXT", "CSV"};
        comboBox = new JComboBox(type);
        comboBox.setBounds(317, 525, 134, 30);
        frame.getContentPane().add(comboBox);

        generate = new JButton();
        generate.setBounds(461, 525, 107, 28);
        frame.getContentPane().add(generate);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(434, 107, 332, 154);
        frame.getContentPane().add(textArea);

        producerTextField = new JTextField();
        producerTextField.setColumns(10);
        producerTextField.setBounds(196, 197, 191, 22);
        frame.getContentPane().add(producerTextField);

        priceTextField = new JTextField();
        priceTextField.setColumns(10);
        priceTextField.setBounds(196, 241, 191, 22);
        frame.getContentPane().add(priceTextField);

        stockTextField = new JTextField();
        stockTextField.setColumns(10);
        stockTextField.setBounds(196, 285, 191, 22);
        frame.getContentPane().add(stockTextField);

        btnEN = new JButton("EN");
        btnEN.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnEN.setBounds(136, 579, 77, 36);
        frame.getContentPane().add(btnEN);

        btnRo = new JButton("RO");
        btnRo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnRo.setBounds(225, 579, 77, 36);
        frame.getContentPane().add(btnRo);

        btnFr = new JButton("FR");
        btnFr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFr.setBounds(46, 579, 77, 36);
        frame.getContentPane().add(btnFr);

        setRs(ResourceBundle.getBundle("msg", locale));

        pageTitle.setText(rs.getString("pageTitle"));
        lblNewLabel.setText(rs.getString("lblNewLabel"));
        nameLabel.setText(rs.getString("nameLabel"));
        producerLabel.setText(rs.getString("producerLabel"));
        priceLabel.setText(rs.getString("priceLabel"));
        stockLabel.setText(rs.getString("stockLabel"));
        filterLabel.setText(rs.getString("filterLabel"));
        lblStoreId.setText(rs.getString("lblStoreId"));
        btnSearchByName.setText(rs.getString("btnSearchByName"));
        btnDelete.setText(rs.getString("btnDelete"));
        btnDisponibility.setText(rs.getString("btnDisponibility"));
        btnUpdate.setText(rs.getString("btnUpdate"));
        btnSelect.setText(rs.getString("btnSelect"));
        btnProducer.setText(rs.getString("btnProducer"));
        btnInsert.setText(rs.getString("btnInsert"));
        btnPrice.setText(rs.getString("btnPrice"));
        btnViewPerfumesFrom.setText(rs.getString("btnViewPerfumesFrom"));
        btnViewAllPerfumes.setText(rs.getString("btnViewAllPerfumes"));
        btnFilterByNameAndPrice.setText(rs.getString("btnFilterByNameAndPrice"));
        generate.setText(rs.getString("generate"));
        btnEN.setText(rs.getString("btnEN"));
        btnRo.setText(rs.getString("btnRo"));
        btnFr.setText(rs.getString("btnFr"));
        lblNewLabel_1.setText(rs.getString("lblNewLabel_1"));

        frame.setVisible(true);



        employeeController = new EmployeeController(this);

    }


    public String getNameTextField() {
        return nameTextField.getText();
    }

    public void setNameTextField(String s) {
        this.nameTextField.setText(s);
    }

    public String getProducerTextField() {
        return producerTextField.getText();
    }

    public void setProducerTextField(String s) {
        this.producerTextField.setText(s);
    }

    public String getPriceTextField() {
        return priceTextField.getText();
    }

    public void setPriceTextField(String s) {
        this.priceTextField.setText(s);
    }

    public String getStockTextField() {
        return stockTextField.getText();
    }

    public void setStockTextField(String s) {
        this.stockTextField.setText(s);
    }

    public String getTextField() {
        return textField.getText();
    }

    public void setTextField(String s) {
        this.textField.setText(s);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(String s) {
        this.textArea.setText(s);
    }

    public String getSearchByNameTF() {
        return searchByNameTF.getText();
    }

    public void setSearchByNameTF(String s) {
        this.searchByNameTF.setText(s);
    }

    public JButton getBtnSearchByName() {
        return btnSearchByName;
    }

    public void setBtnSearchByName(JButton btnSearchByName) {
        this.btnSearchByName = btnSearchByName;
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

    public JButton getBtnViewPerfumesFrom() {
        return btnViewPerfumesFrom;
    }

    public void setBtnViewPerfumesFrom(JButton btnViewPerfumesFrom) {
        this.btnViewPerfumesFrom = btnViewPerfumesFrom;
    }

    public JButton getBtnViewAllPerfumes() {
        return btnViewAllPerfumes;
    }

    public void setBtnViewAllPerfumes(JButton btnViewAllPerfumes) {
        this.btnViewAllPerfumes = btnViewAllPerfumes;
    }

    public JButton getBtnFilterByNameAndPrice() {
        return btnFilterByNameAndPrice;
    }

    public void setBtnFilterByNameAndPrice(JButton btnFilterByNameAndPrice) {
        this.btnFilterByNameAndPrice = btnFilterByNameAndPrice;
    }

    public JButton getGenerate() {
        return generate;
    }

    public void setGenerate(JButton generate) {
        this.generate = generate;
    }

    public String getSelectedItem() {
        return (String) comboBox.getSelectedItem();
    }


    public JButton getBtnEN() {
        return btnEN;
    }

    public void setBtnEN(JButton btnEN) {
        this.btnEN = btnEN;
    }

    public JButton getBtnRo() {
        return btnRo;
    }

    public void setBtnRo(JButton btnRo) {
        this.btnRo = btnRo;
    }

    public JButton getBtnFr() {
        return btnFr;
    }

    public void setBtnFr(JButton btnFr) {
        this.btnFr = btnFr;
    }

    public void showMessage(String message) {

        JOptionPane.showMessageDialog(new JFrame(), message);
    }
    public void setRs(ResourceBundle rs) {
        this.rs = rs;
    }

    public void onLocaleChange(Locale locale){
        setRs(ResourceBundle.getBundle("msg", locale));

        pageTitle.setText(rs.getString("pageTitle"));
        lblNewLabel.setText(rs.getString("lblNewLabel"));
        nameLabel.setText(rs.getString("nameLabel"));
        producerLabel.setText(rs.getString("producerLabel"));
        priceLabel.setText(rs.getString("priceLabel"));
        stockLabel.setText(rs.getString("stockLabel"));
        filterLabel.setText(rs.getString("filterLabel"));
        lblStoreId.setText(rs.getString("lblStoreId"));
        btnSearchByName.setText(rs.getString("btnSearchByName"));
        btnDelete.setText(rs.getString("btnDelete"));
        btnDisponibility.setText(rs.getString("btnDisponibility"));
        btnUpdate.setText(rs.getString("btnUpdate"));
        btnSelect.setText(rs.getString("btnSelect"));
        btnProducer.setText(rs.getString("btnProducer"));
        btnInsert.setText(rs.getString("btnInsert"));
        btnPrice.setText(rs.getString("btnPrice"));
        btnViewPerfumesFrom.setText(rs.getString("btnViewPerfumesFrom"));
        btnViewAllPerfumes.setText(rs.getString("btnViewAllPerfumes"));
        btnFilterByNameAndPrice.setText(rs.getString("btnFilterByNameAndPrice"));
        generate.setText(rs.getString("generate"));
        btnEN.setText(rs.getString("btnEN"));
        btnRo.setText(rs.getString("btnRo"));
        btnFr.setText(rs.getString("btnFr"));
        lblNewLabel_1.setText(rs.getString("lblNewLabel_1"));


    }

}
