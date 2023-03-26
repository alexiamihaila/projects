package model;

public class Order {
    private int orderId;
    private int client;
    private int product;
    private int quantity;

    public int  getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

}
