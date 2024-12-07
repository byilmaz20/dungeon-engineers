package src.Mechanics;

public class GridEnvironment {
    PositionPoint heroPosition;
    PositionPoint runePosition;
    boolean isRuneFound;
    PositionPoint[][] map;

    public GridEnvironment(PositionPoint heroPosition, PositionPoint runePosition, boolean isRuneFound, PositionPoint[][] map) {
        this.heroPosition = heroPosition;
        this.runePosition = runePosition;
        this.isRuneFound = isRuneFound;
        this.map = map;
    }

    public void checkMovement(Direction direction) {
    }
    public void moveHero(Direction direction) {
    }
    public boolean isRuneFound() {
        return false;
    }
    public void updateGameState() {
    }

}
