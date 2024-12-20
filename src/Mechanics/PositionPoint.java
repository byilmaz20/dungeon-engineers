package src.Mechanics;

import java.util.Random;

public class PositionPoint {
    public int x;
    public int y;
    
    public PositionPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(PositionPoint other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public boolean equals(PositionPoint other) {
        return this.x == other.x && this.y == other.y;
    }
    public static Direction.DirectionEnum getRandomDirection() {
        Direction.DirectionEnum[] directions = Direction.DirectionEnum.values();
        Random random = new Random();
        return directions[random.nextInt(directions.length)];
    }
    public PositionPoint move(Direction.DirectionEnum direction) {
        switch (direction) {
            case UP:
                return new PositionPoint(x, y - 1);
            case DOWN:
                return new PositionPoint(x, y + 1);
            case LEFT:
                return new PositionPoint(x - 1, y);
            case RIGHT:
                return new PositionPoint(x + 1, y);
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
