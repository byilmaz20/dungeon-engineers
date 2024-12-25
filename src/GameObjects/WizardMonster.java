package src.GameObjects;

import java.util.Random;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class WizardMonster extends Monster {
    public WizardMonster(PositionPoint position){
        super(position, null);
        this.type = MonsterTypes.WizardMonster;
    }
    public void teleportRune(GridEnvironment grid) {
    Random random = new Random();

        PositionPoint runePosition = grid.rune.position;
        PositionPoint newrunePosition = grid.rune.position;
        while (runePosition == newrunePosition ) { 
                            newrunePosition = grid.getHall().getObstacles().get(random.nextInt(grid.getHall().getObstacles().size())).position;

        }
        grid.rune.position  = newrunePosition;

                // PositionPoint newrunePosition = grid.getHall().getObstacles().get(random.nextInt(hall.getObstacles().size())).position;
}
}