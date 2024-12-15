package src.Mechanics;
import src.GameObjects.Monster;
import src.GameObjects.Hall;
import src.GameObjects.Hero;

import java.util.List;
import java.util.ArrayList;

public class GameSystem {
    boolean  isGameRunning;
    List<Monster> monsters;
    Hero hero;
    Hall currentHall;
    public GameSystem() {
        isGameRunning = false;
        monsters = new ArrayList<Monster>();
        hero = new Hero();
        currentHall = null;
    }
    public void runGame() {
    }
    public void verifyRandomLocation() {
    }
    public void updateHall() {
    }
    public void checkGameOver() {
    }
}
