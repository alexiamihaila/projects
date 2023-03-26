package start;

import presentation.*;

public class Start {


    public static void main(String[] args){


        ClientView cV = new ClientView();
        OrderView oV = new OrderView();
        MainPage mP = new MainPage();
        ProductView pV = new ProductView();

        mP.setVisible(true);
        Controller c = new Controller(cV, mP, oV, pV);
    }
}
