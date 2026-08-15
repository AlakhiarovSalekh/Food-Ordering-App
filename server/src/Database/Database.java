package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import Entities.Menu;
import Entities.MenuItem;
import Entities.Order;
import Entities.Customer;
import Entities.OrderItem;

public class Database {
    static final String db_url = "jdbc:mysql://localhost:3306/food_ordering_app";
    static final String USER = "root";
    static final String PASS = "";
    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection(db_url, USER, PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer verifyLogin(String username, String password) {
        try {
            String sql = "SELECT * FROM customers WHERE LOWER(name) = LOWER(?) AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String name = rs.getString("name");
                String street = rs.getString("street");
                String city = rs.getString("city");
                String phone = rs.getString("phone");

                Customer customer = new Customer(customerId, name, street, city, phone);
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Customer verifySignup(String username, String password, String phone, String street, String city) {
        try {
            String sql_ = "SELECT * FROM customers WHERE LOWER(name) = LOWER(?)";
            PreparedStatement ps_ = connection.prepareStatement(sql_);
            ps_.setString(1, username);
            ResultSet rs_ = ps_.executeQuery();
            if (rs_.next()) {
                return null;
            }
            String sql = "INSERT INTO customers (name, password, phone, street, city) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, phone);
            ps.setString(4, street);
            ps.setString(5, city);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int customerId = rs.getInt(1);
                Customer customer = new Customer(customerId, username, street, city, phone);
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Menu getMenu() {
        Menu menu = new Menu();

        try {
            String sql = "SELECT * FROM menu_items";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("item_name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");

                MenuItem menuItem = new MenuItem(id, name, description, price);
                menu.addMenuItem(menuItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return menu;
    }

    public List<Order> getPreviousOrders(int customerId) {
        List<Order> previousOrders = new ArrayList<>();
    
        try {
            String sql = "SELECT o.order_id, o.customer_id, o.address_id, o.order_date, o.total_amount, o.total_price, "
                    + "oi.order_item_id, oi.quantity, oi.price, oi.item_name " +
                    "FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "WHERE o.customer_id = ? " +
                    "ORDER BY o.order_date DESC";
    
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId); 
    
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                customerId = resultSet.getInt("customer_id");
                String addressId = resultSet.getString("address_id");
                Date orderDate = resultSet.getDate("order_date");
                int totalAmount = resultSet.getInt("total_amount");
                double totalPrice = resultSet.getDouble("total_price");
                int orderItemId = resultSet.getInt("order_item_id");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String itemName = resultSet.getString("item_name");
    
                Order order = previousOrders.stream().filter(o -> o.getOrderId() == orderId).findFirst().orElse(null);
    
                if (order == null) {
                    order = new Order(orderId, customerId, addressId, orderDate, totalAmount, totalPrice);
                    previousOrders.add(order);
                }
    
                OrderItem orderItem = new OrderItem(orderItemId, orderId, itemName, quantity, price);

                order.addOrderItem(orderItem);
            }
    
            return previousOrders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }        

    public int saveOrder(int customerId, String addressId, LocalDate orderDate, int totalAmount, Double totalPrice) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders (customer_id, address_id, order_date, total_amount, total_price) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, customerId);
            statement.setString(2, addressId);
            statement.setDate(3, java.sql.Date.valueOf(orderDate));
            statement.setInt(4, totalAmount);
            statement.setDouble(5, totalPrice);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                return orderId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public void saveOrderItems(int orderId, Map<MenuItem, Integer> cartItems) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO order_items (order_id, item_name, quantity, price) VALUES (?, ?, ?, ?)");
            for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
                MenuItem menuItem = entry.getKey();
                int quantity = entry.getValue();
                statement.setInt(1, orderId);
                statement.setString(2, menuItem.getName());
                statement.setInt(3, quantity);
                statement.setDouble(4, menuItem.getPrice());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveOrderReview(int orderId, int rating, String review) {
        try {
            String sql = "INSERT INTO order_reviews (order_id, rating, review) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, rating);
            ps.setString(3, review);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Map<String, Object>> getOrderReviews(int customerId) {
        List<Map<String, Object>> reviews = new ArrayList<>();
        try {
            String sql = "SELECT o.order_id, o.order_date, r.rating, r.review " +
                         "FROM orders o " +
                         "JOIN order_reviews r ON o.order_id = r.order_id " +
                         "WHERE o.customer_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> review = new HashMap<>();
                review.put("orderId", rs.getInt("order_id"));
                review.put("orderDate", rs.getDate("order_date"));
                review.put("rating", rs.getInt("rating"));
                review.put("review", rs.getString("review"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reviews;
    }
}
