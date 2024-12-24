package src.GameController;

import java.util.List;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;

public class GameFlowController {
    private static final List<HallTypes> hallSequence = List.of(
        HallTypes.AIR, HallTypes.EARTH, HallTypes.FIRE, HallTypes.WATER
    );

    private static int currentHallIndex = 0; // Tracks the current hall in the sequence
    public static Hall currentHall;
    private static PlayModeController playModeController;

    private static boolean gameFinished = false; // Track if the game is finished

    public GameFlowController() {
        currentHall = BuildModeController.Halls.get("Hall of " + hallSequence.get(currentHallIndex));
        playModeController = new PlayModeController(currentHall);
    }

    public static void proceedNextHall() {
        if (playModeController != null) {
            playModeController.disposeScreen();
        }

        System.out.println(currentHall.hallType + " hall completed!");

        // Check if all halls are completed
        if (currentHallIndex == hallSequence.size() - 1) {
            finishGame();
            return;
        }

        // Move to the next hall
        currentHallIndex = (currentHallIndex + 1) % hallSequence.size();
        HallTypes nextHallType = hallSequence.get(currentHallIndex);
        System.out.println('\n' + "Entering the Hall of " + nextHallType + "...");
        
        currentHall = BuildModeController.Halls.get("Hall of " + nextHallType);
        playModeController = new PlayModeController(currentHall);
    }

    private static void finishGame() {
        gameFinished = true;
        System.out.println("\n All halls completed! Congratulations, you've finished the game! ");
        System.exit(0); 
    }
    private static void endGame() {
        gameFinished = true;
        System.out.println("\n Game Over! ");
        System.exit(0); 
    }
}
