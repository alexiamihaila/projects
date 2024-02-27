package View;
//import Controller.ManagerController;

import Controller.ManagerController;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManagerView implements ViewType{
    ResourceBundle rs = ResourceBundle.getBundle("msg");
    private JFrame frame;
    private JTextField searchByNameTextField;
    private JTextField storeIdTf;

   private ManagerController managerController;

    private JButton btnFilter;
    private JButton btnSearchByName;
    private JButton filterBy;
    private JButton btnGenerate;
    private JButton btnRo;
    private JButton btnFR;
    private JButton btnEn;
    private JButton btnNewButton;

    private JTextArea textArea;

    private JLabel lblNewLabel_11;

    private JComboBox<String> comboBox;
    private JComboBox<String> comboBox_1;
    private JLabel pageTitle;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel filterPerfumeLabel;
    private JLabel fileLabel;

    private String language;

    private Locale locale;

    public ManagerView(String language){
        this.language = language;

        if(language.equals("English"))
            locale = new Locale("en", "US");

        else if(language.equals("French"))
            locale = new Locale("fr", "FR");

        else if(language.equals("Romanian"))
            locale = new Locale("ro", "RO");

        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.setBounds(100, 100, 800, 583);

        frame.getContentPane().setLayout(null);

        pageTitle = new JLabel();
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        pageTitle.setBounds(168, 23, 485, 41);
        frame.getContentPane().add(pageTitle);

        lblNewLabel = new JLabel();
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(353, 75, 96, 22);
        frame.getContentPane().add(lblNewLabel);


        btnSearchByName = new JButton();
        btnSearchByName.setFont(new Font("Tahoma", Font.PLAIN, 13));
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

        filterBy = new JButton();
        filterBy.setBounds(116, 393, 126, 31);
        frame.getContentPane().add(filterBy);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(377, 140, 356, 107);
        frame.getContentPane().add(scrollPane);

        filterPerfumeLabel = new JLabel();
        filterPerfumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filterPerfumeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        filterPerfumeLabel.setBounds(50, 315, 251, 16);
        frame.getContentPane().add(filterPerfumeLabel);


        lblNewLabel_2 = new JLabel();
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_2.setBounds(353, 254, 399, 80);
        frame.getContentPane().add(lblNewLabel_2);

        btnFilter = new JButton();
        btnFilter.setBounds(493, 382, 126, 35);
        frame.getContentPane().add(btnFilter);

        lblNewLabel_3 = new JLabel();
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(385, 357, 192, 14);
        frame.getContentPane().add(lblNewLabel_3);

        storeIdTf = new JTextField();
        storeIdTf.setBounds(587, 355, 81, 22);
        frame.getContentPane().add(storeIdTf);
        storeIdTf.setColumns(10);

        frame.getContentPane().setLayout(null);

        String[] types = {"Store","Producer", "Disponibility", "Price"};
        comboBox = new JComboBox(types);
        comboBox.setBounds(90, 352, 181, 31);
        frame.getContentPane().add(comboBox);

        lblNewLabel_11 = new JLabel();
        lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblNewLabel_11.setBounds(425, 292, 264, 58);
        frame.getContentPane().add(lblNewLabel_11);

        fileLabel = new JLabel();
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fileLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        fileLabel.setBounds(90, 455, 254, 28);
        frame.getContentPane().add(fileLabel);

        btnGenerate = new JButton();
        btnGenerate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGenerate.setBounds(493, 457, 126, 28);
        frame.getContentPane().add(btnGenerate);

        String[] types1 = {"XML", "JSON", "CSV", "TXT"};
        comboBox_1 = new JComboBox(types1);
        comboBox_1.setBounds(364, 458, 112, 28);

        frame.getContentPane().add(comboBox_1);

        btnFR = new JButton("FR");
        btnFR.setFont(new Font("Tahoma", Font.PLAIN, 15));

        btnFR.setBounds(277, 494, 81, 37);
        frame.getContentPane().add(btnFR);

        btnEn = new JButton("EN");
        btnEn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnEn.setBounds(374, 494, 81, 37);
        frame.getContentPane().add(btnEn);

        btnRo = new JButton("RO");
        btnRo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRo.setBounds(465, 494, 81, 37);
        frame.getContentPane().add(btnRo);

        btnNewButton = new JButton();
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnNewButton.setBounds(10, 494, 175, 34);
        frame.getContentPane().add(btnNewButton);

        setRs(ResourceBundle.getBundle("msg", locale));

        pageTitle.setText(rs.getString("pageTitle1"));
        lblNewLabel.setText(rs.getString("lblNewLabel1"));
        btnSearchByName.setText(rs.getString("btnSearchByName"));
        filterBy.setText(rs.getString("filterBy"));
        filterPerfumeLabel.setText(rs.getString("filterPerfumeLabel"));
        lblNewLabel_2.setText(rs.getString("lblNewLabel_2"));
        btnFilter.setText(rs.getString("btnFilter"));
        lblNewLabel_3.setText(rs.getString("lblNewLabel_3"));
        lblNewLabel_11.setText(rs.getString("lblNewLabel_11"));
        fileLabel.setText(rs.getString("lblNewLabel_1"));
        btnGenerate.setText(rs.getString("generate"));
        btnNewButton.setText(rs.getString("btnNewButton"));

        frame.setVisible(true);

        managerController = new ManagerController(this);
    }

    public void setTextArea(String s) {
        this.textArea.setText(s);
    }

    public JButton getBtnRo() {
        return btnRo;
    }

    public void setBtnRo(JButton btnRo) {
        this.btnRo = btnRo;
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

    public String getSelectedItem() {
        return (String) comboBox_1.getSelectedItem();
    }

    public String getSelectedItem1() {
        return (String) comboBox.getSelectedItem();
    }

    public JButton getBtnGenerate(){
        return btnGenerate;
    }

    public String getSearchByNameTextField() {
        return searchByNameTextField.getText();
    }

    public void setSearchByNameTextField(JTextField searchByNameTextField) {
        this.searchByNameTextField = searchByNameTextField;
    }

    public String getStoreIdTf() {
        return storeIdTf.getText();
    }

    public void setStoreIdTf(JTextField storeIdTf) {
        this.storeIdTf = storeIdTf;
    }

    public JButton getBtnFilter() {
        return btnFilter;
    }

    public void setBtnFilter(JButton btnFilter) {
        this.btnFilter = btnFilter;
    }

    public JButton getBtnSearchByName() {
        return btnSearchByName;
    }

    public void setBtnSearchByName(JButton btnSearchByName) {
        this.btnSearchByName = btnSearchByName;
    }

    public JButton getFilterBy() {
        return filterBy;
    }

    public JButton getChartButton(){return btnNewButton;}

    public void setFilterBy(JButton filterBy) {
        this.filterBy = filterBy;
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
    public void setRs(ResourceBundle rs) {
        this.rs = rs;
    }


    public void onLocaleChange(Locale locale){
        setRs(ResourceBundle.getBundle("msg", locale));

        pageTitle.setText(rs.getString("pageTitle1"));
        lblNewLabel.setText(rs.getString("lblNewLabel1"));
        btnSearchByName.setText(rs.getString("btnSearchByName"));
        filterBy.setText(rs.getString("filterBy"));
        filterPerfumeLabel.setText(rs.getString("filterPerfumeLabel"));
        lblNewLabel_2.setText(rs.getString("lblNewLabel_2"));
        btnFilter.setText(rs.getString("btnFilter"));
        lblNewLabel_3.setText(rs.getString("lblNewLabel_3"));
        lblNewLabel_11.setText(rs.getString("lblNewLabel_11"));
        fileLabel.setText(rs.getString("lblNewLabel_1"));
        btnGenerate.setText(rs.getString("generate"));
        btnNewButton.setText(rs.getString("btnNewButton"));
    }
}
