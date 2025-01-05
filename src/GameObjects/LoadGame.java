package src.GameObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import src.GameController.GameFlowController;

public class LoadGame implements Serializable{
    public static GameFlowController loadGame(String saveName) {
        try {
            // Define the save directory and file path
            String saveDir = "Saves";
            String filePath = saveDir + File.separator + saveName + ".dat";

            // Deserialize the object from the file
            try (FileInputStream fileIn = new FileInputStream(filePath);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    GameFlowController gameState = (GameFlowController) in.readObject();
                System.out.println("Game loaded successfully from: " + filePath);
                return gameState;
            }
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
