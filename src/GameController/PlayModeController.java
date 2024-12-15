package src.GameController;

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

    public PlayModeController(Hall hall) {
        this.grid = new GridEnvironment(hero.getPosition(), rune.getPosition(), currentHall);
        this.monsterSpawner = new SpawnMonsterController(grid);
        monsterSpawner.spawnMonster();
        this.hero = new Hero();
        this.currentHall = hall;
        Random random = new Random();
        int x = random.nextInt(25);
        int y = random.nextInt(25);
        PositionPoint runePosition = new PositionPoint(x, y);
        this.rune = new Rune(runePosition, currentHall);
        this.activeEnchantment = null;
        this.isDoorOpen = false;
    }
    public void updateScreen() {
        
    }
    public static void main(String[] args) {
        new PlayModeScreen(HallTypes.EARTH);
    }

}
