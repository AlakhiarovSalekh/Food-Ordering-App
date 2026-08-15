package GUI;

import Controller.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class represents the login page of the application.
 * It is responsible for displaying the login form and handling user input.
 */

public class LoginPanel extends JPanel {
    // Components for the login panel
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginPanel(Client client) {
        // Set the layout for the panel
        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("M&M");
        title.setFont(new Font("Segoe Script", Font.BOLD, 48));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setSize(400, 200);
        add(title, BorderLayout.NORTH);

        // Initialize and configure the components for the login panel
        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe Script", Font.BOLD, 16));
        loginButton.setBackground(new Color(204, 204, 204));
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                client.sendLoginRequest(username, password);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        signupButton = new JButton("Sign up");
        signupButton.setFont(new Font("Segoe Script", Font.BOLD, 16));
        signupButton.setBackground(new Color(204, 204, 204));
        signupButton.addActionListener(e -> {
            client.navigateToSignupPage();
        });

        // Add components to the panel
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setSize(300, 550);
        fieldsPanel.setBackground(new Color(204, 204, 204));
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 230)));
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(204, 204, 204));
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(signupButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonsPanel.add(loginButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
