package src.GameController;

import java.io.Serializable;
import java.sql.Time;

import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.Mechanics.GridEnvironment;
import src.UI.PlayModeScreen;

public class SaveGameController implements Serializable{

    private int currentHallIndex; // Tracks the current hall in the sequence
    Hall currentHall;
    PlayModeController playModeController;
    public static GameFlowController gameState;

    public static void setGameFlowController(GameFlowController gmf){
        gameState = gmf;
    }
    public SaveGameController(){
        this.currentHall = gameState.getCurrentHall();
        this.currentHallIndex = gameState.getCurrentHallIndex();
        this.playModeController = gameState.getPlayModeController();
    }
    public Hall getCurrentHall(){
        return this.currentHall;
    }
    public int getCurrentHallIndex(){
        return this.currentHallIndex;
    }

    public GameFlowController getGameFlowController(){
        return gameState;
    }
    
    public PlayModeController getPlayModeController(){
        return this.playModeController;
    }

}
