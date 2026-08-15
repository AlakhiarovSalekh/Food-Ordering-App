package GUI;

import javax.swing.*;
import java.awt.*;

import Controller.Client;
import Entities.Menu;
import Entities.MenuItem;

public class HomePanel extends JPanel {
    private Menu menu;
    private Client client;
    private JPanel menuPanel;
    private JScrollPane scrollPane;

    public HomePanel(Client client) {
        this.client = client;

        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setSize(300, 550);
        menuPanel.setBackground(new Color(204, 204, 204));
        JLabel title = new JLabel("Home");
        title.setFont(new Font("Segoe Script", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        JButton cartButton = new JButton("Cart");
        JButton historyButton = new JButton("History");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(e -> {
            client.logout();
        });

        cartButton.addActionListener(e -> {
            client.navigateToCartPage();
        });

        profileButton.addActionListener(e -> {
            client.navigateToProfilePage();
        });

        historyButton.addActionListener(e -> {
            client.navigateToHistoryPage();
        });

        buttonPanel.add(cartButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(menuPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setMenu(Menu menu) {
        this.menu = menu;

        for (MenuItem menuItem : menu.getMenuItems()) {
            JPanel itemPanel = createMenuItemPanel(menuItem);
            menuPanel.add(itemPanel);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    public Menu getMenu() {
        return menu;
    }

    private JPanel createMenuItemPanel(MenuItem menuItem) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(340, 100);
        panel.setMaximumSize(new Dimension(300, 120));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        panel.setBackground(new Color(204, 204, 204));

        JLabel nameLabel = new JLabel(menuItem.getName());
        nameLabel.setFont(new Font("Segoe Script", Font.BOLD, 16));
        JLabel descriptionLabel = new JLabel(menuItem.getDescription());
        descriptionLabel.setFont(new Font("Poor Richard", Font.PLAIN, 14));
        JLabel priceLabel = new JLabel("Price: $" + menuItem.getPrice());
        priceLabel.setFont(new Font("Poor Richard", Font.PLAIN, 14));

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Segoe Script", Font.BOLD, 12));
        addToCartButton.setBackground(new Color(204, 204, 204));

        addToCartButton.addActionListener(e -> {
            client.addToCart(menuItem);
        });

        panel.add(nameLabel);
        panel.add(descriptionLabel);
        panel.add(priceLabel);
        panel.add(addToCartButton);

        return panel;
    }
}
