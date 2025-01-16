package src.GameObjects;

import java.io.Serializable;
import java.util.Random;

import src.GameController.GameModeController;
import src.GameController.WizardTimeController;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class WizardMonster extends Monster {
    private WizardTimeController wizardTimeController;
    private Timer wizardTimer;
    public WizardMonster(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = MonsterTypes.WizardMonster;
        this.wizardTimeController = new WizardTimeController(grid, this);
        wizardTimeController.startTimeController();
        wizardTimer = wizardTimeController.getTimer();
    }
    

    /**
     * Teleports the rune in the grid to a new random position on an obstacle within the hall.
     * 
     * Requires:
     * - The `grid` must not be null.
     * - The `grid.rune` must not be null.
     * - The `grid.getHall()` must not be null.
     * - The `grid.getHall().getObstacles()` must not be null or empty.
     * - The hall must contain more than one obstacle.
     * 
     * Modifies:
     * - The `position` of the `rune` in the `grid`.
     * 
     * Effects:
     * - Randomly selects a new position from the obstacles in the hall for the rune.
     * - Ensures the new position is different from the current position of the rune.
     * - Throws `IllegalArgumentException` if:
     *   - `grid` is null.
     *   - `grid.rune` is null.
     *   - `grid.getHall()` is null.
     *   - `grid.getHall().getObstacles()` is null or empty.
     *   - The hall contains only one obstacle.
     */

    public void teleportRune(GridEnvironment grid) {
        if (grid == null || grid.rune == null || grid.getHall() == null || grid.getHall().getObstacles() == null || grid.getHall().getObstacles().isEmpty()) {
            throw new IllegalArgumentException("Grid, rune, hall, or obstacles must not be null or empty");
        }

        Random random = new Random();
        PositionPoint runePosition = grid.rune.position;
        PositionPoint newRunePosition;
        if (grid.getHall().getObstacles().size() == 1) {
            throw new IllegalArgumentException("There must be more than one obstacle in the hall");
        }
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