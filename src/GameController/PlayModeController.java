package src.GameController;

import java.io.Serializable;
import java.sql.Time;

import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;
import src.UI.PlayModeScreen;
import src.GameObjects.Hero;

public class PlayModeController implements Serializable{
    
    Hall currentHall;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    SpawnMonsterController monsterSpawner;
    PlayModeScreen playModeScreen;
    TimeController timeController; // asıl time controller spawn monster ve enchnatmentı kontrol eder.

    public PlayModeController(Hall hall) {
        this.currentHall = hall;
        this.grid = new GridEnvironment(currentHall);

        timeController = new TimeController(grid);

        hall.setMainTimeController(timeController);
        grid.setMainTimeController(timeController);

        //grid.update(leveldata)

        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
        this.timeController = grid.getMainTimeController();
        Timer timer = timeController.getTimer();
        this.playModeScreen = new PlayModeScreen(grid, timeController);

    }
    public PlayModeController(Hall hall, Hero hero, TimeController currentMainTimeController) {
        this.currentHall = hall;
        this.grid = new GridEnvironment(currentHall, hero);
        //grid.update(leveldata)

        hall.setMainTimeController(currentMainTimeController);
        grid.setMainTimeController(currentMainTimeController);

        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
        this.timeController = grid.getMainTimeController();
        Timer timer = timeController.getTimer();
        this.playModeScreen = new PlayModeScreen(grid, timeController);

    }
    public GridEnvironment getGrid() {
        return grid;
    }
    public void disposeScreen() {
        playModeScreen.dispose();
    }
    public TimeController getTimeController() {
        return timeController;
    }

    public void openDoor() {
        isDoorOpen = true;
    }

    

    


}
