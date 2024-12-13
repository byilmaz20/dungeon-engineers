package src.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class UIScreen extends JFrame{

    public JPanel backgroundPanel;
    private final int width;
    private final int height;
    private String backgroundPath;

    public UIScreen(int width, int height, String title, String backgroundPath){
        super("ROKUE-LIKE DUNGEON ENGINEERS");
        this.width = width;
        this.height = height;
        this.backgroundPath = backgroundPath;
    }

    public void setBackgroundImage() {
    	
    	this.setResizable(false);
    	this.setTitle("Dungeon Engineers ROKUE-LIKE");
    	this.setSize(width, height);
    	this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.getContentPane().setLayout(null);
    	

    	backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("/Users/begumyilmaz/Documents/okul/koç/4.1/comp302/project/projectrepo/src/Images/BackgroundImages/mainMenuBackground.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image to fill the entire panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setBounds(0, 0, width, height);
        backgroundPanel.setLocation(0, 0);
        backgroundPanel.setSize(new Dimension(width, height));
        backgroundPanel.setLayout(null);
        backgroundPanel.setOpaque(false);
        setContentPane(backgroundPanel);        }
    
    
}
