package src.GameController;
public class GameModeController {
    private static GameModeController instance;
    private String gameMode; // "easy" or "hard"

    public GameModeController() {
    }

    public static GameModeController getInstance() {
        if (instance == null) {
            instance = new GameModeController();
        }
        return instance;
    }

    public void setGameMode(String mode) {
        this.gameMode = mode;
    }

    public String getGameMode() {
        return gameMode;
    }
    
}
