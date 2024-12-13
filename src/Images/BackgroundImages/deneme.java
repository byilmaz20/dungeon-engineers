package src.Images.BackgroundImages;
import javax.swing.*;
import java.awt.*;

public class deneme {

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("JFrame with Background Image");
        frame.setSize(800, 600); // Set the size of the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom JPanel with a background image
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("/Users/begumyilmaz/Documents/okul/koç/4.1/comp302/project/projectrepo/src/Images/BackgroundImages/mainMenuBackground.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image to fill the entire panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the layout and add components to the panel if needed
        backgroundPanel.setLayout(new BorderLayout());

        // Example: Add a button
        JButton button = new JButton("Click Me");
        backgroundPanel.add(button, BorderLayout.SOUTH);

        // Set the custom panel as the content pane
        frame.setContentPane(backgroundPanel);

        // Make the frame visible
        frame.setVisible(true);
    }
}