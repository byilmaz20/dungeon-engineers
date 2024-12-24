package src.GameObjects;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class WizardMonster extends Monster {
    public WizardMonster(PositionPoint position){
        super(position, null);
    }

public void teleportRune(GridEnvironment grid) {
    PositionPoint randomLocation = grid.selectRandomLocation();
    if (randomLocation != null) {
        grid.rune.position = randomLocation;
        

    }
        
    }
}