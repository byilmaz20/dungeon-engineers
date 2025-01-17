package src.GameController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import src.GameObjects.Enchantment;
import src.GameObjects.Entity;
import src.GameObjects.Hall;
import src.GameObjects.Hero;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
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
        PositionPoint randomPos = findRandomFreePosition(hall, 25, 25);
    hero.setPositon(randomPos);
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
        PositionPoint randomPos = findRandomFreePosition(hall, 25, 25);
    hero.setPositon(randomPos);
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
    public static PositionPoint findRandomFreePosition(Hall hall, int mapWidth, int mapHeight) {
        boolean[][] occupied = new boolean[mapWidth][mapHeight];

        // Mark all entity positions as occupied
        for (Entity entity : hall.getEntitys()) {
            int x = entity.position.x;
            int y = entity.position.y;
            if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                occupied[x][y] = true;
            }
        }

        // Collect all free positions
        List<PositionPoint> freePositions = new ArrayList<>();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (!occupied[x][y]) {
                    freePositions.add(new PositionPoint(x, y));
                }
            }
        }

        // If no free spots exist, handle accordingly
        if (freePositions.isEmpty()) {
            System.out.println("No available positions found.");
            return null;
        }

        // Pick a random free cell
        Random random = new Random();
        return freePositions.get(random.nextInt(freePositions.size()));
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
