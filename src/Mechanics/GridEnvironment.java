package src.Mechanics;

import src.GameObjects.Entity;

public class GridEnvironment {
    PositionPoint heroPosition;
    PositionPoint runePosition;
    Hall hall;
    boolean  isRuneFound;
    PositionPoint[][] map;
    int mapWidth;
    int mapHeight;


    public GridEnvironment(PositionPoint heroPosition, PositionPoint runePosition, Hall hall) {
        this.heroPosition = heroPosition;
        this.runePosition = runePosition;
        this.map = new PositionPoint[mapWidth][mapHeight];
        this.mapWidth = map.length;
    }

    public void checkMovement(Direction direction) {
        
    }
    public void moveHero(Direction direction) {

    }
    public boolean  isRuneFound() {
        return false;
    }
    public void updateGameState() {
    }
    public void addEntity(Entity entity, PositionPoint position) {
        this.map[position.getX()][position.getY()] = position;
    }
    public void removeEntity(Entity entity) {
    }
    public void moveEntity(Entity entity, PositionPoint newPosition) {
    }

}
