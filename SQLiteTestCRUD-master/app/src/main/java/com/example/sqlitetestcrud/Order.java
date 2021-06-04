package com.example.sqlitetestcrud;

public class Order {
    private int id;
    private String orderName;
    private float price;
    private int quantity;
    private String timeOrder;
    private String dateOrder;

    public Order(int id, String orderName, float price, int quantity,String timeOrder, String dateOrder) {
        this.id = id;
        this.orderName = orderName;
        this.price = price;
        this.quantity = quantity;
        this.timeOrder = timeOrder;
        this.dateOrder = dateOrder;
    }

    public Order(String orderName, float price, int quantity, String timeOrder, String dateOrder) {
        this.orderName = orderName;
        this.price = price;
        this.quantity = quantity;
        this.timeOrder = timeOrder;
        this.dateOrder = dateOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }


}
