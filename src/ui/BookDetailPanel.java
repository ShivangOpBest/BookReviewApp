package ui;

import models.Book;
import models.Review;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookDetailPanel extends JPanel {
    private Book currentBook;
    private MainFrame parentFrame;
    private JTextArea reviewInputArea;
    private JPanel reviewsDisplayPanel;
    private int selectedRating;
    private JButton[] starButtons;
    
    public BookDetailPanel(Book currentBook, MainFrame parentFrame) {
        this.currentBook = currentBook;
        this.parentFrame = parentFrame;
        this.selectedRating = 0;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton backButton = new JButton("← Back to Books");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.showMainView();
            }
        });
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(Color.WHITE);
        
        JPanel bookDisplayPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.decode(currentBook.getBookColor()));
                g.fillRect(0, 0, getWidth(), getHeight());
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g.getFontMetrics();
                String title = currentBook.getBookTitle();
                int titleWidth = fm.stringWidth(title);
                g.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 2 - 20);
                
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                String author = "by " + currentBook.getAuthorName();
                int authorWidth = g.getFontMetrics().stringWidth(author);
                g.drawString(author, (getWidth() - authorWidth) / 2, getHeight() / 2 + 10);
            }
        };
        bookDisplayPanel.setPreferredSize(new Dimension(300, 400));
        centerPanel.add(bookDisplayPanel);
        
        JPanel addReviewPanel = new JPanel();
        addReviewPanel.setLayout(new BoxLayout(addReviewPanel, BoxLayout.Y_AXIS));
        addReviewPanel.setBackground(Color.WHITE);
        
        JLabel ratingLabel = new JLabel("Your Rating:");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addReviewPanel.add(ratingLabel);
        addReviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        starsPanel.setBackground(Color.WHITE);
        starButtons = new JButton[5];
        
        for (int i = 0; i < 5; i++) {
            final int rating = i + 1;
            starButtons[i] = new JButton("★");
            starButtons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            starButtons[i].setForeground(Color.GRAY);
            starButtons[i].setBackground(Color.WHITE);
            starButtons[i].setBorderPainted(false);
            starButtons[i].setFocusPainted(false);
            starButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedRating = rating;
                    updateStarDisplay();
                }
            });
            starsPanel.add(starButtons[i]);
        }
        addReviewPanel.add(starsPanel);
        addReviewPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel reviewLabel = new JLabel("Write Your Review:");
        reviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addReviewPanel.add(reviewLabel);
        addReviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        reviewInputArea = new JTextArea(8, 30);
        reviewInputArea.setLineWrap(true);
        reviewInputArea.setWrapStyleWord(true);
        reviewInputArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reviewInputArea);
        addReviewPanel.add(scrollPane);
        addReviewPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton submitButton = new JButton("Submit Review");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(255, 140, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitReview();
            }
        });
        addReviewPanel.add(submitButton);
        
        centerPanel.add(addReviewPanel);
        add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JLabel allReviewsLabel = new JLabel("All Reviews");
        allReviewsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bottomPanel.add(allReviewsLabel, BorderLayout.NORTH);
        
        reviewsDisplayPanel = new JPanel();
        reviewsDisplayPanel.setLayout(new BoxLayout(reviewsDisplayPanel, BoxLayout.Y_AXIS));
        reviewsDisplayPanel.setBackground(Color.WHITE);
        
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsDisplayPanel);
        reviewsScrollPane.setPreferredSize(new Dimension(700, 200));
        bottomPanel.add(reviewsScrollPane, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        loadExistingReviews();
    }
    
    private void updateStarDisplay() {
        for (int i = 0; i < 5; i++) {
            if (i < selectedRating) {
                starButtons[i].setForeground(Color.ORANGE);
            } else {
                starButtons[i].setForeground(Color.GRAY);
            }
        }
    }
    
    private void submitReview() {
        String reviewText = reviewInputArea.getText().trim();
        
        if (reviewText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please write a review!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedRating == 0) {
            JOptionPane.showMessageDialog(this, "Please select a rating!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        
        Review newReview = new Review(reviewText, selectedRating, currentDate);
        parentFrame.addReview(currentBook.getBookId(), newReview);
        
        reviewInputArea.setText("");
        selectedRating = 0;
        updateStarDisplay();
        
        loadExistingReviews();
        
        JOptionPane.showMessageDialog(this, "Review added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loadExistingReviews() {
        reviewsDisplayPanel.removeAll();
        
        ArrayList<Review> bookReviews = parentFrame.getReviews(currentBook.getBookId());
        
        if (bookReviews == null || bookReviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews yet. Be the first to review!");
            noReviewsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noReviewsLabel.setForeground(Color.GRAY);
            reviewsDisplayPanel.add(noReviewsLabel);
        } else {
            for (int i = 0; i < bookReviews.size(); i++) {
                Review review = bookReviews.get(i);
                
                JPanel reviewPanel = new JPanel();
                reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
                reviewPanel.setBackground(new Color(245, 245, 245));
                reviewPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                reviewPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                
                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBackground(new Color(245, 245, 245));
                
                String starsText = "";
                for (int j = 0; j < review.getStarRating(); j++) {
                    starsText += "★";
                }
                JLabel starsLabel = new JLabel(starsText);
                starsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                starsLabel.setForeground(Color.ORANGE);
                
                JLabel dateLabel = new JLabel(review.getReviewDate());
                dateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                dateLabel.setForeground(Color.GRAY);
                
                headerPanel.add(starsLabel, BorderLayout.WEST);
                headerPanel.add(dateLabel, BorderLayout.EAST);
                
                JTextArea reviewTextArea = new JTextArea(review.getReviewText());
                reviewTextArea.setEditable(false);
                reviewTextArea.setLineWrap(true);
                reviewTextArea.setWrapStyleWord(true);
                reviewTextArea.setBackground(new Color(245, 245, 245));
                reviewTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
                
                reviewPanel.add(headerPanel);
                reviewPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                reviewPanel.add(reviewTextArea);
                
                reviewsDisplayPanel.add(reviewPanel);
                reviewsDisplayPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        reviewsDisplayPanel.revalidate();
        reviewsDisplayPanel.repaint();
    }
}