package View;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginView extends JFrame {
    ResourceBundle rs = ResourceBundle.getBundle("msg");
    private JFrame frame;
    private JTextField usernameTextField;
    private JTextField passwordTextField;

    private JButton btnLogIn =   new JButton("LOG IN");
    private JComboBox comboBox;
    private JLabel usernameLabel;
    private JLabel pageTitle;
    private JLabel passwordLabel;
    private JButton btnEN;
    private JButton btnFR;
    private JButton btnRO;

    private String language = "";

    public LoginView() { frame = new JFrame();
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        usernameTextField = new JTextField();
        usernameTextField.setText("");
        usernameTextField.setBounds(352, 120, 229, 32);
        frame.getContentPane().add(usernameTextField);
        usernameTextField.setColumns(10);

        usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(new Color(51, 102, 153));
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
        usernameLabel.setBounds(91, 120, 251, 32);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(usernameLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setForeground(new Color(51, 102, 153));
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
        passwordLabel.setBounds(148, 178, 194, 32);
        frame.getContentPane().add(passwordLabel);

        pageTitle = new JLabel("Perfume Store Management App");
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(117, 23, 585, 41);
        frame.getContentPane().add(pageTitle);

        passwordTextField = new JTextField();
        passwordTextField.setText("");
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(352, 184, 229, 32);
        frame.getContentPane().add(passwordTextField);


        btnLogIn.setForeground(new Color(51, 102, 153));
        btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnLogIn.setBounds(314, 251, 171, 41);
        frame.getContentPane().add(btnLogIn);

        btnEN = new JButton("EN");
        btnEN.setBounds(359, 390, 89, 41);
        frame.getContentPane().add(btnEN);

        btnFR = new JButton("FR");
        btnFR.setBounds(240, 390, 89, 41);
        frame.getContentPane().add(btnFR);

        btnRO = new JButton("RO");
        btnRO.setBounds(475, 390, 89, 41);
        frame.getContentPane().add(btnRO);
        frame.setVisible(true);

    }


    public String getUsernameTextField() {
        return usernameTextField.getText();
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public String getPasswordTextField() {
        return passwordTextField.getText();
    }

    public void setPasswordTextField(JTextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public JButton getBtnLogIn() {
        return btnLogIn;
    }

    public void setBtnLogIn(JButton btnLogIn) {
        this.btnLogIn = btnLogIn;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(JLabel pageTitle) {
        this.pageTitle = pageTitle;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public JButton getBtnEN() {
        return btnEN;
    }

    public void setBtnEN(JButton btnEN) {
        this.btnEN = btnEN;
    }

    public JButton getBtnFR() {
        return btnFR;
    }

    public void setBtnFR(JButton btnFR) {
        this.btnFR = btnFR;
    }

    public JButton getBtnRO() {
        return btnRO;
    }

    public void setBtnRO(JButton btnRO) {
        this.btnRO = btnRO;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public void setRs(ResourceBundle rs) {
        this.rs = rs;
    }

    public void showMessage(String message) {

        JOptionPane.showMessageDialog(new JFrame(), message);
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void onLocaleChange(Locale locale){
        setRs(ResourceBundle.getBundle("msg", locale));

        usernameLabel.setText(rs.getString("usernameLabel"));
        passwordLabel.setText(rs.getString("passwordLabel"));
        btnLogIn.setText(rs.getString("btnLogIn"));
        pageTitle.setText(rs.getString("pageTitle"));
    }

}
