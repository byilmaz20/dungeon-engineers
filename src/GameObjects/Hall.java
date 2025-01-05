package src.GameObjects;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    public HallTypes hallType;
    public List<Entity> entities;          // Main list of all entities
    List<Enchantment> enchantments; // Sublist for Enchantments
    List<Monster> monsters;         // Sublist for Monsters
    List<Obstacles> obstacles;      // Sublist for Obstacles
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
        //System.out.println("Entity added: " + entity.getClass().getSimpleName());
    }

    // Remove an entity and update sublists if needed
    public void removeEntity(Entity entity) {
        entities.remove(entity); // Remove from main list
        //System.out.println("Entity removed: " + entity.getClass().getSimpleName());
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
   


public Hero getHero() {
    for (Entity entity : this.getEntitys()) { // Loop through all entities in the hall
        if (entity instanceof Hero) { // Check if the entity is an instance of Hero
            return (Hero) entity; // Cast and return the Hero
        }
    }
    return null; // Return null if no Hero is found
}


}
