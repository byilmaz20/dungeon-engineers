package src.GameController;

import java.awt.GridBagConstraints;
import java.util.Random;
import src.GameObjects.Enchantment;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Rune;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.UI.PlayModeScreen;

public class PlayModeController {
    Hero hero;
    Rune rune;
    Hall currentHall;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    SpawnMonsterController monsterSpawner;
    PlayModeScreen playModeScreen;

    public PlayModeController(Hall hall) {
        this.currentHall = hall;
        this.playModeScreen = new PlayModeScreen(this.currentHall.hallType);
        this.hero = new Hero();
        
        Random random = new Random();
        int x = random.nextInt(25);
        int y = random.nextInt(25);
        PositionPoint runePosition = new PositionPoint(x, y);
        this.rune = new Rune(runePosition, currentHall);
        this.grid = new GridEnvironment(hero.getPosition(), rune.getPosition(), currentHall);
        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.activeEnchantment = null;
        this.isDoorOpen = false;
    }
    public void updatePlayModeScreen() {
        this.grid.moveEntity(this.hero);
        this.grid.moveEntity(this.rune);
    }
    public static void main(String[] args) {
        PlayModeController controller = new PlayModeController(new Hall(HallTypes.EARTH));
        controller.updatePlayModeScreen();
        controller.monsterSpawner.printGrid(controller.grid);
    }

}
