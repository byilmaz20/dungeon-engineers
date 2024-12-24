package src.GameObjects;
import src.GameController.GameFlowController;
import src.Mechanics.PositionPoint;

public class Rune extends Entity {
    boolean isFound;

    public Rune(PositionPoint position, Hall hall) {
        super(position, hall);
        this.isFound = false; // Initialize the rune as not found

    }
    public PositionPoint getPosition() {
        return this.position;
    }
    public void setPosition(PositionPoint position) {
        this.position = position;
    }
    public void found() {
        this.isFound = true;
        GameFlowController.proceedNextHall();
    }
  
}