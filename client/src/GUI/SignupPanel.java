package GUI;

import javax.swing.*;

import Controller.Client;

import java.awt.*;

public class SignupPanel extends JPanel {
    // Components for the login panel
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JTextField cityField;
    private JTextField streetField;
    private JButton loginButton;
    private JButton signupButton;

    public SignupPanel(Client client) {
        // Set the layout for the panel
        setLayout(new BorderLayout());
        setBackground(new Color(204, 204, 204));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("M&M");
        title.setFont(new Font("Segoe Script", Font.BOLD, 48));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setSize(400, 200);
        title.setBackground(new Color(204, 204, 204));
        add(title, BorderLayout.NORTH);

        // Initialize and configure the components for the login panel
        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        passwordField = new JPasswordField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        phoneField = new JTextField();
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        cityField = new JTextField();
        JLabel streetLabel = new JLabel("Street:");
        streetLabel.setFont(new Font("Segoe Script", Font.BOLD, 12));
        streetField = new JTextField();

        signupButton = new JButton("Sign up");
        signupButton.setFont(new Font("Segoe Script", Font.BOLD, 16));
        signupButton.setBackground(new Color(204, 204, 204));
        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String phone = phoneField.getText();
            String city = cityField.getText();
            String street = streetField.getText();
            if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || city.isEmpty() || street.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            client.sendSignupRequest(username, password, phone, city, street);
        });
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe Script", Font.BOLD, 16));
        loginButton.setBackground(new Color(204, 204, 204));
        loginButton.addActionListener(e -> {
            client.navigateToLoginPage();
        });

        // Add components to the panel
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setSize(300, 550);
        fieldsPanel.setBackground(new Color(204, 204, 204));
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 130)));
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(cityLabel);
        fieldsPanel.add(cityField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        fieldsPanel.add(streetLabel);
        fieldsPanel.add(streetField);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(loginButton);
        buttonsPanel.setBackground(new Color(204, 204, 204));
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonsPanel.add(signupButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
