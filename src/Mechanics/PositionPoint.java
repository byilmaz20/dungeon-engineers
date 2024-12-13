package src.Mechanics;

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

    public boolean  equals(PositionPoint other) {
        return this.x == other.x && this.y == other.y;
    }

    public PositionPoint move(Direction direction) {
        return new PositionPoint(x, y);
    }

}
