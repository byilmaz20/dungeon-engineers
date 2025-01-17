package src.GameController;

import java.io.Serializable;
import java.util.List;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.Mechanics.SoundManager;
import src.UI.GameOverScreen;

public class GameFlowController implements Serializable{
    private static final List<HallTypes> hallSequence = List.of(
        HallTypes.AIR, HallTypes.EARTH, HallTypes.FIRE, HallTypes.WATER
    );

        private static SoundManager winnerSound;
        private static int currentHallIndex = 0; // Tracks the current hall in the sequence
        public static Hall currentHall;
        private static PlayModeController playModeController;
    
        private static boolean gameFinished = false; // Track if the game is finished
    
        public GameFlowController() {
            
            currentHall = BuildModeController.Halls.get("Hall of " + hallSequence.get(currentHallIndex));
            playModeController = new PlayModeController(currentHall);
            winnerSound = new SoundManager("src/voices/winnersound.wav");
        }
    
        public GameFlowController(int currentHallIndex, Hall savedCurrentHall, Hero savedHero, TimeController savedTimeController) {
            GameFlowController.currentHall = savedCurrentHall;
            GameFlowController.currentHallIndex = currentHallIndex;
    
            playModeController = new PlayModeController(GameFlowController.currentHall, savedHero, savedTimeController);
    
        }
        
        public static void proceedNextHall() {
            if (playModeController != null) {
                // Dispose old screen, stop timers, etc.
                playModeController.disposeScreen();
                for (ITimeControllers timeController : playModeController.getGrid().getTimeControllers()) {
                    timeController.getTimer().pauseTimer();
                }
            }
        
            System.out.println(currentHall.hallType + " hall completed!");
        
            // If we finished the last hall, end the game
            if (currentHallIndex == hallSequence.size() - 1) {
                winnerSound.playSound();
                endGame("src/Images/BackgroundImages/gameoverwin.png", " ");
                return;
            }
        
            // Grab the existing Hero from the old hall's grid
            Hero existingHero = playModeController.getGrid().getHero();
            // This ensures we keep the same hero object with same lives, inventory, etc.
        
            // Move to the next hall
            currentHallIndex = (currentHallIndex + 1) % hallSequence.size();
            HallTypes nextHallType = hallSequence.get(currentHallIndex);
            System.out.println("\nEntering the Hall of " + nextHallType + "...");
        
            // Retrieve the new Hall
            currentHall = BuildModeController.Halls.get("Hall of " + nextHallType);
        
            // Instead of PlayModeController(currentHall), reuse the hero
            playModeController = new PlayModeController(currentHall, existingHero);
        }
        

    public static void endGame(String backgroundPath,String reason) {
        gameFinished = true;
        new GameOverScreen(backgroundPath, reason);
        System.out.println("\n!Game Over! ");
        
    }
    
    public int getCurrentHallIndex(){
        return currentHallIndex;
    }
    public Hall getCurrentHall(){
        return currentHall;
    }

    public PlayModeController getPlayModeController(){
        return playModeController;
    }

}


