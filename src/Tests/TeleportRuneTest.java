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
import src.GameObjects.WizardMonster;
import src.GameObjects.Hero;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;

public class TeleportRuneTest {
    private WizardMonster wizardMonster;
    private GridEnvironment grid;
    private Hall hall;
    private Rune rune;
    private Hero hero;
    private Obstacles obstacle1, obstacle2, obstacle3;

    @BeforeEach
    void setUp() {
        GameModeController gmc = new GameModeController();
        gmc.setGameMode("easy");
        GameModeController.getInstance().setGameMode("easy");

        PositionPoint wizardPosition = new PositionPoint(0, 0);
        PositionPoint heroPosition = new PositionPoint(3, 0);
        PositionPoint runePosition = new PositionPoint(5, 21);
        PositionPoint obstaclePosition1 = new PositionPoint(5, 21);
        PositionPoint obstaclePosition2 = new PositionPoint(21, 14);
        PositionPoint obstaclePosition3 = new PositionPoint(21, 4);

        hall = new Hall(HallTypes.AIR);
        grid = new GridEnvironment(hall);

        wizardMonster = new WizardMonster(wizardPosition, hall, grid);
        rune = new Rune(runePosition, hall);
        hero = new Hero(heroPosition, hall);
        obstacle1 = new Obstacles(obstaclePosition1, hall, ObstacleType.CHEST);
        obstacle2 = new Obstacles(obstaclePosition2, hall, ObstacleType.CHEST);
        obstacle3 = new Obstacles(obstaclePosition3, hall, ObstacleType.CHEST);

        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(obstacle1);
        hall.placeEntity(obstacle2);
        hall.placeEntity(obstacle3);
        hall.placeEntity(wizardMonster);
        

        
    }

    @Test
    void testTeleportRuneNormalCase() {
        PositionPoint oldPosition = grid.getRune().getPosition(); // Get current position of the rune
        wizardMonster.teleportRune(grid); // Call the teleport method
        PositionPoint newPosition = grid.getRune().getPosition(); // Get the new position of the rune

        // Ensure the rune has been moved to a new position
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
        hall.removeEntity(obstacle2);
        hall.removeEntity(obstacle3);

        PositionPoint oldPosition = grid.getRune().getPosition();
        wizardMonster.teleportRune(grid);
        PositionPoint newPosition = grid.getRune().getPosition();

        // Rune's position should remain the same as there’s only one obstacle
        assertEquals(oldPosition, newPosition);
    }

    @Test
    void testTeleportRuneWithNullGrid() {
        // Ensure the method throws an exception for a null grid
        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(null));
    }

    @Test
    void testTeleportRuneWithNullRune() {
        grid.setRune(null); // Remove the rune from the grid
        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(grid));
    }

    @Test
    void testTeleportRuneWithEmptyObstacles() {
        hall.getObstacles().clear(); // Clear all obstacles
        assertThrows(IllegalArgumentException.class, () -> wizardMonster.teleportRune(grid));
    }
}