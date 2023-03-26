package View;

import javax.swing.*;

public interface IAdminView {

    public void setVisible();


    public JFrame getFrame();

    public void setFrame(JFrame frame);

    public JTextField getNameTextField();

    public void setNameTextField(JTextField nameTextField) ;

    public JTextField getProducerTextField() ;

    public void setProducerTextField(JTextField producerTextField) ;

    public JTextField getPriceTextField() ;

    public void setPriceTextField(JTextField priceTextField);

    public JTextField getStockTextField() ;

    public void setStockTextField(JTextField stockTextField) ;

    public JTextField getTextField();

    public void setTextField(JTextField textField) ;


    public JButton getBtnPrice() ;

    public void setBtnPrice(JButton btnPrice) ;

    public JButton getBtnInsert() ;

    public void setBtnInsert(JButton btnInsert) ;

    public JButton getBtnDelete() ;

    public void setBtnDelete(JButton btnDelete) ;

    public JButton getBtnUpdate() ;

    public void setBtnUpdate(JButton btnUpdate) ;

    public JButton getBtnSelect() ;

    public void setBtnSelect(JButton btnSelect) ;

    public JTextArea getTextArea();

    public void setTextArea(JTextArea textArea);
    public void insertErrorMessage();

    public void deleteErrorMEssage();

    public void selectErrorMEssage();

    public void updateErrorMEssage();
}
