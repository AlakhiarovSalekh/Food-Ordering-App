package Routes;

import Controller.Client;
import GUI.*;

import javax.swing.*;
import java.awt.*;

public class Router {
    private Client client;
    private JFrame frame;

    // Define components for each page
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private HomePanel homePanel;
    private CartPanel cartPanel;
    private ProfilePanel profilePanel;
    private HistoryPanel historyPanel;
    private ReviewPanel reviewPanel;

    public Router(Client client) {
        this.client = client;

        // Initialize the GUI components for each page
        loginPanel = new LoginPanel(client);
        signupPanel = new SignupPanel(client);
        homePanel = new HomePanel(client);
        cartPanel = new CartPanel(client);
        profilePanel = new ProfilePanel(client);
        historyPanel = new HistoryPanel(client);
        reviewPanel = new ReviewPanel(client);

        frame = new JFrame("Food Ordering App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);

        frame.getContentPane().setLayout(new CardLayout());

        frame.getContentPane().add(loginPanel, "login");
        frame.getContentPane().add(signupPanel, "signup");
        frame.getContentPane().add(homePanel, "home");
        frame.getContentPane().add(cartPanel, "cart");
        frame.getContentPane().add(profilePanel, "profile");
        frame.getContentPane().add(historyPanel, "history");
        frame.getContentPane().add(reviewPanel, "review");

        showLoginPage();
        System.out.println("Router initialized");

        frame.setVisible(true);
    }

    // Methods to show each page
    public void showLoginPage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "login");
    }

    public void showSignupPage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "signup");
    }

    public void showHomePage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "home");
    }

    public void showCartPage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "cart");
        cartPanel.updateCartItems();
    }

    public void showHistoryPage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "history");
        historyPanel.updateUI();
    }

    public void showProfilePage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "profile");
    }

    public void showReviewPage() {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "review");
        reviewPanel.updateUI();
    }

    // Methods to display messages
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displayInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters for each page
    public HomePanel getHomePanel() {
        return homePanel;
    }
    public CartPanel getCartPanel() {
        return cartPanel;
    }
    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }
    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }
    public ReviewPanel getReviewPanel() {
        return reviewPanel;
    }

    public void handleCartResponse(String response) {
        cartPanel.hideLoadingDialog();
        if (response.equals("ORDER_PLACED")) {
            displayInfoMessage("Order placed successfully!");
            cartPanel.clearCart();
            showHomePage();
        } else if (response.equals("PAYMENT_FAILED")) {
            displayErrorMessage("Payment failed. Please try again.");
        } else {
            displayErrorMessage("Failed to place order. Please try again.");
        }
    }
}
