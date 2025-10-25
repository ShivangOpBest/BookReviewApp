package models;

import java.io.Serializable;

public class Review implements Serializable {
    private String reviewText;
    private int starRating;
    private String reviewDate;
    
    public Review(String reviewText, int starRating, String reviewDate) {
        this.reviewText = reviewText;
        this.starRating = starRating;
        this.reviewDate = reviewDate;
    }
    
    public String getReviewText() {
        return reviewText;
    }
    
    public int getStarRating() {
        return starRating;
    }
    
    public String getReviewDate() {
        return reviewDate;
    }
}