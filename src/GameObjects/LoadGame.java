package src.GameObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import src.GameController.GameFlowController;

public class LoadGame implements Serializable{
    public static Hall loadGame(String saveName) {
        System.out.println(saveName);
        try {
            // Define the save directory and file path
           
            String filePath = saveName;


            // Deserialize the object from the file
            try (FileInputStream fileIn = new FileInputStream(filePath);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    
                    Hall Hal = (Hall) in.readObject();
                    int currentHallIndex = (int) in.readObject();

                System.out.println("Game loaded successfully from: " + filePath);
                
                System.out.println(Hal.getHallTypes());
                System.out.println(currentHallIndex);

                return Hal;
            }
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
