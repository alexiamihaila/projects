package Model;

public class Stock {
    private int storeID;
    private int perfumeID;
    private int quantity;


    public Stock(int storeID, int perfumeID, int quantity) {
        this.storeID = storeID;
        this.perfumeID = perfumeID;
        this.quantity = quantity;
    }


    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getPerfumeID() {
        return perfumeID;
    }

    public void setPerfumeID(int perfumeID) {
        this.perfumeID = perfumeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
