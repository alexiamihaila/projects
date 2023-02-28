import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Scheduler {

    private ArrayList<Coada> cozi;
    private int maxNrCozi;
    private Strategy strategy = new ConcreteStrategy();


    public Scheduler(int maxNrCozi){
        this.cozi = new ArrayList<Coada>();
        for(int i = 0; i < maxNrCozi; i++){
            BlockingQueue<Client> queue = new LinkedBlockingQueue<>();
            AtomicInteger wt = new AtomicInteger();
            Coada coada = new Coada(queue, wt);
            // Thread t = new Thread(coada);
            //t.start();
            cozi.add(coada);
        }
    }


    public ArrayList<Coada> getCozi(){
        return cozi;
    }

    public void dispatchTask(Client c){

        strategy.addTaskStrategy(cozi, c);
    }
}
