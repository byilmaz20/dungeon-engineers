
package src.UI;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.IOException;

public class MP3PLAYER {
    private Player player;
    private String filePath;
    private boolean isLooping = false;
    private Thread playThread;

    public  MP3PLAYER(String filePath) {
        this.filePath = filePath;
    }

    public void playSound() {

        try (FileInputStream fis = new FileInputStream(filePath)) {
            player = new Player(fis);
            Thread playThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                }
            });
            playThread.start();
        } catch (Exception e) {
        }
    }

    public void playBackgroundMusic(boolean loop) {
        isLooping = loop;

        // Daha önce çalışan bir thread varsa durdur
        if (playThread != null && playThread.isAlive()) {
            stop();
        }

        playThread = new Thread(() -> {
            try {
                do {
                    try (FileInputStream fis = new FileInputStream(filePath)) {
                        player = new Player(fis);
                        player.play(); // Müziği çal
                    }
                } while (isLooping); // Döngü durumuna göre tekrarla
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        playThread.start(); // Yeni thread başlat
    }

    public void stop() {
        if (player != null) {
            player.close();
           
        }
    }
}
