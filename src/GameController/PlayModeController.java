package src.GameController;

import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.Mechanics.GridEnvironment;
import src.UI.PlayModeScreen;

public class PlayModeController{
    
    Hall currentHall;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    SpawnMonsterController monsterSpawner;
    PlayModeScreen playModeScreen;
    TimeController timeController;

    public PlayModeController(Hall hall) {
        this.currentHall = hall;
        
        this.grid = new GridEnvironment(currentHall);
        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
        this.timeController = new TimeController(grid);
        this.playModeScreen = new PlayModeScreen(grid, timeController.getTimer());

    }
    public void disposeScreen() {
        playModeScreen.dispose();
    }


}
