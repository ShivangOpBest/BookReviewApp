package ui;

import models.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookPanel extends JPanel {
    private Book bookData;
    private MainFrame parentFrame;
    
    public BookPanel(Book bookData, MainFrame parentFrame) {
        this.bookData = bookData;
        this.parentFrame = parentFrame;
        
        setPreferredSize(new Dimension(200, 280));
        setBackground(Color.decode(bookData.getBookColor()));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel(bookData.getBookTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel authorLabel = new JLabel("by " + bookData.getAuthorName());
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(authorLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        add(centerPanel, BorderLayout.CENTER);
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.showBookDetails(bookData);
            }
            
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            }
            
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRect(20, 20, 60, 80);
        g2d.setColor(new Color(255, 255, 255, 30));
        g2d.fillRect(25, 25, 50, 70);
    }
}