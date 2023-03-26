package View;

import javax.swing.*;

public interface IEmployeeView {
    public JFrame getFrame();

    public void setFrame(JFrame frame);

    public JTextField getNameTextField();

    public void setNameTextField(JTextField nameTextField);

    public JTextField getProducerTextField();

    public void setProducerTextField(JTextField producerTextField);

    public JTextField getPriceTextField();

    public void setPriceTextField(JTextField priceTextField);

    public JTextField getStockTextField();

    public void setStockTextField(JTextField stockTextField);

    public JTextField getTextField();

    public void setTextField(JTextField textField);

    public void setVisible();

    public void setStoreId(String storeId);

    public void updateTextArea(String s, JTextArea tA);

    public JTextArea getTextArea();

    public void setTextArea(JTextArea textArea);

    public JButton getBtnInsert();

    public void setBtnInsert(JButton btnInsert);

    public JButton getBtnDelete();

    public void setBtnDelete(JButton btnDelete);

    public JButton getBtnUpdate();

    public void setBtnUpdate(JButton btnUpdate);

    public JButton getBtnSelect();


    public void setBtnSelect(JButton btnSelect);

    public JButton getBtnProducer();

    public void setBtnProducer(JButton btnProducer);


    public JButton getBtnDisponibility();

    public void setBtnDisponibility(JButton btnDisponibility);

    public JButton getBtnPrice();

    public void setBtnPrice(JButton btnPrice);


    public JButton getBtnViewAllUsers();

    public void setBtnViewAllUsers(JButton btnViewAllUsers);

    public void selectErrorMEssage();

    public void deleteErrorMEssage();
    public void insertErrorMEssage();
    public void updateErrorMessage();
}
