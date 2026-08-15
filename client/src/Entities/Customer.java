package Entities;

public class Customer {
    private int customerId;
    private String name;
    private String street;
    private String city;
    private String phone;

    public Customer(int customerId, String name, String street, String city, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.street = street;
        this.city = city;
        this.phone = phone;
    }

    // Getters and setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
