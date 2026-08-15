package GUI;

import javax.swing.*;
import java.awt.*;

import Controller.Client;
import Entities.Customer;

public class ProfilePanel extends JPanel {
    private Client client;
    private Customer customer;
    private JPanel detailsPanel;
    private JScrollPane scrollPane;

    public ProfilePanel(Client client) {
        this.client = client;

        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(204, 204, 204));

        JLabel title = new JLabel("Profile");
        title.setBackground(new Color(204, 204, 204));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Segoe Script", Font.BOLD, 16));
        title.setOpaque(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        JButton homeButton = new JButton("Home");
        JButton cartButton = new JButton("Cart");
        JButton historyButton = new JButton("History");
        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(e -> {
            client.logout();
        });

        cartButton.addActionListener(e -> {
            client.navigateToCartPage();
        });

        homeButton.addActionListener(e -> {
            client.navigateToHomePage();
        });

        historyButton.addActionListener(e -> {
            client.navigateToHistoryPage();
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        updateUI();
    }

    public void updateUI() {
        if (detailsPanel != null) {
            detailsPanel.removeAll();
            detailsPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            detailsPanel.add(createCustomerDetailsPanel());
            detailsPanel.revalidate();
            detailsPanel.repaint();
        }
    }

    private JPanel createCustomerDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(204, 204, 204));
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.setAlignmentY(CENTER_ALIGNMENT);

        if (customer != null) {
            JLabel name = new JLabel("Name: " + customer.getName());
            name.setFont(new Font("Segoe Script", Font.PLAIN, 16));
            JLabel phone = new JLabel("Phone: " + customer.getPhone());
            phone.setFont(new Font("Segoe Script", Font.PLAIN, 16));
            JLabel address = new JLabel("Address: " + customer.getStreet() + ", " + customer.getCity());
            address.setFont(new Font("Segoe Script", Font.PLAIN, 16));

            panel.add(name);
            panel.add(phone);
            panel.add(address);
        }

        return panel;
    }
}
