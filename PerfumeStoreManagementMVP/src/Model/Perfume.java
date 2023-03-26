package Model;

public class Perfume {


    private int perfumeId;
    private String name;
    private String producer;
    private int price;

    public Perfume(int perfumeId,String name, String producer, int price) {
        this.perfumeId = perfumeId;
        this.name = name;
        this.producer = producer;
        this.price = price;
    }
    public Perfume(String name, String producer, int price){
        this.name = name;
        this.producer = producer;
        this.price = price;
    }

    public Perfume(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString(){
        return this.getName() + ", "+ this.getProducer()+ ", " + this.getPrice();
    }

    public int getPerfumeId() {
        return perfumeId;
    }

    public void setPerfumeId(int perfumeId) {
        this.perfumeId = perfumeId;
    }

}
