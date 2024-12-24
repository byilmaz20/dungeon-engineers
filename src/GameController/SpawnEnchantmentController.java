package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Enchantment;
import src.GameObjects.Rune;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class SpawnEnchantmentController {
    private GridEnvironment grid;

    public SpawnEnchantmentController(GridEnvironment grid) {
        this.grid = grid; // Initialize with the grid
    }

    public Enchantment spawnEnchantment() {
        PositionPoint randomLocation = grid.selectRandomLocation(); // Get a random empty position
        if (randomLocation != null) {
            // Create a random enchantment
            Enchantment enchantment = new Enchantment(randomLocation, grid.getHall());

            // Place the monster on the grid
            if (grid.moveEntity(enchantment)) {
                System.out.println("Spawned " + enchantment.getType() + " at position: " + randomLocation);
                        return enchantment;

            } else {
                System.out.println("Failed to place the enchantment at position: " + randomLocation);
                return null;
            }

        }
                        return null;

    }
}