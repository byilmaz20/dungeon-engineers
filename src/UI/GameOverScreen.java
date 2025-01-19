package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.Mechanics.SoundManager;


public class GameOverScreen extends UIScreen implements Serializable{
    private SoundManager buttonClickSound;
    private SoundManager gameOverSound;

    public GameOverScreen(String backgroundPath, String reason) {
        super(1200, 900, "Game Over");

        buttonClickSound = new SoundManager("src/voices/clickbutton.wav");
        gameOverSound = new SoundManager("src/voices/gameover.wav");

        JPanel customBackgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon(backgroundPath).getImage();
            

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        customBackgroundPanel.setLayout(new BorderLayout());
        customBackgroundPanel.setBounds(0, 0, 1200, 900);
        setContentPane(customBackgroundPanel);

        // "GAME OVER" başlığı
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Üst boşluk
        customBackgroundPanel.add(titleLabel, BorderLayout.NORTH);


        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setOpaque(false);

        JLabel messageLabel = new JLabel(reason, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Monospaced", Font.BOLD, 36)); 
        messageLabel.setForeground(Color.white);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0)); 
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setOpaque(false);
        messageLabel.setBackground(new Color(0, 0, 0)); // Set background to black for contrast

        messagePanel.add(messageLabel);


        customBackgroundPanel.add(messagePanel, BorderLayout.CENTER);


        // "Exit Game" button
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Serif", Font.BOLD, 25)); // Rustic-style font
        exitButton.setForeground(new Color(255, 250, 240)); // Off-white text (natural tone)
        exitButton.setBackground(new Color(101, 67, 33)); // Brownish color for a rustic feel
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> {
            buttonClickSound.playSound();
            System.out.println("Exit button clicked. Exiting game...");
            System.exit(0); // Close the game
        });
        // "Start New Game" button
        JButton newGameButton = new JButton("Start New Game");
        newGameButton.setFont(new Font("Serif", Font.BOLD, 25)); // Rustic-style font
        newGameButton.setForeground(new Color(255, 250, 240)); // Off-white text (natural tone)
        newGameButton.setBackground(new Color(34, 139, 34)); // Forest green for a natural look
        newGameButton.setFocusPainted(false);
        newGameButton.setOpaque(true);
        newGameButton.setBorderPainted(false);
        newGameButton.addActionListener(e -> {
            buttonClickSound.playSound();
            new MainMenuScreen();
            dispose(); // Close the GameOverScreen
            // Add your game restart logic here
        });
        // "Exit Game" button




        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false); // Transparent background
        bottomPanel.add(exitButton);
        bottomPanel.add(newGameButton);
        customBackgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
        
    
        gameOverSound.playSound(); 
  
        setVisible(true);
         
    }
}
