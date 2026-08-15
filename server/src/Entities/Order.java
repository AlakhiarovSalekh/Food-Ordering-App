package Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private String addressId;
    private Date orderDate;
    private int totalAmount;
    private double totalPrice;
    private List<OrderItem> orderItems;

    public Order(int orderId, int customerId, String addressId, Date orderDate, int totalAmount, double totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        this.orderItems = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
