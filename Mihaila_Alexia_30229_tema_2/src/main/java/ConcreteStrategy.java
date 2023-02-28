import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategy implements Strategy{

    private int timpMin;
    private int indiceMin;
    //in functie de cel mai scurt timp
    @Override
    public void addTaskStrategy(ArrayList<Coada> cozi, Client client) {
        timpMin = 100000;
        for(int i = 0; i < cozi.size(); i++){

            int timpCoada = cozi.get(i).getWaitingPeriod().intValue();
            if(timpCoada < timpMin) {
                timpMin = timpCoada;
                //System.out.println(timpMin);
                indiceMin = i;
            }
        }

        for(Coada c: cozi){
            if(timpMin == c.getWaitingPeriod().intValue()) {
                c.addClient(client);
                break;
            }
        }



    }

}
