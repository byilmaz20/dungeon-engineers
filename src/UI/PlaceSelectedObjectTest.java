package src.UI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setUp() {
        // Setup initial state
        screen = new BuildModeScreen();
        cell = new JLabel();
        cell.setIcon(new ImageIcon("src/Images/BackgroundImages/cell.png"));
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

        assertEquals("Object count should increase by 1", initialCount + 1, (int) screen.getHallObjectCounts().get(hallName));
        assertNotNull("Cell icon should be updated", cell.getIcon());
        assertTrue("Placement should be tracked", BuildModeScreen.hallObjectPlacements.get(hallName).containsKey(new Point(0, 0)));
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

        screen.placeSelectedObject(cell, hallName); // Remove it
        assertEquals("Object count should decrease by 1", updatedCount - 1, (int) screen.getHallObjectCounts().get(hallName));
        assertEquals("Cell icon should reset to default", "cell", ((ImageIcon) cell.getIcon()).getDescription());
        assertFalse("Placement should be removed", BuildModeScreen.hallObjectPlacements.get(hallName).containsKey(new Point(0, 0)));
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
        assertEquals("Object count should not change", 0, (int) screen.getHallObjectCounts().get(hallName));
        assertEquals("Cell icon should not change", "cell", ((ImageIcon) cell.getIcon()).getDescription());
    }
}
