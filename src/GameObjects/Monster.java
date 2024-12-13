package src.GameObjects;
import src.Mechanics.PositionPoint;

public class Monster {
    PositionPoint position;
    boolean  isTriggered;

    public Monster(PositionPoint position){
        this.position = position;
        this.isTriggered = false;
    }
    public void updatePosition() {
        // Move monster
    }
    public void selectRandomMonster() {
        // Select random monster
    }
    public void attackPlayer() {
        // Attack player
    }
}