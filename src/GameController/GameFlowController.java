package src.GameController;

import java.util.Map;
import src.GameObjects.Hall; 

public class GameFlowController {
    public static void main(String[] args) {
        System.out.println("");
        for (Map.Entry<String, Hall> entry : BuildModeController.Halls.entrySet()) {
            
            Hall hall = entry.getValue();
            System.out.println(hall);
            PlayModeController playModeController = new PlayModeController(hall);


        }

    }
}
