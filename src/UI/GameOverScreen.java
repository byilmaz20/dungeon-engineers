package src.UI;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends UIScreen {

    public GameOverScreen(String reason) {
        super(800, 630, "Game Over", "src/Images/BackgroundImages/gameover.png"); // Daha kompakt yükseklik

        // Ana düzen
        setSize(800, 630); // Ekranın yüksekliği biraz azaltıldı
        setLayout(null); // Null layout ile manuel yerleşim sağlanır.

        // "GAME OVER" başlığı
        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Daha büyük font
        titleLabel.setForeground(Color.RED); // Kırmızı renk
        titleLabel.setBounds(0, 10, 800, 60); // Üstte ortalanmış
        add(titleLabel);

        // "Reason" mesajı
        JLabel messageLabel = new JLabel(reason, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Daha küçük font
        messageLabel.setForeground(Color.RED); // Kırmızı renk
        messageLabel.setBounds(0, 70, 800, 40); // Başlığın hemen altında
        add(messageLabel);

        // Arka plan görseli
        JLabel background = new JLabel(new ImageIcon(
                new ImageIcon("src/Images/BackgroundImages/gameover.png")
                        .getImage()
                        .getScaledInstance(800, 500, Image.SCALE_SMOOTH))); // Görselin yüksekliği küçültüldü
        background.setBounds(0, 110, 800, 500); // Görsel, yazıların altına hizalandı
        add(background); // Görseli ekledik

        // "Exit Game" butonu
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE); // Beyaz yazı
        exitButton.setBackground(new Color(139, 0, 0)); // Koyu kırmızı arka plan
        exitButton.setFocusPainted(false); // Buton odak efekti kaldırıldı
        exitButton.setBounds(250, 540, 300, 40); // Buton birkaç cm yukarı kaldırıldı
        exitButton.addActionListener(e -> {
            System.out.println("Exit button clicked. Exiting game...");
            System.exit(0); // Butona basıldığında oyun kapanır
        });
        add(exitButton); // Butonu ekledik

        setVisible(true);
    }
}
