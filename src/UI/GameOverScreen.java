package src.UI;

import java.awt.*;
import javax.swing.*;

public class GameOverScreen extends UIScreen {

    public GameOverScreen(String reason) {
        super(1200, 900, "Game Over");

        JPanel customBackgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("src/Images/BackgroundImages/gameover.png").getImage();

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

        // "Exit Game" butonu
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Arial", Font.BOLD, 25));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(139, 0, 0)); // Koyu kırmızı
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            System.out.println("Exit button clicked. Exiting game...");
            System.exit(0); // Oyunu kapatır
        });

        // Buton için alt panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Şeffaf arka plan
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0)); // Alt boşluk
        buttonPanel.add(exitButton);

        customBackgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
