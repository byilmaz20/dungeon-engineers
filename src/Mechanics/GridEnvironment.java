package src.Mechanics;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
>>>>>>> main
import src.GameObjects.Entity;
import src.GameObjects.Hall;

public class GridEnvironment {
    PositionPoint heroPosition;
    PositionPoint runePosition;
    Hall hall;
    boolean isRuneFound;
    Entity[][] map; // Grid of entities
    int mapWidth = 25; // Fixed grid width
    int mapHeight = 25; // Fixed grid height


    public GridEnvironment(PositionPoint heroPosition, PositionPoint runePosition, Hall hall) {
        this.heroPosition = heroPosition;
        this.runePosition = runePosition;
        this.hall = hall;
        this.map = new Entity[mapWidth][mapHeight]; // Initialize a 25x25 grid
        this.isRuneFound = false;
    for (Entity entity : hall.getEntitys()) { // Use the getter method
        if (isPositionValid(entity.position)) {
            map[entity.position.x][entity.position.y] = entity; // Place the entity on the grid
            System.out.println("Entity placed on grid at: " + entity.position);
        } else {
            System.out.println("Invalid position for entity: " + entity.position);
        }
    }

    }
    private boolean isPositionValid(PositionPoint position) {
        return position.x >= 0 && position.x < mapWidth &&
               position.y >= 0 && position.y < mapHeight &&
               map[position.x][position.y] == null;
    }

    public boolean checkMovement(Entity entity, Direction direction) {
    // Calculate the new position
    PositionPoint newPosition = entity.position.move(direction.getDirectionEnum());

    // Validate the new position
    return isPositionValid(newPosition);
}
    // Overloaded moveEntity: validates the entity's current position
    public boolean moveEntity(Entity entity) {
    // Check if the entity's position is valid
    if (isPositionValid(entity.position)) {
        // Place the entity on the grid
        map[entity.position.x][entity.position.y] = entity; // Set entity in its position
        hall.placeEntity(entity); // Add to the hall's entity list

        System.out.println("Entity placed at: " + entity.position);
        return true;
    } else {
        System.out.println("Invalid position for entity: " + entity.position);
        return false;
    }
<<<<<<< HEAD
    public void updateGameState() {
    }
    public void addEntity(Entity entity, PositionPoint position) {
        this.map[position.getX()][position.getY()] = position;
    }
    public void removeEntity(Entity entity) {
    }
    public void moveEntity(Entity entity, PositionPoint newPosition) {
    }
=======
}

    public boolean moveEntity(Entity entity, Direction direction) {
    if (checkMovement(entity, direction)) {
        // Calculate the new position
        PositionPoint newPosition = entity.position.move(direction.getDirectionEnum());

        // Update the grid
        map[entity.position.x][entity.position.y] = null; // Clear current position
        entity.position = newPosition; // Update entity position
        map[newPosition.x][newPosition.y] = entity; // Set entity in new position
        if (entity instanceof src.GameObjects.Hero) {
            heroPosition = newPosition;
        }

        System.out.println("Entity moved to: " + newPosition);
        return true;
    } else {
        System.out.println("Invalid movement. Position occupied or out of bounds.");
        return false;
    }
    }

   /* public void moveHero(Direction direction) {
    // Calculate the new position for the hero
    PositionPoint newPosition = heroPosition.move(direction.getDirectionEnum());

    // Validate the new position
    if (isPositionValid(newPosition)) {
        // Update the grid: clear the current position
        map[heroPosition.x][heroPosition.y] = null;

        // Update the hero's position
        heroPosition = newPosition;

        // Update the grid: place the hero in the new position
        map[heroPosition.x][heroPosition.y] = null; // If hero itself is an entity, map[heroPosition.x][heroPosition.y] = new Hero(...);

        System.out.println("Hero moved to: " + heroPosition);
    } else {
        System.out.println("Invalid move: Position is occupied or out of bounds.");
    }
}//*/

    public boolean  isRuneFound() {
        return isRuneFound;
    }
    public PositionPoint selectRandomLocation() {
    List<PositionPoint> availablePositions = new ArrayList<>();

    // Loop through the grid to find all available positions
    for (int x = 0; x < mapWidth; x++) {
        for (int y = 0; y < mapHeight; y++) {
            if (map[x][y] == null) { // Check if the cell is unoccupied
                availablePositions.add(new PositionPoint(x, y));
            }
        }
    }

    // If no available positions, return null
    if (availablePositions.isEmpty()) {
        System.out.println("No available positions found.");
        return null;
    }

    // Select a random position from the available ones
    Random random = new Random();
    PositionPoint randomPosition = availablePositions.get(random.nextInt(availablePositions.size()));

    System.out.println("Random available position selected: " + randomPosition);
    return randomPosition;
}
public Hall getHall() {
        return this.hall;
    }
    public Entity[][] getmap(){
        return this.map;
    }

>>>>>>> main

}
