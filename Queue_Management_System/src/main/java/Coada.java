import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Coada implements Runnable {
    private BlockingQueue<Client> coada;
    private AtomicInteger waitingPeriod;
    private boolean var;
    private int wt; //waiting time

    public Coada(BlockingQueue<Client> coada, AtomicInteger waitingPeriod){
        this.coada = coada;
        this.waitingPeriod = waitingPeriod;
    }

    public void addClient(Client newClient) {
        coada.add(newClient);
        int i = 0;
        while(i < newClient.getServiceTime())
        {
            waitingPeriod.incrementAndGet();
            i++;
        }
    }

    public synchronized void  run() {
        while(var){
            if(!coada.isEmpty()) {
                try {
                    Thread.sleep( 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                wt = coada.peek().getServiceTime();
                if (wt > 1) {
                    wt--;
                    coada.peek().setServiceTime(wt);
                    waitingPeriod.decrementAndGet();
                } else if (wt == 1) {
                    coada.remove(coada.peek());
                    waitingPeriod.decrementAndGet();
                }


            }
        }
    }


    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public void setWaitingPeriod(int integ ){
        this.waitingPeriod.addAndGet(integ);
    }

    public BlockingQueue<Client> getCoada() {
        return coada;
    }

    public void setVar(boolean var) {
        this.var = var;
    }
}
