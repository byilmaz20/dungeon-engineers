package src.GameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.PositionPoint;

public class Hall {
    public HallTypes hallType;
    public List<Entity> entities;          // Main list of all entities
    List<Enchantment> enchantments; // Sublist for Enchantments
    List<Monster> monsters;         // Sublist for Monsters
    List<Obstacles> obstacles;      // Sublist for Obstacles
    int minimumObjectsRequired;
        public Rune rune;
        public Hero hero;

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

    private void createRuneFromObstacles() {
            if (obstacles.isEmpty()) {
            System.out.println("No obstacles available to set the Rune position.");
            return;
        }
        // Choose a random obstacle
        Random random = new Random();
        Obstacles randomObstacle = obstacles.get(random.nextInt(obstacles.size()));

        // Create a Rune at the position of the selected obstacle
        PositionPoint runePosition = randomObstacle.position;
        Rune rune = new Rune(runePosition, this);

        // Set the Rune for the hall (but do not add to entities)
        this.rune = rune;
        System.out.println("Rune created at position: " + runePosition);
    }
    public void createHero() {
        Random random = new Random();
        boolean validPositionFound = false;
        PositionPoint heroPosition = null;

        while (!validPositionFound) {
            // Generate random x and y within bounds
            int x = random.nextInt(25);
            int y = random.nextInt(25);
            heroPosition = new PositionPoint(x, y);

            validPositionFound = true;

            // Check if the position is not occupied by an obstacle or adjacent to any obstacle
            for (Obstacles obstacle : obstacles) {
                if (heroPosition.equals(obstacle.position) || isAdjacent(heroPosition, obstacle.position)) {
                    validPositionFound = false;
                    break;
                }
            }
        }

        // Create the Hero and set it to the Hall
        this.hero = new Hero(heroPosition, this);
                this.placeEntity(hero); // Add the Hero to the entities

        System.out.println("Hero created at position: " + heroPosition);
    }

    private boolean isAdjacent(PositionPoint position1, PositionPoint position2) {
        int dx = Math.abs(position1.x - position2.x);
        int dy = Math.abs(position1.y - position2.y);
        return dx <= 1 && dy <= 1; // Check if positions are adjacent
    }
    public static void main(String[] args) {
    // Create a Hall
    Hall hall = new Hall(HallTypes.EARTH);

    // Add some obstacles to the hall
    hall.placeEntity(new Obstacles(new PositionPoint(3, 4), hall, ObstacleType.BARREL));
    hall.placeEntity(new Obstacles(new PositionPoint(7, 8), hall, ObstacleType.ONE_BOX));
    hall.placeEntity(new Obstacles(new PositionPoint(10, 5), hall, ObstacleType.RECTANGLE));
    hall.createRuneFromObstacles();
    hall.createHero();

    // Rune is automatically created during Hall instantiation
    if (hall.rune != null) {
        System.out.println("Rune is set at position: " + hall.rune.getPosition().x + hall.rune.getPosition().y );
    } else {
        System.out.println("Rune was not created.");
    }
    if (hall.hero != null) {
            System.out.println("Hero is set at position: " + hall.hero.getPosition().x + hall.hero.getPosition().y);
        } else {
            System.out.println("Hero was not created.");
        }
}


}
