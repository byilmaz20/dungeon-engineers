package src.GameObjects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import src.GameController.GameFlowController;

import java.io.IOException;

public class SaveGame implements Serializable{
    public static void saveGame(GameFlowController gameState, String saveName) {
        try {
            // Define the save directory and file path
            String saveDir = "Saves";
            File saveFolder = new File(saveDir);
            if (!saveFolder.exists()) saveFolder.mkdirs();

            // Save file path
            String filePath = saveDir + File.separator + saveName + ".dat";

            // Serialize the object to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(gameState);
                System.out.println("Game saved successfully at: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }
}
