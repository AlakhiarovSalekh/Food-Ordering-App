package Entities;

import java.util.Map;

public class OrderRequest {
    private int customerId;
    private String address;
    private Map<MenuItem, Integer> cartItems;

    public OrderRequest(int customerId, String address, Map<MenuItem, Integer> cartItems) {
        this.customerId = customerId;
        this.address = address;
        this.cartItems = cartItems;
    }

    // Include getters and setters for the fields
    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public Map<MenuItem, Integer> getCartItems() {
        return cartItems;
    }
}
