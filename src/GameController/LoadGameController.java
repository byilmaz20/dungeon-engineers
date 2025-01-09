package src.GameController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

import javax.swing.JOptionPane;

import src.GameController.GameFlowController;
import src.GameObjects.Hall;
import src.UI.LoadGameScreen;

public class LoadGameController implements Serializable{
    public static void loadGame(String saveName) {
        System.out.println(saveName);
        try {
            // Define the save directory and file path
           
            String filePath = saveName;


            // Deserialize the object from the file
            try (FileInputStream fileIn = new FileInputStream(filePath);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    
                    Hall hall = (Hall) in.readObject();
                    int currentHallIndex = (int) in.readObject();
                    Map halls = (Map) in.readObject();

                System.out.println("Game loaded successfully from: " + filePath);
                
                System.out.println(hall.getHallTypes());
                System.out.println(currentHallIndex);

                if (hall != null) {
                    JOptionPane.showMessageDialog(null, "Game Loaded! Player: " + hall.getHallTypes());
                    // Start the loaded game or transition to the game screen
                    BuildModeController.Halls = halls;
                    new GameFlowController(currentHallIndex, hall);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to load the game file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            
        }
    }
}
