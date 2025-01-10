package src.Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Mechanics.GridEnvironment;

import src.Mechanics.PositionPoint;
import src.GameController.GameModeController;
import src.GameObjects.ArcherMonster;
import src.GameObjects.Entity;
import src.GameObjects.Hero;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;


public class isPositionValidTest {
    private Hall hall;
    private GridEnvironment grid;

    @BeforeEach
    void setUp() {
        GameModeController GameModeController = new GameModeController() ;
        GameModeController.setGameMode("easy"); 
        GameModeController.getInstance().setGameMode("easy"); // Set the game mode for tests
        hall = new Hall(HallTypes.AIR);
        PositionPoint coordinates = new PositionPoint(0, 0);

        hall.put(coordinates, obj);
        grid = new GridEnvironment(hall);
        PositionPoint heroPosition = new PositionPoint(3, 0); // Within range
        Hero hero = new Hero(heroPosition, hall);
        hero = new Hero(heroPosition, hall);
        hero.setLifeCount(3); // Initial life count
    }
    @Test
    void testValidPosition() {
        PositionPoint position = new PositionPoint(3, 3);
        grid.map[3][3] = null; // Ensure the position is unoccupied.
        assertFalse(grid.isPositionValid(position), "Position should be valid.");
    }
    @Test
    void testPositionOutOfBounds() {
        PositionPoint position = new PositionPoint(30, 3); // x-coordinate exceeds mapWidth.
        assertFalse(grid.isPositionValid(position));
    }
    @Test
    void testPositionOccupied() {
        PositionPoint position = new PositionPoint(2, 2);
        grid.map[2][2] = (Entity) new Object(); // Mark the position as occupied.
        assertFalse(grid.isPositionValid(position));
    }

    
}
