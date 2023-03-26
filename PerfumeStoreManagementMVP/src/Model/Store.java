package Model;

import java.util.List;

public class Store {

    private int storeId;
    private int managerId;

    public Store(int storeId, int managerId) {
        this.storeId = storeId;
        this.managerId = managerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }






}
