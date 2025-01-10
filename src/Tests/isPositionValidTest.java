package src.Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import src.Mechanics.PositionPoint;

public class isPositionValidTest {
    private int mapWidth;
    private int mapHeight;
    private Object[][] map;

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
        assertTrue(isPositionValid(position));
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
