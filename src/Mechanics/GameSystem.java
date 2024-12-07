package src.Mechanics;

public class GameSystem {
    boolean isGameRunning;
    List<Monster> monsters;
    Player player;
    Hall currentHall;
    public GameSystem() {
        isGameRunning = false;
        monsters = new ArrayList<Monster>();
        player = new Player();
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
