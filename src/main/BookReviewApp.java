package main;

import ui.MainFrame;
import javax.swing.SwingUtilities;

public class BookReviewApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}