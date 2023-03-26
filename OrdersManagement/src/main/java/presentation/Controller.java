package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private ClientView cV;
    private MainPage mP;
    private OrderView oV;
    private ProductView pV;

    public Controller(ClientView cV, MainPage mP, OrderView oV, ProductView pV){

        this.cV = cV;
        this.mP = mP;
        this.oV = oV;
        this.pV = pV;


        mP.addClientBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cV.setVisible(true);
            }
        });

        mP.addOrderBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oV.setVisible(true);
            }
        });

        mP.addProductBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pV.setVisible(true);
            }
        });



    }
}
