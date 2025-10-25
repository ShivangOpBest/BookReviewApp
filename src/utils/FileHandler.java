package utils;

import models.Review;
import java.io.*;
import java.util.*;

public class FileHandler {
    private String fileName;
    
    public FileHandler() {
        this.fileName = "books.txt";
    }
    
    public void saveReviews(HashMap<Integer, ArrayList<Review>> allReviews) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(fileName);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(allReviews);
            objectOutput.close();
            fileOutput.close();
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    
    public HashMap<Integer, ArrayList<Review>> loadReviews() {
        try {
            FileInputStream fileInput = new FileInputStream(fileName);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            HashMap<Integer, ArrayList<Review>> loadedReviews = (HashMap<Integer, ArrayList<Review>>) objectInput.readObject();
            objectInput.close();
            fileInput.close();
            return loadedReviews;
        } catch (Exception e) {
            return new HashMap<Integer, ArrayList<Review>>();
        }
    }
}