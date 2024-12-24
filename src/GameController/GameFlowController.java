package src.GameController;

public class GameFlowController {
    public GameFlowController() {
        //for (Map.Entry<String, Hall> entry : BuildModeController.Halls.entrySet()) {
          //  Hall hall = entry.getValue();
            //PlayModeController playModeController = new PlayModeController(hall);
        //}
        PlayModeController playModeController = new PlayModeController(BuildModeController.Halls.get("Hall of Earth"));
    }
    
}
