package src.Mechanics;

import java.io.Serializable;

public class Direction {
    //UP, DOWN, LEFT, RIGHT
    public enum DirectionEnum implements Serializable{
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
