package src.GameObjects;

import src.Mechanics.PositionPoint;


public class Obstacles extends Entity {
    
    public Obstacles(PositionPoint position, Hall hall) {
        super(position, hall); // Initialize the base Entity class
    }

    // Check if the obstacle contains the rune
    public boolean containsRune(Rune rune) {
        // Compare the positions of this obstacle and the rune
        return this.position.equals(rune.position);
    }
}
