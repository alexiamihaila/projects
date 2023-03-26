package model;

public class Product {

    private int prodId;
    private int quantity;
    private String name;
    private int price;

    public Product(int prodId,String name, int quantity, int price){
        this.prodId = prodId;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
