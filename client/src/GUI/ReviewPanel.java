package GUI;

import Controller.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ReviewPanel extends JPanel {
    private Client client;
    private JTextArea reviewTextArea;
    private JComboBox<Integer> ratingComboBox;
    private JButton submitButton;
    private JPanel reviewsPanel;

    public ReviewPanel(Client client) {
        this.client = client;
        setLayout(new BorderLayout());

        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(reviewsPanel), BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        reviewTextArea = new JTextArea(3, 20);
        submitPanel.add(new JScrollPane(reviewTextArea));

        ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        submitPanel.add(ratingComboBox);

        submitButton = new JButton("Submit Review");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderId = getSelectedOrderId(); // Implement this method to get the selected order ID
                int rating = (int) ratingComboBox.getSelectedItem();
                String review = reviewTextArea.getText();
                client.sendSaveReviewRequest(orderId, rating, review);
            }
        });
        submitPanel.add(submitButton);

        add(submitPanel, BorderLayout.SOUTH);
    }

    public void setReviews(List<Map<String, Object>> reviews) {
        reviewsPanel.removeAll();
        for (Map<String, Object> review : reviews) {
            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BorderLayout());
            reviewPanel.add(new JLabel("Order ID: " + review.get("orderId")), BorderLayout.NORTH);
            reviewPanel.add(new JLabel("Rating: " + review.get("rating")), BorderLayout.CENTER);
            reviewPanel.add(new JLabel("Review: " + review.get("review")), BorderLayout.SOUTH);
            reviewsPanel.add(reviewPanel);
        }
    }

    public void updateUI() {
        super.updateUI();
        revalidate();
        repaint();
    }

    private int getSelectedOrderId() {
        // Implement this method to get the selected order ID from the UI
        return 0;
    }
}
