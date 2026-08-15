package Controllers;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Database.Database;
import Entities.Menu;
import Entities.MenuItem;
import Entities.Order;
import Entities.OrderRequest;
import Entities.OrderRequestSerializer;
import Entities.Customer;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Database database;
    private PrintWriter writer;
    private BufferedReader reader;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.database = new Database();
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Read client requests and send responses
            String request;
            while((request = reader.readLine()) != null) {
                // Handle different types of requests from the client
                if (request.startsWith("LOGIN")) {
                    handleLogin(request);
                } else if (request.startsWith("SIGNUP")){
                    handleSignup(request);
                } else if (request.equals("MENU")) {
                    handleGetMenu();
                } else if (request.equals("PLACE_ORDER")) {
                    handlePlaceOrder();
                } else if (request.startsWith("PREVIOUS_ORDERS")) {
                    handlePreviousOrders(request);
                } else if (request.startsWith("SAVE_REVIEW")) {
                    handleSaveReview(request);
                } else if (request.startsWith("GET_REVIEWS")) {
                    handleGetReviews(request);
                } else if (request.equals("PAYMENT_SUCCESS")) {
                    handlePaymentSuccess();
                } else if (request.equals("PAYMENT_FAILURE")) {
                    handlePaymentFailure();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Handle client requests
    private void handleLogin(String request) {
        String[] parts = request.split(":");
        String username = parts[1];
        String password = parts[2];

        Customer loginSuccess = database.verifyLogin(username, password);

        if (loginSuccess != null) {
            writer.println(userToJson(loginSuccess));
        } else {
            writer.println("LOGIN_FAILURE");
        }
    }

    private void handleSignup(String request) {
        String[] parts = request.split(":");
        String username = parts[1];
        String password = parts[2];
        String phone = parts[3];
        String street = parts[4];
        String city = parts[5];

        Customer signupSuccess = database.verifySignup(username, password, phone, street, city);

        if (signupSuccess != null) {
            writer.println(userToJson(signupSuccess));
        } else {
            writer.println("SIGNUP_FAILURE");
        }
    }

    private void handleGetMenu() {
        Menu menu = database.getMenu();
        writer.println(menuToJson(menu));
    }

    private void handlePreviousOrders(String request) {
        String[] parts = request.split(":");
        int customerId = Integer.parseInt(parts[1]);
        List<Order> previousOrders = database.getPreviousOrders(customerId);
    
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String response = gson.toJson(previousOrders);
    
        writer.println(response);
    }
    
    private void handlePlaceOrder() {
        String orderJson;
        try {
            orderJson = reader.readLine();
            System.out.println(orderJson);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(OrderRequest.class, new OrderRequestSerializer());
            Gson gson = gsonBuilder.create();

            OrderRequest orderRequest = gson.fromJson(orderJson, OrderRequest.class);

            processOrder(orderRequest.getCustomerId(), orderRequest.getAddress(), orderRequest.getCartItems());

            writer.println("ORDER_PLACED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSaveReview(String request) {
        String[] parts = request.split(":");
        int orderId = Integer.parseInt(parts[1]);
        int rating = Integer.parseInt(parts[2]);
        String review = parts[3];

        database.saveOrderReview(orderId, rating, review);
        writer.println("REVIEW_SAVED");
    }

    private void handleGetReviews(String request) {
        String[] parts = request.split(":");
        int customerId = Integer.parseInt(parts[1]);
        List<Map<String, Object>> reviews = database.getOrderReviews(customerId);

        Gson gson = new Gson();
        String response = gson.toJson(reviews);
        writer.println(response);
    }

    private void handlePaymentSuccess() {
        writer.println("ORDER_PLACED");
    }

    private void handlePaymentFailure() {
        writer.println("PAYMENT_FAILED");
    }

    public void processOrder(int customerId, String addressId, Map<MenuItem, Integer> cartItems) {
        LocalDate orderDate = LocalDate.now();
        
        int totalAmount = 0;
        Double totalPrice = 0.0;
        for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += quantity;
            totalPrice += menuItem.getPrice()*quantity;
        }
        
        int orderId = database.saveOrder(customerId, addressId, orderDate, totalAmount, totalPrice);
        
        database.saveOrderItems(orderId, cartItems);
    }

    private void closeConnections() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility methods
    private String menuToJson(Menu menu) {
        Gson gson = new Gson();
        return gson.toJson(menu);
    }
    
    private String userToJson(Customer user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}
