package src.Tests;

import src.GameObjects.Hero;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.GameController.GameModeController;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;


public class TeleportRuneTest {

    private WizardMonster wizardMonster;
    private Hall hall;
    private GridEnvironment grid;
    private Obstacles obstacle1, obstacle2, obstacle3;
    private Rune rune;
    private PositionPoint wizardPosition;

    @BeforeEach
    void setUp() {

        GameModeController gameModeController = new GameModeController();
        gameModeController.setGameMode("easy");
        GameModeController.getInstance().setGameMode("easy");

        wizardPosition = new PositionPoint(0, 0);
        PositionPoint obstaclePosition1 = new PositionPoint(5, 21);
        PositionPoint obstaclePosition2 = new PositionPoint(21, 14);
        PositionPoint obstaclePosition3 = new PositionPoint(21, 4);

        hall = new Hall(HallTypes.AIR);

        obstacle1 = new Obstacles(obstaclePosition1, hall, ObstacleType.POTION);
        obstacle2 = new Obstacles(obstaclePosition2, hall, ObstacleType.POTION);
        obstacle3 = new Obstacles(obstaclePosition3, hall, ObstacleType.POTION);

        
    }

    @Test
    void testTeleportRuneNormalCase() {
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);

        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        hall.placeEntity(wizardMonster);
        rune = grid.getRune();

        PositionPoint oldPosition = rune.getPosition(); // Get current rune position
        wizardMonster.teleportRune(grid); // Call the teleport method
        PositionPoint newPosition = rune.getPosition(); // Get the new rune position

        // Ensure the rune has moved to a new position
        assertNotEquals(oldPosition, newPosition);

        // Ensure the new position is one of the obstacle positions
        assertTrue(
            newPosition.equals(obstacle1.getPosition()) ||
            newPosition.equals(obstacle2.getPosition()) ||
            newPosition.equals(obstacle3.getPosition())
        );
    }

    @Test
    void testTeleportRuneWhenOnlyOneObstacle() {
        // Remove two obstacles to leave only one
        hall.placeEntity(obstacle1);

        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        hall.placeEntity(wizardMonster);
        rune = grid.getRune();

        assertEquals(1, hall.getObstacles().size(), "There should be only one obstacle in the hall");

        // Ensure the method throws an exception due to insufficient obstacles
        Exception exception = assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(grid));
        assertEquals("There must be more than one obstacle in the hall", exception.getMessage());
    }

        
    

    @Test
    void testTeleportRuneWithEmptyObstacles() {
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);

        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        hall.placeEntity(wizardMonster);
        rune = grid.getRune();

        hall.getObstacles().clear(); // Clear all obstacles
        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(grid));
    }

    @Test
    void testTeleportRuneWithNullGrid() {
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);

        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        hall.placeEntity(wizardMonster);
        rune = grid.getRune();

        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(null));
    }

    @Test
    void testTeleportRuneWithNullRune() {
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);

        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        hall.placeEntity(wizardMonster);
        rune = grid.getRune();

        grid.setRune(null); // Remove the rune from the grid
        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(grid));
    }
}
