package src.Mechanics;

public class Direction {
    //UP, DOWN, LEFT, RIGHT
    public enum DirectionEnum {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private DirectionEnum directionEnum;

    public Direction(DirectionEnum directionEnum) {
        this.directionEnum = directionEnum;
    }

    public DirectionEnum getDirectionEnum() {
        return directionEnum;
    }
}
