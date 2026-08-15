package Entities;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private String itemName;
    private int quantity;
    private double price;

    public OrderItem(int orderItemId, int orderId, String itemName, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
