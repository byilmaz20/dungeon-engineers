
package src.UI;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3PLAYER {
    private Player player;
    private String filePath;
   

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

    public void stop() {
        if (player != null) {
            player.close();
           
        }
    }
}
