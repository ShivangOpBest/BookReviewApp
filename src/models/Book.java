package models;

import java.io.Serializable;

public class Book implements Serializable {
    private int bookId;
    private String bookTitle;
    private String authorName;
    private String bookColor;
    
    public Book(int bookId, String bookTitle, String authorName, String bookColor) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookColor = bookColor;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public String getBookColor() {
        return bookColor;
    }
}