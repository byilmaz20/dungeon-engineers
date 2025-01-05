package src.GameController;

import java.io.Serializable;

import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;
import src.UI.PlayModeScreen;

public class PlayModeController implements Serializable{
    
    Hall currentHall;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    SpawnMonsterController monsterSpawner;
    PlayModeScreen playModeScreen;
    TimeController timeController; // asıl time controller spawn monster ve enchnatmentı kontrol eder

    public PlayModeController(Hall hall) {
        this.currentHall = hall;
        this.grid = new GridEnvironment(currentHall);
        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
        this.timeController = new TimeController(grid);
        Timer timer = timeController.getTimer();
        this.playModeScreen = new PlayModeScreen(grid, timeController, timer.getRemainingTime());

    }
    public void disposeScreen() {
        playModeScreen.dispose();
    }
    public TimeController getTimeController() {
        return timeController;
    }

    public GridEnvironment getGridEnvironment() {
        return grid;
    }
    public PlayModeScreen getPlayModeScreen() {
        return playModeScreen;
    }


}
