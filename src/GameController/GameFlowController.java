package src.GameController;

import java.util.Map;
import src.GameObjects.Hall; 

public class GameFlowController {
    public GameFlowController() {
        for (Map.Entry<String, Hall> entry : BuildModeController.Halls.entrySet()) {
            Hall hall = entry.getValue();
            PlayModeController playModeController = new PlayModeController(hall);
        }
    }
    
}
