package ui;

import models.Book;
import models.Review;
import utils.FileHandler;
import utils.SearchThread;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private ArrayList<Book> allBooks;
    private ArrayList<Book> displayedBooks;
    private HashMap<Integer, ArrayList<Review>> allReviews;
    private FileHandler fileHandler;
    private JPanel mainPanel;
    private JPanel booksContainer;
    private JTextField searchField;
    private int currentPage;
    private int booksPerPage;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel pageLabel;
    
    public MainFrame() {
        this.allBooks = new ArrayList<Book>();
        this.displayedBooks = new ArrayList<Book>();
        this.allReviews = new HashMap<Integer, ArrayList<Review>>();
        this.fileHandler = new FileHandler();
        this.currentPage = 0;
        this.booksPerPage = 6;
        
        initializeBooks();
        loadReviewsFromFile();
        setupFrame();
        showMainView();
    }
    
    private void initializeBooks() {
        allBooks.add(new Book(1, "To Kill a Mockingbird", "Harper Lee", "#8B4513"));
        allBooks.add(new Book(2, "1984", "George Orwell", "#2F4F4F"));
        allBooks.add(new Book(3, "Pride and Prejudice", "Jane Austen", "#DDA0DD"));
        allBooks.add(new Book(4, "The Great Gatsby", "F. Scott Fitzgerald", "#FFD700"));
        allBooks.add(new Book(5, "Harry Potter", "J.K. Rowling", "#8B0000"));
        allBooks.add(new Book(6, "The Catcher in the Rye", "J.D. Salinger", "#CD5C5C"));
        allBooks.add(new Book(7, "The Hobbit", "J.R.R. Tolkien", "#228B22"));
        allBooks.add(new Book(8, "Brave New World", "Aldous Huxley", "#4682B4"));
        allBooks.add(new Book(9, "The Lord of the Rings", "J.R.R. Tolkien", "#8B4513"));
        allBooks.add(new Book(10, "Animal Farm", "George Orwell", "#DC143C"));
        allBooks.add(new Book(11, "The Chronicles of Narnia", "C.S. Lewis", "#4169E1"));
        allBooks.add(new Book(12, "Jane Eyre", "Charlotte Brontë", "#800080"));
        allBooks.add(new Book(13, "Wuthering Heights", "Emily Brontë", "#2F4F4F"));
        allBooks.add(new Book(14, "The Odyssey", "Homer", "#B8860B"));
        allBooks.add(new Book(15, "Crime and Punishment", "Fyodor Dostoevsky", "#8B0000"));
        allBooks.add(new Book(16, "The Divine Comedy", "Dante Alighieri", "#FF4500"));
        allBooks.add(new Book(17, "Moby Dick", "Herman Melville", "#191970"));
        allBooks.add(new Book(18, "War and Peace", "Leo Tolstoy", "#696969"));
        allBooks.add(new Book(19, "The Brothers Karamazov", "Fyodor Dostoevsky", "#8B4513"));
        allBooks.add(new Book(20, "Don Quixote", "Miguel de Cervantes", "#DAA520"));
        allBooks.add(new Book(21, "The Alchemist", "Paulo Coelho", "#CD853F"));
        allBooks.add(new Book(22, "One Hundred Years", "Gabriel García Márquez", "#556B2F"));
        allBooks.add(new Book(23, "Fahrenheit 451", "Ray Bradbury", "#FF6347"));
        allBooks.add(new Book(24, "The Little Prince", "Antoine de Saint-Exupéry", "#87CEEB"));
        allBooks.add(new Book(25, "Les Misérables", "Victor Hugo", "#483D8B"));
        
        displayedBooks.addAll(allBooks);
    }
    
    private void loadReviewsFromFile() {
        Thread loadThread = new Thread(new Runnable() {
            public void run() {
                allReviews = fileHandler.loadReviews();
            }
        });
        loadThread.start();
        try {
            loadThread.join();
        } catch (Exception e) {
            System.out.println("Error loading reviews");
        }
    }
    
    private void setupFrame() {
        setTitle("Book Review Application");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(255, 248, 220));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(mainPanel);
    }
    
    public void showMainView() {
        mainPanel.removeAll();
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(255, 248, 220));
        
        JLabel titleLabel = new JLabel("Book Review App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(139, 69, 19));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(255, 248, 220));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });
        
        JLabel searchLabel = new JLabel("🔍");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        booksContainer = new JPanel(new GridLayout(2, 3, 15, 15));
        booksContainer.setBackground(new Color(255, 248, 220));
        
        JScrollPane scrollPane = new JScrollPane(booksContainer);
        scrollPane.setBackground(new Color(255, 248, 220));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(255, 248, 220));
        
        previousButton = new JButton("← Previous");
        previousButton.setFont(new Font("Arial", Font.BOLD, 14));
        previousButton.setBackground(new Color(255, 140, 0));
        previousButton.setForeground(Color.WHITE);
        previousButton.setFocusPainted(false);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    updateBooksDisplay();
                }
            }
        });
        
        pageLabel = new JLabel();
        pageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(255, 140, 0));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalPages = (int) Math.ceil((double) displayedBooks.size() / booksPerPage);
                if (currentPage < totalPages - 1) {
                    currentPage++;
                    updateBooksDisplay();
                }
            }
        });
        
        bottomPanel.add(previousButton);
        bottomPanel.add(pageLabel);
        bottomPanel.add(nextButton);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        updateBooksDisplay();
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void updateBooksDisplay() {
        booksContainer.removeAll();
        
        int startIndex = currentPage * booksPerPage;
        int endIndex = Math.min(startIndex + booksPerPage, displayedBooks.size());
        
        for (int i = startIndex; i < endIndex; i++) {
            Book book = displayedBooks.get(i);
            BookPanel bookPanel = new BookPanel(book, this);
            booksContainer.add(bookPanel);
        }
        
        int totalPages = (int) Math.ceil((double) displayedBooks.size() / booksPerPage);
        pageLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
        
        previousButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);
        
        booksContainer.revalidate();
        booksContainer.repaint();
    }
    
    private void performSearch() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            displayedBooks.clear();
            displayedBooks.addAll(allBooks);
            currentPage = 0;
            updateBooksDisplay();
            return;
        }
        
        SearchThread searchThread = new SearchThread(allBooks, searchText);
        Thread thread = new Thread(searchThread);
        thread.start();
        
        try {
            thread.join();
            displayedBooks.clear();
            displayedBooks.addAll(searchThread.getSearchResults());
            currentPage = 0;
            updateBooksDisplay();
        } catch (Exception e) {
            System.out.println("Search error");
        }
    }
    
    public void showBookDetails(Book book) {
        mainPanel.removeAll();
        BookDetailPanel detailPanel = new BookDetailPanel(book, this);
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    public void addReview(int bookId, Review review) {
        if (!allReviews.containsKey(bookId)) {
            allReviews.put(bookId, new ArrayList<Review>());
        }
        allReviews.get(bookId).add(review);
        
        Thread saveThread = new Thread(new Runnable() {
            public void run() {
                fileHandler.saveReviews(allReviews);
            }
        });
        saveThread.start();
    }
    
    public ArrayList<Review> getReviews(int bookId) {
        return allReviews.get(bookId);
    }
}