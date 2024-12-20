package src.GameObjects;

import src.Mechanics.PositionPoint;

public class Obstacles extends Entity {
    private ObstacleType type;

    public Obstacles(PositionPoint position, Hall hall, ObstacleType type) {
        super(position, hall); // Initialize the base Entity class
        this.type = type;
    }

    // Check if the obstacle contains the rune
    public boolean containsRune(Rune rune) {
        // Compare the positions of this obstacle and the rune
        return this.position.equals(rune.position);
    }

    // Getter for the obstacle type
    public ObstacleType getType() {
        return type;
    }

    // Setter for the obstacle type
    public void setType(ObstacleType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Obstacle: " + type + " at " + position;
    }

    // Enum for obstacle types
    public enum ObstacleType {
        SKULL,
        STAIR,
        RECTANGLE,
        ONE_BOX, // Renamed for better readability
        TWO_BOX, // Renamed for better readability
        BARREL,
        CHEST,
        POTION
    }
}
