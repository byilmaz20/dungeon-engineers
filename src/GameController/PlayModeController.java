package src.GameController;

import java.io.Serializable;
import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.GameObjects.Hero;
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
    TimeController timeController; // asıl time controller spawn monster ve enchnatmentı kontrol eder.
    public PlayModeController(Hall hall, Hero hero) {
        this.currentHall = hall;
        Hall testHall = hall;
        GridEnvironment testgrid = new GridEnvironment(testHall);
        hero.setPositon(testgrid.selectRandomLocation());
        // Instead of making a new Hero, reuse the one passed in:
        this.grid = new GridEnvironment(currentHall, hero);
    
        // Create a brand-new TimeController for the new hall (if that’s your intention)
        // Or if you want to preserve time across halls, you can pass an existing TimeController.
        TimeController timeController = new TimeController(grid);
        grid.setMainTimeController(timeController);
    
        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
    
        // Start the hall timer
        Timer timer = timeController.getTimer();
        timeController.startTimeController();
    
        // Build the play mode screen as usual
        this.playModeScreen = new PlayModeScreen(grid, timeController);
    }
    
    public PlayModeController(Hall hall) {
        this.currentHall = hall;
        
        this.grid = new GridEnvironment(currentHall);

        TimeController timeController = new TimeController(grid);
        grid.setMainTimeController(timeController);

        //grid.update(leveldata)

        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
        Timer timer = timeController.getTimer();
        timeController.startTimeController();
        this.playModeScreen = new PlayModeScreen(grid, timeController);

    }
    public PlayModeController(Hall hall, Hero hero, TimeController savedTimeController) {

        this.currentHall = hall;
        Hall testHall = hall;
        GridEnvironment testgrid = new GridEnvironment(testHall);
        hero.setPositon(testgrid.selectRandomLocation());
        this.grid = new GridEnvironment(currentHall, hero);
        grid.setMainTimeController(savedTimeController);
        //grid.update(leveldata)
        for (ITimeControllers tcr : hall.getTimeControllers()) {
            tcr.setGrid(grid);
            grid.addTimeController(tcr);
            
        }
        


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
