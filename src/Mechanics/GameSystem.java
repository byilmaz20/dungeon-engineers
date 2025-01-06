package src.Mechanics;
import src.GameObjects.Monster;
import src.GameObjects.Hall;
import src.GameObjects.Hero;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class GameSystem implements Serializable{
    boolean  isGameRunning;
    List<Monster> monsters;
    Hero hero;
    Hall currentHall;
    public GameSystem() {
        isGameRunning = false;
        monsters = new ArrayList<Monster>();
        hero = new Hero(null, currentHall);
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
