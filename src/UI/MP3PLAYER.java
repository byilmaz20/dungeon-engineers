
package src.UI;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3PLAYER {
    private Player player;
    private String filePath;



    // Constructor: Dosya yolunu alır
    public  MP3PLAYER(String filePath) {
        this.filePath = filePath;
    }

    // MP3 çalma metodu
    public void playSound() {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            player = new Player(fis);
            Thread playThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            playThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MP3 durdurma metodu
    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}
