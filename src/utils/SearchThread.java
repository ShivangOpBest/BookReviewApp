package utils;

import models.Book;
import java.util.ArrayList;

public class SearchThread implements Runnable {
    private ArrayList<Book> allBooks;
    private String searchText;
    private ArrayList<Book> searchResults;
    
    public SearchThread(ArrayList<Book> allBooks, String searchText) {
        this.allBooks = allBooks;
        this.searchText = searchText.toLowerCase();
        this.searchResults = new ArrayList<Book>();
    }
    
    public void run() {
        for (int i = 0; i < allBooks.size(); i++) {
            Book currentBook = allBooks.get(i);
            String title = currentBook.getBookTitle().toLowerCase();
            String author = currentBook.getAuthorName().toLowerCase();
            
            if (title.contains(searchText) || author.contains(searchText)) {
                searchResults.add(currentBook);
            }
        }
    }
    
    public ArrayList<Book> getSearchResults() {
        return searchResults;
    }
}