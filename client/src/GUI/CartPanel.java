package GUI;

import Controller.Client;
import Entities.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.text.DecimalFormat;

public class CartPanel extends JPanel {
    private Client client;
    private JPanel footerPanel;
    private JButton homeButton;
    private JButton historyButton;
    private JButton profileButton;
    private JButton checkoutButton;
    private JDialog loadingDialog;

    public CartPanel(Client client) {
        this.client = client;

        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Cart");
        titleLabel.setFont(new Font("Segoe Script", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JLabel emptyLabel = new JLabel("Your cart is empty.");
        emptyLabel.setFont(new Font("Segoe Script", Font.PLAIN, 14));
        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        itemsPanel.add(emptyLabel);

        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create the buttons
        homeButton = new JButton("Home");
        historyButton = new JButton("History");
        profileButton = new JButton("Profile");
        checkoutButton = new JButton("Checkout");

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.navigateToHomePage();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.navigateToHistoryPage();
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.navigateToProfilePage();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to checkout?", "Checkout",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    showLoadingDialog();
                    client.checkout();
                }
            }
        });

        footerPanel.add(homeButton);
        footerPanel.add(historyButton);
        footerPanel.add(profileButton);
        footerPanel.add(checkoutButton);

        add(footerPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(300, 400));
    }

    public void addItem(MenuItem menuItem) {
        client.addToCart(menuItem);
        updateCartItems();
    }

    public void removeItem(MenuItem menuItem) {
        client.removeFromCart(menuItem);
        updateCartItems();
    }

    public void clearCart() {
        client.clearCart();
        updateCartItems();
    }

    public void updateCartItems() {
        removeAll();

        setLayout(new BorderLayout());
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Cart");
        titleLabel.setFont(new Font("Segoe Script", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(204, 204, 204));

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(340, 550));
        add(scrollPane, BorderLayout.CENTER);

        Map<MenuItem, Integer> cartItems = client.getCartItems();

        if (cartItems.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your cart is empty.");
            emptyLabel.setFont(new Font("Segoe Script", Font.PLAIN, 14));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsPanel.add(emptyLabel);
        } else {
            // Total label
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            JLabel totalLabel = new JLabel("Total: $" + decimalFormat.format(client.getCartTotal()));
            totalLabel.setFont(new Font("Segoe Script", Font.PLAIN, 14));
            itemsPanel.add(totalLabel);
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel itemsLabel = new JLabel("Items:");
            itemsLabel.setFont(new Font("Segoe Script", Font.PLAIN, 14));
            itemsPanel.add(itemsLabel);
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
            sep.setForeground(Color.BLACK);
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            itemsPanel.add(sep);
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
                MenuItem menuItem = entry.getKey();
                int quantity = entry.getValue();

                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
                itemPanel.setBackground(new Color(204, 204, 204));
                itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                itemPanel.setPreferredSize(new Dimension(340, 50));
                itemPanel.setMaximumSize(new Dimension(340, 50));

                // Quantity label
                JLabel quantityLabel = new JLabel("Quantity: " + quantity);
                quantityLabel.setFont(new Font("Segoe Script", Font.PLAIN, 10));
                itemPanel.add(quantityLabel, BorderLayout.WEST);

                // Decrease button
                JButton decreaseButton = new JButton("-");

                decreaseButton.setFont(new Font("Segoe Script", Font.BOLD, 10));
                decreaseButton.setBackground(new Color(204, 204, 204));
                decreaseButton.addActionListener(e -> {
                    client.removeFromCart(menuItem);
                    updateCartItems();
                });

                // Increase button
                JButton increaseButton = new JButton("+");
                increaseButton.setFont(new Font("Segoe Script", Font.BOLD, 14));
                increaseButton.setBackground(new Color(204, 204, 204));
                increaseButton.addActionListener(e -> {
                    client.addToCart(menuItem);
                    updateCartItems();
                });

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setBackground(new Color(204, 204, 204));
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
                buttonsPanel.add(decreaseButton);
                buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                buttonsPanel.add(increaseButton);

                itemPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.CENTER);
                itemPanel.add(buttonsPanel, BorderLayout.EAST);

                // Item label
                JLabel itemLabel = new JLabel(menuItem.getName() + " - $" + menuItem.getPrice());
                itemLabel.setFont(new Font("Segoe Script", Font.PLAIN, 14));
                itemPanel.add(itemLabel, BorderLayout.NORTH);

                itemsPanel.add(itemPanel);
                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                separator.setForeground(Color.BLACK);

                itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                itemsPanel.add(separator);
                itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        add(footerPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void showLoadingDialog() {
        loadingDialog = new JDialog((Frame) null, "Processing Payment", true);
        JLabel loadingLabel = new JLabel("Processing payment, please wait...");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingDialog.add(loadingLabel);
        loadingDialog.setSize(300, 100);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setVisible(true);
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }
}
