import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfQueues;
    private int numberOfCLients;
    private int averageWaitingTime;
    private int avgWSum = 0;
    private int countTime;
    private int peakHour = 0;
    private int averageServiceTime;
    private int peakClients = 0;
    private int max = 0;
    private int cl = 0;
    private WriteInFile fileData;
    private View view;
    private Scheduler scheduler;
    public ArrayList<Client> generatedClients = new ArrayList<Client>();
    public int[] arrTimeVec ;



    public int getRandomArrivalTime() {
        return (int) ((Math.random() * (maxArrivalTime - minArrivalTime)) + minArrivalTime);
    }
    public int getRandomProcessingTime() {
        return (int) ((Math.random() * (maxProcessingTime - minProcessingTime)) + minProcessingTime);
    }

    public void setTimeLimit(int timeLimit){
        this.timeLimit = timeLimit;
    }

    public void setNumberOfQueues(int numberOfQueues){
        this.numberOfQueues = numberOfQueues;
    }

    public void setNumberOfCLients(int numberOfCLients){
        this.numberOfCLients = numberOfCLients;
    }

    public void setMaxProcessingTime(int maxProcessingTime){
        this.maxProcessingTime = maxProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime){
        this.minProcessingTime = minProcessingTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime){
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime){
        this.minArrivalTime = minArrivalTime;
    }

    public void createArrTime(){
        for(int i = 0; i < this.arrTimeVec.length; i++){
            int a = getRandomArrivalTime();
            this.arrTimeVec[i] = a;
        }
        Arrays.sort(this.arrTimeVec);
//        for(int i = 0; i < this.arrTimeVec.length - 1; i++)
//            for(int j = i + 1; j < this.arrTimeVec.length; j++){
//                if(this.arrTimeVec[i] > this.arrTimeVec[j]) {
//                    int aux = this.arrTimeVec[i];
//                    this.arrTimeVec[i] = this.arrTimeVec[j];
//                    this.arrTimeVec[j] = aux;
//                }
//            }

    }


    public void generateNrandomClients(int numberOfCLients){
        this.arrTimeVec = new int[numberOfCLients];
        this.createArrTime();
        for(int i = 0; i < numberOfCLients; i++) {
            int randPrTime = getRandomProcessingTime();
            this.averageServiceTime+=randPrTime;
            int randArrTime = this.arrTimeVec[i] ;//arrTimeVec[i];
            Client clAux = new Client(i + 1, randArrTime, randPrTime);
            this.generatedClients.add(clAux);
        }


    }
    public SimulationManager() {

        this.view = new View();
        this.fileData = new WriteInFile();
        getFromInterface();

    }


    @Override
    public synchronized void run() {
        countTime = 0;
        //SimulationManager simulMan = new SimulationManager();
        int currentTime = 0;
        while (currentTime <= timeLimit) {
            System.out.println("Time: " + currentTime);
            String s = "";
            s = s + "Time: "+ currentTime + "\n";
            for (Iterator<Client> iterator = generatedClients.iterator(); iterator.hasNext(); ) {
                Client c = iterator.next();
                int arrTime = c.getArrivalTime();
                if (arrTime == currentTime && arrTime + c.getServiceTime() <= timeLimit) {
                    scheduler.dispatchTask(c);
                    iterator.remove();
                    averageWaitingTime += c.getServiceTime();
                    //peakClients++;
                }
            }


            int i = 0;
            System.out.print("Waiting clients: ");
            s = s + "Waiting clients: ";
            for (Client c : generatedClients) {
                System.out.print(c.toString() + ", ");
                s =s+ c.toString() + ", ";
            }
            s = s + "\n";
            System.out.println();

            int count = 0;
            boolean ok = false;
            avgWSum = 0;

            this.peakClients = 0;
            for (Coada c : scheduler.getCozi()) {
                i++;
                peakClients+= c.getWaitingPeriod().get();
                System.out.print("Queue " + i + ": ");
                s = s + "Queue " + i + ": ";
                if (c.getCoada().isEmpty()) {
                    System.out.println("Closed");
                    s = s + "Closed" + "\n";
                } else {
                    ok = true;
                    for (Client client : c.getCoada()) {
                        System.out.println(client.toString() + ", ");
                        s = s + client.toString() + ", \n";
                    }

                }
                if(this.peakClients > this.max){
                    this.max = this.peakClients;
                    this.peakHour = currentTime;
                }
            }

            this.countTime++;
            currentTime++;
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentTime > timeLimit)
                for (Coada c : scheduler.getCozi())
                    c.setVar(false);

            System.out.println();
            s = s + "\n";
            //System.out.println(s);
            try {
                fileData.writeInFile(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        view.getTextArea().append(s);
        }
        float rezServ = (float)averageServiceTime/numberOfCLients;
        float avgWait = (float)averageWaitingTime/countTime;
        try {
            System.out.println("Average service time is: " + rezServ + "\n");
            System.out.println("Average waiting time is: " + avgWait + "\n");
            System.out.println("Peak hour is: " + peakHour);
            fileData.writeInFile("Average service time is: " + rezServ + "\n");
            fileData.writeInFile("Average waiting time is: " + avgWait + "\n");
            fileData.writeInFile("Peak hour is: " + peakHour);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFromInterface(){
        view.buttonRunListener(e->{
        try {
            String timeLimit = view.getTimeLimitTF();
            String maxProcessingTime = view.getMaxProcessingTimeTF();
            String minProcessingTime = view.getMinProcessingTimeTF();
            String maxArrivalTime = view.getMaxArrivalTimeTF();
            String minArrivalTime = view.getMinArrivalTimeTF();
            String numberOfQueues = view.getNumberOfQueuesTF();
            String numberOfCLients = view.getNumberOfClientsTF();


            int timeLimitInt = Integer.parseInt(timeLimit);
            int maxProcessingTimeInt = Integer.parseInt(maxProcessingTime);
            int minProcessingTimeInt = Integer.parseInt(minProcessingTime);
            int maxArrivalTimeInt = Integer.parseInt(maxArrivalTime);
            int minArrivalTimeInt = Integer.parseInt(minArrivalTime);
            int numberOfQueuesInt = Integer.parseInt(numberOfQueues);
            int numberOfCLientsInt = Integer.parseInt(numberOfCLients);

            setNumberOfCLients(numberOfCLientsInt);
            setNumberOfQueues(numberOfQueuesInt);
            setMaxProcessingTime(maxProcessingTimeInt);
            setMinProcessingTime(minProcessingTimeInt);
            setMinArrivalTime(minArrivalTimeInt);
            setMaxArrivalTime(maxArrivalTimeInt);
            setTimeLimit(timeLimitInt);
        } catch(NumberFormatException x){
            view.showError("Datele au fost introduse gresit!");
        }

            scheduler = new Scheduler(numberOfQueues);
            for(int i = 0; i < numberOfQueues; i++)
            {
                scheduler.getCozi().get(i).setVar(true);
                Thread t1 = new Thread(scheduler.getCozi().get(i));
                t1.start();
            }
            generateNrandomClients(numberOfCLients);

        Thread t = new Thread(this);
        t.start();
    });
    }
        public static void main (String[]args){
            SimulationManager s = new SimulationManager();

        }


}
