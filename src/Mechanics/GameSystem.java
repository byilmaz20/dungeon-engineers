package src.Mechanics;
import src.GameObjects.Monster;
import src.GameObjects.PlayerObject;
import src.GameObjects.Hall;
import java.util.List;
import java.util.ArrayList;

public class GameSystem {
    boolean  isGameRunning;
    List<Monster> monsters;
    PlayerObject player;
    Hall currentHall;
    public GameSystem() {
        isGameRunning = false;
        monsters = new ArrayList<Monster>();
        player = new PlayerObject();
        currentHall = new Hall();
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
