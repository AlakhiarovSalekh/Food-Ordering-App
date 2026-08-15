package GUI;

import javax.swing.*;

import Controller.Client;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

import Entities.Order;
import Entities.OrderItem;

public class HistoryPanel extends JPanel {
    private Client client;
    private List<Order> orders;
    private JPanel detailsPanel;
    private JScrollPane scrollPane;

    public HistoryPanel(Client client) {
        this.client = client;

        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(204, 204, 204));


        JLabel title = new JLabel("History");
        title.setBackground(new Color(204, 204, 204));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Segoe Script", Font.BOLD, 16));
        title.setOpaque(true);
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        JButton homeButton = new JButton("Home");
        JButton cartButton = new JButton("Cart");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");
        JButton reviewButton = new JButton("Leave a Review");

        logoutButton.addActionListener(e -> {
            client.logout();
        });

        cartButton.addActionListener(e -> {
            client.navigateToCartPage();
        });

        homeButton.addActionListener(e -> {
            client.navigateToHomePage();
        });

        profileButton.addActionListener(e -> {
            client.navigateToProfilePage();
        });

        reviewButton.addActionListener(e -> {
            client.navigateToReviewPage();
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(reviewButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        updateUI();
    }

    public void updateUI() {
        if (detailsPanel != null) {
            detailsPanel.removeAll();
            if (orders != null) {
                for (Order order : orders) {
                    JPanel orderPanel = createOrderPanel(order);
                    detailsPanel.add(orderPanel);
                    detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
            detailsPanel.revalidate();
            detailsPanel.repaint();
        }
    }

    private JPanel createOrderPanel(Order order) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setSize(380, 100);
        panel.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));
        panel.setBackground(new Color(204, 204, 204));

        JLabel orderTitle = new JLabel("Order ID: " + order.getOrderId());
        panel.add(orderTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel orderDateLabel = new JLabel("Order Date: " + dateFormat.format(order.getOrderDate()));
        panel.add(orderDateLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel totalAmountLabel = new JLabel("Total Amount: " + order.getTotalAmount());
        panel.add(totalAmountLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel totalPriceLabel = new JLabel("Total Price: " + order.getTotalPrice());
        panel.add(totalPriceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        List<OrderItem> items = order.getOrderItems();
        if (items != null) {
            JLabel itemsLabel = new JLabel("Items:");
            panel.add(itemsLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            for (OrderItem item : items) {
                JLabel itemLabel = new JLabel(item.getQuantity() + " x " + item.getItemName());
                panel.add(itemLabel);
            }
        }

        return panel;
    }

}
