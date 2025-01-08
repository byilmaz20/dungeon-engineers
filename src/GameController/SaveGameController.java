package src.GameController;

import java.sql.Time;

import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.Mechanics.GridEnvironment;
import src.UI.PlayModeScreen;

public class SaveGameController {

    private int currentHallIndex; // Tracks the current hall in the sequence
    Hall currentHall;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    SpawnMonsterController monsterSpawner;
    TimeController timeController; 

    public static GameFlowController gameState;
    public static void setGameFlowController(GameFlowController gmf){
        gameState = gmf;
    }
    public SaveGameController(){
        this.currentHall = gameState.getCurrentHall();
        this.currentHallIndex = gameState.getCurrentHallIndex();
    }
    public Hall getCurrentHall(){
        return this.currentHall;
    }
    public int getCurrentHallIndex(){
        return this.currentHallIndex;
    }
    public GridEnvironment getGridEnvironment(){
        return this.grid;
    }
   
    public TimeController getTimeController(){
        return this.timeController;
    }

}
