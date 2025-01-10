package src.Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.UI.BuildModeScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class tests the functionality of the placeSelectedObject method in the BuildModeScreen class.
 * 
 * Specifications:
 * - Requires: A valid BuildModeScreen instance, a JLabel representing a cell, and a valid hall name.
 * - Modifies: The cell's icon, the hall object counts, and the hall object placements.
 * - Effects: Places the selected object on the specified cell, updates the hall object counts, and tracks placement. 
 *            If the cell already contains an object, replaces it with the default state.
 */
public class PlaceSelectedObjectTest {
    private BuildModeScreen screen;
    private JLabel cell;
    private String hallName = "Hall of WATER";

    @BeforeEach
    public void setUp() {
        // Setup initial state
        screen = new BuildModeScreen();
        cell = new JLabel();
        ImageIcon last = new ImageIcon("src/Images/BackgroundImages/cell.png");
        last.setDescription("cell");
        cell.setIcon(last);
        
        screen.setSelectedObjectIcon(new ImageIcon("src/Images/ObjectImages/Barrel.png"));
    }

    @Test
    public void testPlaceObjectOnEmptyCell() {
        /**
         * Requires: An empty cell (with default icon), valid hall name.
         * Modifies: The cell's icon, hall object counts, hall object placements.
         * Effects: Adds the selected object to the cell, increments the object count for the hall, 
         *          and updates the hall object placement map.
         */
        int initialCount = screen.getHallObjectCounts().get(hallName);
        
        screen.placeSelectedObject(cell, hallName);

        JPanel hallPanel = screen.getHallPanel(hallName); 
        int cellIndex = screen.getComponentIndex(hallPanel, cell);
        int row = cellIndex / 25;
        int col = cellIndex % 25;
        assertEquals(initialCount + 1, (int) screen.getHallObjectCounts().get(hallName), "Object count should increase by 1");
        assertNotNull(cell.getIcon(), "Cell icon should be updated");
        assertTrue(BuildModeScreen.hallObjectPlacements.get(hallName).containsKey(new Point(row, col)), "Placement should be tracked");
    }

    @Test
    public void testReplaceObjectWithDefaultState() {
        /**
         * Requires: A cell with an object already placed, valid hall name.
         * Modifies: The cell's icon, hall object counts, hall object placements.
         * Effects: Removes the object from the cell, decrements the object count for the hall, 
         *          and removes the placement from the hall object placement map.
         */
        screen.placeSelectedObject(cell, hallName); // Place an object
        int updatedCount = screen.getHallObjectCounts().get(hallName);

        JPanel hallPanel = screen.getHallPanel(hallName); 
        int cellIndex = screen.getComponentIndex(hallPanel, cell);
        int row = cellIndex / 25;
        int col = cellIndex % 25;

        screen.placeSelectedObject(cell, hallName); 
        assertEquals(updatedCount - 1, (int) screen.getHallObjectCounts().get(hallName), "Object count should decrease by 1");
        assertEquals("cell", ((ImageIcon) cell.getIcon()).getDescription(), "Cell icon should reset to default");
        assertFalse(BuildModeScreen.hallObjectPlacements.get(hallName).containsKey(new Point(row, col)), "Placement should be removed");
    }


    @Test
    public void testHandleNullSelectedObject() {
        /**
         * Requires: The selected object icon is null.
         * Modifies: None.
         * Effects: Does not change the cell's icon, hall object counts, or hall object placements.
         */
        screen.setSelectedObjectIcon(null);

        screen.placeSelectedObject(cell, hallName);
        assertEquals(0, (int) screen.getHallObjectCounts().get(hallName), "Object count should not change");
        assertEquals("cell", ((ImageIcon) cell.getIcon()).getDescription());
    }
}
