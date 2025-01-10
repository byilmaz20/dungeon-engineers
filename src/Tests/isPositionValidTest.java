package src.Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Mechanics.PositionPoint;
import src.GameController.GameModeController;
import src.GameObjects.ArcherMonster;
import src.GameObjects.Hero;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;

public class isPositionValidTest {
    private int mapWidth;
    private int mapHeight;
    private Object[][] map;
    private PositionPoint point;

    @BeforeEach
    void setUp() {
        mapWidth = 10;
        mapHeight = 10;
        map = new Object[mapWidth][mapHeight];
    }
    @Test
    void testValidPosition() {
        PositionPoint position = new PositionPoint(3, 3);
        map[3][3] = null; // Ensure the position is unoccupied.
        assertTrue(position.isPositionValid(position));
    }
    @Test
    void testPositionOutOfBounds() {
        PositionPoint position = new PositionPoint(15, 3); // x-coordinate exceeds mapWidth.
        assertFalse(isPositionValid(position));
    }
    @Test
    void testPositionOccupied() {
        PositionPoint position = new PositionPoint(2, 2);
        map[2][2] = new Object(); // Mark the position as occupied.
        assertFalse(isPositionValid(position));
    }

    
}
