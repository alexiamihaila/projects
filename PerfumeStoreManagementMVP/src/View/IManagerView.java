package View;

import javax.swing.*;

public interface IManagerView {

    public void setVisible();

    public JTextField getSearchByNameTextField();

    public void setSearchByNameTextField(JTextField searchByNameTextField);

    public JTextField getStoreIdTf();

    public void setStoreIdTf(JTextField storeIdTf);

    public JButton getProdButton();

    public void setProdButton(JButton prodButton);

    public JButton getDispButton();

    public void setDispButton(JButton dispButton);

    public JButton getNameButton();

    public void setNameButton(JButton nameButton);

    public JButton getBtnSearchByName();

    public void setBtnSearchByName(JButton btnSearchByName);


    public JButton getPriceInStoreButton();

    public void setPriceInStoreButton(JButton priceInStoreButton);

    public JButton getPriceButton();

    public void setPriceButton(JButton priceButton);

    public JTextArea getTextArea() ;

    public void setTextArea(JTextArea textArea) ;

    public void getErrorMessage();
    public void searchNameError();

}
