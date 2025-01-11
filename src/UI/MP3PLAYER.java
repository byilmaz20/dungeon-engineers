
package src.UI;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3PLAYER {
    private Player player;

    public void play(String filePath) {
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

    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}
