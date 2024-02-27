package View;

//import Controller.AdminController;


import Controller.AdminController;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminView implements ViewType{
    ResourceBundle rs = ResourceBundle.getBundle("msg");
    private JFrame frame;
    private JTextField passwordTextField;
    private JTextField functionTextField;
    private JTextField managerIdTextField;
    private JTextField storeIdTextField;
    private JTextField usernameTextField;

    private JButton btnViewAllUsers;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnSelect;
    private JButton btnFilter;
    private JButton btnFR;
    private JButton btnEn;
    private JButton btnRo;

    private JComboBox<String> comboBox;
    private JTextArea textArea;

    private JLabel pageTitle;
    private JLabel lblNewLabel_33;
    private JLabel passwordLabel;
    private JLabel functionLabel;
    private JLabel managerIdLabel;
    private JLabel storeIdLabel;
    private JLabel lblNewLabel_15;
    private JLabel usernameLabel;

    private AdminController adminController;
    private String language = "";
    private Locale locale;

    public AdminView(String language){
        this.language = language;

        if(language.equals("English"))
            locale = new Locale("en", "US");

        else if(language.equals("French"))
            locale = new Locale("fr", "FR");

        else if(language.equals("Romanian"))
            locale = new Locale("ro", "RO");

        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 556);

        frame.getContentPane().setLayout(null);

        pageTitle = new JLabel();
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(115, 23, 564, 41);
        frame.getContentPane().add(pageTitle);

        lblNewLabel_33 = new JLabel();
        lblNewLabel_33.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_33.setForeground(new Color(51, 102, 153));
        lblNewLabel_33.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_33.setBounds(317, 75, 156, 22);
        frame.getContentPane().add(lblNewLabel_33);

        passwordLabel = new JLabel();
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        passwordLabel.setBounds(46, 153, 140, 28);
        frame.getContentPane().add(passwordLabel);

        functionLabel = new JLabel();
        functionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        functionLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        functionLabel.setBounds(46, 208, 140, 28);
        frame.getContentPane().add(functionLabel);

        managerIdLabel = new JLabel();
        managerIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        managerIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        managerIdLabel.setBounds(30, 258, 156, 28);
        frame.getContentPane().add(managerIdLabel);

        storeIdLabel = new JLabel();
        storeIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        storeIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        storeIdLabel.setBounds(46, 297, 140, 28);
        frame.getContentPane().add(storeIdLabel);

        passwordTextField = new JTextField();
        passwordTextField.setBounds(196, 158, 191, 29);
        frame.getContentPane().add(passwordTextField);
        passwordTextField.setColumns(10);

        functionTextField = new JTextField();
        functionTextField.setColumns(10);
        functionTextField.setBounds(196, 213, 191, 29);
        frame.getContentPane().add(functionTextField);

        managerIdTextField = new JTextField();
        managerIdTextField.setColumns(10);
        managerIdTextField.setBounds(196, 257, 191, 29);
        frame.getContentPane().add(managerIdTextField);

        storeIdTextField = new JTextField();
        storeIdTextField.setColumns(10);
        storeIdTextField.setBounds(196, 302, 191, 29);
        frame.getContentPane().add(storeIdTextField);

        btnInsert = new JButton();
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnInsert.setBounds(46, 351, 162, 29);
        frame.getContentPane().add(btnInsert);

        btnDelete = new JButton();
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDelete.setBounds(225, 351, 162, 29);
        frame.getContentPane().add(btnDelete);

        btnUpdate = new JButton();
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnUpdate.setBounds(46, 404, 162, 29);
        frame.getContentPane().add(btnUpdate);

        btnSelect = new JButton();
        btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSelect.setBounds(225, 404, 162, 29);
        frame.getContentPane().add(btnSelect);

        btnViewAllUsers = new JButton();
        btnViewAllUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnViewAllUsers.setBounds(496, 364, 211, 41);
        frame.getContentPane().add(btnViewAllUsers);


        textArea = new JTextArea();
        textArea.setBounds(434, 108, 342, 223);
        //frame.getContentPane().add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(434, 108, 342, 223);
        frame.getContentPane().add(scrollPane);


        usernameLabel = new JLabel();
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        usernameLabel.setBounds(0, 110, 190, 28);
        frame.getContentPane().add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setColumns(10);
        usernameTextField.setBounds(196, 108, 191, 29);
        frame.getContentPane().add(usernameTextField);

        String[] functions=new String[]{"EMPLOYEE", "ADMIN", "MANAGER"};
        comboBox = new JComboBox(functions);
        comboBox.setBounds(531, 418, 162, 30);
        frame.getContentPane().add(comboBox);


        lblNewLabel_15 = new JLabel();
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_15.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_15.setBounds(397, 414, 124, 30);
        frame.getContentPane().add(lblNewLabel_15);

        btnFilter = new JButton();
        btnFilter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnFilter.setBounds(696, 418, 80, 28);
        frame.getContentPane().add(btnFilter);

        frame.getContentPane().setLayout(null);

        btnFR = new JButton("FR");
        btnFR.setBounds(266, 478, 71, 30);
        frame.getContentPane().add(btnFR);

        btnEn = new JButton("EN");
        btnEn.setBounds(359, 478, 71, 30);
        frame.getContentPane().add(btnEn);

        btnRo = new JButton("RO");
        btnRo.setBounds(450, 478, 71, 30);
        frame.getContentPane().add(btnRo);

        setRs(ResourceBundle.getBundle("msg", locale));

        btnViewAllUsers.setText(rs.getString("btnViewAllUsers"));
        btnInsert.setText(rs.getString("btnInsert"));
        btnDelete.setText(rs.getString("btnDelete"));
        btnUpdate.setText(rs.getString("btnUpdate"));
        btnSelect.setText(rs.getString("btnSelect"));
        btnFilter.setText(rs.getString("btnFilter"));
        pageTitle.setText(rs.getString("pageTitle"));
        lblNewLabel_33.setText(rs.getString("lblNewLabel_33"));
        passwordLabel.setText(rs.getString("passwordLabel"));
        functionLabel.setText(rs.getString("functionLabel"));
        managerIdLabel.setText(rs.getString("managerIdLabel"));
        storeIdLabel.setText(rs.getString("storeIdLabel"));
        lblNewLabel_15.setText(rs.getString("lblNewLabel_15"));
        usernameLabel.setText(rs.getString("usernameLabel"));

        frame.setVisible(true);

        adminController = new AdminController(this);
    }


    public String getPasswordTextField() {
        return passwordTextField.getText();
    }

    public void setPasswordTextField(JTextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public String getFunctionTextField() {
        return functionTextField.getText();
    }

    public void setFunctionTextField(JTextField functionTextField) {
        this.functionTextField = functionTextField;
    }

    public String getManagerIdTextField() {
        return managerIdTextField.getText();
    }

    public void setManagerIdTextField(JTextField managerIdTextField) {
        this.managerIdTextField = managerIdTextField;
    }

    public String getStoreIdTextField() {
        return storeIdTextField.getText();
    }

    public void setStoreIdTextField(JTextField storeIdTextField) {
        this.storeIdTextField = storeIdTextField;
    }

    public String getUsernameTextField() {
        return usernameTextField.getText();
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }


    public JButton getBtnFR() {
        return btnFR;
    }

    public void setBtnFR(JButton btnFR) {
        this.btnFR = btnFR;
    }

    public JButton getBtnEn() {
        return btnEn;
    }

    public void setBtnEn(JButton btnEn) {
        this.btnEn = btnEn;
    }

    public JButton getBtnRo() {
        return btnRo;
    }

    public void setBtnRo(JButton btnRo) {
        this.btnRo = btnRo;
    }


    public JButton getBtnViewAllUsers() {
        return btnViewAllUsers;
    }

    public void setBtnViewAllUsers(JButton btnViewAllUsers) {
        this.btnViewAllUsers = btnViewAllUsers;
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

    public JButton getBtnFilter() {
        return btnFilter;
    }

    public void setBtnFilter(JButton btnFilter) {
        this.btnFilter = btnFilter;
    }

    public void showMessage(String message) {

        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    public void setTextArea(String s) {
        this.textArea.setText(s);
    }
    public String getSelectedItem() {
        Object selectedItem = this.comboBox.getSelectedItem();
        return selectedItem.toString();
    }

    public void setRs(ResourceBundle rs) {
        this.rs = rs;
    }


    public void onLocaleChange(Locale locale){
        setRs(ResourceBundle.getBundle("msg", locale));

        btnViewAllUsers.setText(rs.getString("btnViewAllUsers"));
        btnInsert.setText(rs.getString("btnInsert"));
        btnDelete.setText(rs.getString("btnDelete"));
        btnUpdate.setText(rs.getString("btnUpdate"));
        btnSelect.setText(rs.getString("btnSelect"));
        btnFilter.setText(rs.getString("btnFilter"));
        pageTitle.setText(rs.getString("pageTitle"));
        lblNewLabel_33.setText(rs.getString("lblNewLabel_33"));
        passwordLabel.setText(rs.getString("passwordLabel"));
        functionLabel.setText(rs.getString("functionLabel"));
        managerIdLabel.setText(rs.getString("managerIdLabel"));
        storeIdLabel.setText(rs.getString("storeIdLabel"));
        lblNewLabel_15.setText(rs.getString("lblNewLabel_15"));
        usernameLabel.setText(rs.getString("usernameLabel"));
    }

}
