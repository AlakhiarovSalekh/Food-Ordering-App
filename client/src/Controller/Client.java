package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import Entities.*;
import Routes.Router;

public class Client {
    private Socket serverSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Router router;
    private String currentPage;
    private Customer customer;
    private Map<MenuItem, Integer> cartItems;

    public Client() {
        router = new Router(this);
        cartItems = new HashMap<>();
        currentPage = "LOGIN";
    }

    public void start() {
        try {
            serverSocket = new Socket("localhost", 12345);
            writer = new PrintWriter(serverSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            navigateToLoginPage();

            while (true) {
                String response = reader.readLine();
                handleServerResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
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
    }

    // Navigate to pages
    public void navigateToLoginPage() {
        currentPage = "LOGIN";
        router.showLoginPage();
    }

    public void navigateToSignupPage() {
        currentPage = "SIGNUP";
        router.showSignupPage();
    }

    public void navigateToHomePage() {
        currentPage = "HOME";
        router.showHomePage();
    }

    public void navigateToCartPage() {
        currentPage = "CART";
        router.showCartPage();
    }

    public void navigateToHistoryPage() {
        currentPage = "HISTORY";
        sendHistoryRequest();
        router.showHistoryPage();
    }

    public void navigateToProfilePage() {
        currentPage = "PROFILE";
        router.showProfilePage();
    }

    public void navigateToReviewPage() {
        currentPage = "REVIEW";
        sendReviewRequest();
        router.showReviewPage();
    }

    // Handle server responses
    public void handleServerResponse(String response) {
        System.out.println(response);
        switch (currentPage) {
            case "LOGIN":
                handleLoginResponse(response);
                break;
            case "HOME":
                handleHomeResponse(response);
                break;
            case "SIGNUP":
                handleSignupResponse(response);
                break;
            case "HISTORY":
                handleHistoryResponse(response);
                break;
            case "CART":
                handleCartResponse(response);
                break;
            case "REVIEW":
                handleReviewResponse(response);
                break;
            default:
                break;
        }
    }

    // Send requests to the server for each page
    public void sendLoginRequest(String username, String password) throws IOException {
        String request = "LOGIN:" + username + ":" + password;
        writer.println(request);
    }

    public void sendSignupRequest(String username, String password, String phone, String city, String street) {
        String request = "SIGNUP:" + username + ":" + password + ":" + phone + ":" + street + ":" + city;
        writer.println(request);
    }

    public void sendMenuRequest() {
        String request = "MENU";
        writer.println(request);
    }

    public void sendHistoryRequest() {
        String request = "PREVIOUS_ORDERS:" + customer.getCustomerId();
        writer.println(request);
    }

    public void sendReviewRequest() {
        String request = "GET_REVIEWS:" + customer.getCustomerId();
        writer.println(request);
    }

    public void sendSaveReviewRequest(int orderId, int rating, String review) {
        String request = "SAVE_REVIEW:" + orderId + ":" + rating + ":" + review;
        writer.println(request);
    }

    // Handle server responses for each page
    public void handleLoginResponse(String response) {
        if (response.equals("LOGIN_FAILURE")) {
            router.displayErrorMessage("Invalid username or password. Please try again.");
        } else {
            Gson gson = new Gson();
            customer = gson.fromJson(response, Customer.class);
            navigateToHomePage();
            sendMenuRequest();
            router.getProfilePanel().setCustomer(customer);
        }
    }

    public void handleHomeResponse(String response) {
        Menu menu = jsonToMenu(response);
        router.getHomePanel().setMenu(menu);
    }

    public void handleSignupResponse(String response) {
        if (response.equals("SIGNUP_FAILURE")) {
            router.displayErrorMessage("Failed to create an account. Please try again.");
        } else {
            Gson gson = new Gson();
            customer = gson.fromJson(response, Customer.class);
            navigateToHomePage();
            sendMenuRequest();
            router.getProfilePanel().setCustomer(customer);
        }
    }

    public void handleHistoryResponse(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OrderRequest.class, new OrderRequestSerializer());
        Gson gson = gsonBuilder.create();
        Type orderListType = new TypeToken<List<Order>>() {}.getType();

        List<Order> orders = gson.fromJson(response, orderListType);
        router.getHistoryPanel().setOrders(orders);
        router.getHistoryPanel().updateUI();
    }

    public void handleCartResponse(String response) {
        if (response.equals("ORDER_PLACED")) {
            router.displayInfoMessage("Order placed successfully!");
            clearCart();
            navigateToHomePage();
        } else {
            router.displayErrorMessage("Failed to place order. Please try again.");
        }
    }

    public void handleReviewResponse(String response) {
        Gson gson = new Gson();
        Type reviewListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> reviews = gson.fromJson(response, reviewListType);
        router.getReviewPanel().setReviews(reviews);
        router.getReviewPanel().updateUI();
    }

    // Utility methods
    private Menu jsonToMenu(String response) {
        Gson gson = new Gson();
        Menu menu = gson.fromJson(response, Menu.class);
        return menu;
    }

    public Customer getCustomer() {
        return customer;
    }

    // Cart methods
    public void addToCart(MenuItem menuItem) {
        if (cartItems.containsKey(menuItem)) {
            int quantity = cartItems.get(menuItem);
            cartItems.put(menuItem, quantity + 1);
        } else {
            cartItems.put(menuItem, 1);
        }
    }

    public void removeFromCart(MenuItem menuItem) {
        if (cartItems.containsKey(menuItem)) {
            int quantity = cartItems.get(menuItem);
            if (quantity == 1) {
                cartItems.remove(menuItem);
            } else {
                cartItems.put(menuItem, quantity - 1);
            }
        }
    }

    public double getCartTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
        router.getCartPanel().updateUI();
    }

    public Map<MenuItem, Integer> getCartItems() {
        return cartItems;
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            router.displayErrorMessage("Cannot place an empty order.");
            return;
        }
        writer.println("PLACE_ORDER");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OrderRequest.class, new OrderRequestSerializer());
        Gson gson = gsonBuilder.create();
        String address = customer.getStreet() + ", " + customer.getCity();
        OrderRequest orderRequest = new OrderRequest(customer.getCustomerId(), address, cartItems);
        String json = gson.toJson(orderRequest);
        writer.println(json);

        // Simulate payment process
        simulatePayment();
    }

    private void simulatePayment() {
        // Simulate a delay for the payment process
        try {
            Thread.sleep(2000); // Simulate payment processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mock payment success
        boolean paymentSuccess = true;

        if (paymentSuccess) {
            writer.println("PAYMENT_SUCCESS");
        } else {
            writer.println("PAYMENT_FAILURE");
        }
    }

    public void logout() {
        navigateToLoginPage();
    }
}
