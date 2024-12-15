package src.GameObjects;
import src.Mechanics.PositionPoint;

public class Rune extends Entity {
    PositionPoint position;
    boolean isFound;

    public Rune(PositionPoint position, Hall hall) {
        super(position, hall);
        
    }
  
}