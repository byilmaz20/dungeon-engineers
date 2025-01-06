package src.GameObjects;

import java.util.Random;
import java.io.Serializable;

import src.GameController.WizardTimeController;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class WizardMonster extends Monster implements Serializable {
    private WizardTimeController wizardTimeController;
    private Timer wizardTimer;
    public WizardMonster(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = MonsterTypes.WizardMonster;
        this.wizardTimeController = new WizardTimeController(grid, this);
        wizardTimeController.startTimeController();
        wizardTimer = wizardTimeController.getTimer();
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