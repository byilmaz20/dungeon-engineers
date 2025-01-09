package src.Mechanics;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip clip;

    // Constructor: Ses dosyasını yükle
    public SoundManager(String filePath) {
        try {
            File soundFile = new File(filePath); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("there is a mostake in voice folder taken: " + e.getMessage());
        }
    }

    // Sesi çal
    public void playSound() {
        if (clip != null) {
            clip.setFramePosition(0); // Sesin başından çal
            clip.start();
        }
    }

    // Sesi durdur
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
