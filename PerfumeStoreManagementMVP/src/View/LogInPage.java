package View;
import Presenter.LogInPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class LogInPage extends JFrame implements ILoginView, ActionListener {
    private JFrame frame;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton btnLogIn =   new JButton("LOG IN");
    private JComboBox comboBox;
    private JLabel usernameLabel;
    private JLabel pageTitle;
    private JLabel passwordLabel;
    private JLabel userLabel;

    public LogInPage(AdminPage adminPage, ManagerPage managerPage, EmployeePage employeePage) {
        frame = new JFrame();
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
        usernameLabel.setBounds(219, 104, 195, 53);
        frame.getContentPane().add(usernameLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(new Color(51, 102, 153));
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
        passwordLabel.setBounds(219, 178, 161, 32);
        frame.getContentPane().add(passwordLabel);

        pageTitle = new JLabel("Perfume Store Management App");
        pageTitle.setForeground(new Color(51, 102, 153));
        pageTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
        pageTitle.setBounds(168, 23, 485, 41);
        frame.getContentPane().add(pageTitle);



        passwordTextField = new JTextField();
        passwordTextField.setText("");
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(352, 184, 229, 32);
        frame.getContentPane().add(passwordTextField);


        btnLogIn.setForeground(new Color(51, 102, 153));
        btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnLogIn.setBounds(339, 251, 127, 41);
        btnLogIn.addActionListener(this);
        frame.getContentPane().add(btnLogIn);
        frame.setVisible(true);
    }


    @Override
    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    @Override
    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    @Override
    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    @Override
    public void setPasswordTextField(JTextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    @Override
    public JButton getBtnLogIn() {
        return btnLogIn;
    }

    @Override
    public void setBtnLogIn(JButton btnLogIn) {
        this.btnLogIn = btnLogIn;
    }

    @Override
    public JComboBox getComboBox() {
        return comboBox;
    }

    @Override
    public void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    @Override
    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    @Override
    public JLabel getPageTitle() {
        return pageTitle;
    }

    @Override
    public void setPageTitle(JLabel pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    @Override
    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    @Override
    public JLabel getUserLabel() {
        return userLabel;
    }

    @Override
    public void setUserLabel(JLabel userLabel) {
        this.userLabel = userLabel;
    }


    @Override
    public void actionPerformed(ActionEvent e)  {

        LogInPresenter logInPresenter = new LogInPresenter(this);

        if(e.getSource() == getBtnLogIn()){

            logInPresenter.logIn();

       }

    }
}
