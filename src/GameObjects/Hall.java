package src.GameObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import src.GameController.ITimeControllers;
/**
 * The Hall class represents a game environment of a specific type (e.g., EARTH, AIR, WATER, FIRE).
 * It manages different entities such as enchantments, monsters, and obstacles.
 * The class ensures consistency via a representation invariant and supports operations
 * such as adding/removing entities, verifying minimum object requirements, and tracking hero presence.
  it is the arena that the game gets played on.*/


public class Hall implements Serializable{
    public HallTypes hallType;
    public List<Entity> entities;          // Main list of all entities
    List<Enchantment> enchantments; // Sublist for Enchantments
    List<Monster> monsters;         // Sublist for Monsters
    List<Obstacles> obstacles;      // Sublist for Obstacles
    int minimumObjectsRequired;
    List<ITimeControllers> timeControllers;
    
    public Hall(HallTypes hallType) {
        this.timeControllers = new ArrayList<>();

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
    // Abstract Function:
// AF(c) = A hall of type c.hallType containing:
//         - c.entities: all entities present in the hall,
//         - c.enchantments: enchantments in the hall,
//         - c.monsters: monsters in the hall,
//         - c.obstacles: obstacles in the hall.
//         - c.minimumObjectsRequired: minimum required number of obstacles.
// Representation Invariant:
// 1. c.hallType != null
// 2. c.entities, c.enchantments, c.monsters, and c.obstacles must not be null.
// 3. All objects in c.enchantments, c.monsters, and c.obstacles must be in c.entities.
// 4. c.minimumObjectsRequired >= 0

    public boolean repOk() {
        // (1) hallType != null
        if (hallType == null) return false;
            // (2) Sub-lists and main list should not be null

        if (entities == null || enchantments == null || monsters == null || obstacles == null) return false;

        
        // (3) minimumObjectsRequired >= 0
        if (minimumObjectsRequired < 0) return false;
        // (4) Each sublist item must appear in entities
        for (Enchantment e : enchantments) {
            if (!entities.contains(e)) return false;
        }
        for (Monster m : monsters) {
            if (!entities.contains(m)) return false;
        }
        for (Obstacles o : obstacles) {
            if (!entities.contains(o)) return false;
        }
        return true;
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
        //System.out.println("Entity added: " + entity.getClass().getSimpleName());
    }

    // Remove an entity and update sublists if needed
    public void removeEntity(Entity entity) {
        entities.remove(entity); // Remove from main list
        //System.out.println("Entity removed: " + entity.getClass().getSimpleName());
        // Remove from specific sublists
    if (entity instanceof Enchantment) {
        enchantments.remove(entity);
    } else if (entity instanceof Monster) {
        monsters.remove(entity);
    } else if (entity instanceof Obstacles) {
        obstacles.remove(entity);
    }
    }

    // Getter methods for specific sublists
    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }
    public HallTypes getHallTypes() {
        return hallType;
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

    public void addTimeController(ITimeControllers timeController) {
        this.timeControllers.add(timeController);
    }
    public List<ITimeControllers> getTimeControllers() {
        return this.timeControllers;
    }
   


public Hero getHero() {
    for (Entity entity : this.getEntitys()) { // Loop through all entities in the hall
        if (entity instanceof Hero) { // Check if the entity is an instance of Hero
            return (Hero) entity; // Cast and return the Hero
        }
    }
    return null; // Return null if no Hero is found
}


}
