package src.GameObjects;

import java.io.Serializable;
import java.util.Random;
import src.GameController.WizardTimeController;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class WizardMonster extends Monster {
    private WizardTimeController wizardTimeController;
    transient private Timer wizardTimer;
    public WizardMonster(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = MonsterTypes.WizardMonster;
        this.wizardTimeController = new WizardTimeController(grid, this);
        wizardTimeController.startTimeController();
        wizardTimer = wizardTimeController.getTimer();
    }
    // public void teleportRune(GridEnvironment grid) {
    //     Random random = new Random();

    //     PositionPoint runePosition = grid.rune.position;
    //     PositionPoint newrunePosition = grid.rune.position;
    //     while (runePosition == newrunePosition ) { 
    //         newrunePosition = grid.getHall().getObstacles().get(random.nextInt(grid.getHall().getObstacles().size())).position;
    //     }
    //     grid.rune.position  = newrunePosition;
    //     System.out.println("Rune has been spawned to " + newrunePosition.x +", "+ newrunePosition.y);
    // }

    public void teleportRune(GridEnvironment grid) {
        if (grid == null || grid.rune == null || grid.getHall() == null || grid.getHall().getObstacles() == null || grid.getHall().getObstacles().isEmpty()) {
            throw new IllegalArgumentException("Grid, rune, hall, or obstacles must not be null or empty");
        }

        Random random = new Random();
        PositionPoint runePosition = grid.rune.position;
        PositionPoint newRunePosition;

        do {
            newRunePosition = grid.getHall()
                                .getObstacles()
                                .get(random.nextInt(grid.getHall().getObstacles().size()))
                                .position;
        } while (runePosition.equals(newRunePosition));

        grid.rune.position = newRunePosition;
        System.out.println("Rune has been spawned to " + newRunePosition.x + ", " + newRunePosition.y);
    }

}