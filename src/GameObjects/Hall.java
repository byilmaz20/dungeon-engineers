package src.GameObjects;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    HallTypes hallType;
    List<Entity> entities;          // Main list of all entities
    List<Enchantment> enchantments; // Sublist for Enchantments
    List<Monster> monsters;         // Sublist for Monsters
    List<Obstacles> obstacles;      // Sublist for Obstacles
    Rune rune;
    int minimumObjectsRequired;

    public Hall(HallTypes hallType) {
        this.hallType = hallType;
        this.entities = new ArrayList<>();
        this.enchantments = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.obstacles = new ArrayList<>();

        switch (hallType) {
            case EARTH:
                minimumObjectsRequired = 6;                
                break;
            case AIR:
                minimumObjectsRequired = 9;
                break;
            case WATER:
                minimumObjectsRequired = 13;
                break;
            case FIRE:
                minimumObjectsRequired = 17;
                break;
            default:
                break;
        }
    }

    // Place an entity and update sublists if needed
    public void placeEntity(Entity entity) {
        entities.add(entity); // Add to main list

        // Update specific sublists based on the type of entity
        if (entity instanceof Enchantment) {
            enchantments.add((Enchantment) entity);
        } else if (entity instanceof Monster) {
            monsters.add((Monster) entity);
        } else if (entity instanceof Obstacles) {
            obstacles.add((Obstacles) entity);
        }
        System.out.println("Entity added: " + entity.getClass().getSimpleName());
    }

    // Getter methods for specific sublists
    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Obstacles> getObstacles() {
        return obstacles;
    }

    public boolean checkObjectRequirements() {
        return obstacles.size() >= minimumObjectsRequired;
    }
    public List<Entity> getEntitys(){
        return entities;
    }

    
}
