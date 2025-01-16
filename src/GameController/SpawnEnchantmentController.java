package src.GameController;

import java.io.Serializable;

import src.GameObjects.CloakOfProtectionEnchantment;
import src.GameObjects.Enchantment;
import src.GameObjects.EnchantmentTypes;
import src.GameObjects.ExtraLifeEnchantment;
import src.GameObjects.ExtraTimeEnchantment;
import src.GameObjects.LuringGemEnchantment;
import src.GameObjects.RevealEnchantment;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class SpawnEnchantmentController implements Serializable{

    public SpawnEnchantmentController() {
    }

    public Enchantment spawnEnchantment(GridEnvironment grid) {
        PositionPoint randomLocation = grid.selectRandomLocation(); // Get a random empty position
        if (randomLocation != null) {
            // Create a random enchantment
            EnchantmentTypes type = Enchantment.selectRandomEnchantment();
            // EnchantmentTypes type = EnchantmentTypes.EXTRA_TIME_ENCHANTMENT;
            Enchantment enchantment;

            switch (type) {
                case EXTRA_TIME_ENCHANTMENT:
                    enchantment = new ExtraTimeEnchantment(randomLocation, grid.getHall(), grid);
                    break;
                case CLOAK_OF_PROTECTION_ENCHANTMENT:
                    enchantment = new CloakOfProtectionEnchantment(randomLocation, grid.getHall(), grid);
                    break;
                case REVEAL_ENCHANTMENT:
                    enchantment = new RevealEnchantment(randomLocation, grid.getHall(), grid);
                    break;
                case LURING_GEM_ENCHANTMENT:
                    enchantment = new LuringGemEnchantment(randomLocation, grid.getHall(), grid);
                    break;
                case EXTRA_LIFE_ENCHANTMENT:
                    enchantment = new ExtraLifeEnchantment(randomLocation, grid.getHall(), grid);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }



            // Place the enchantment on the grid
            if (grid.moveEntity(enchantment)) {
                //System.out.println("Spawned " + enchantment.getType() + " at position: " + randomLocation);
                        return enchantment;
            } else {
                //System.out.println("Failed to place the enchantment at position: " + randomLocation);
                return null;
            }
        }
                        return null;
    }
}