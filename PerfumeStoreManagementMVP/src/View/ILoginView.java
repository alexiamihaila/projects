package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public interface ILoginView {

    public JTextField getUsernameTextField();
    public void setUsernameTextField(JTextField usernameTextField);

    public JTextField getPasswordTextField();

    public void setPasswordTextField(JTextField passwordTextField);

    public JButton getBtnLogIn();

    public void setBtnLogIn(JButton btnLogIn);

    public JComboBox getComboBox();

    public void setComboBox(JComboBox comboBox);

    public JLabel getUsernameLabel();

    public void setUsernameLabel(JLabel usernameLabel);

    public JLabel getPageTitle();

    public void setPageTitle(JLabel pageTitle);

    public JLabel getPasswordLabel();

    public void setPasswordLabel(JLabel passwordLabel);

    public JLabel getUserLabel();

    public void setUserLabel(JLabel userLabel);

}
