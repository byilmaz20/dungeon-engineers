package src.Mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import src.GameController.TimeController;
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
    TimeController timeController;
    private GridChangeListener gridChangeListener;

    public interface GridChangeListener {
        void onGridChanged(Entity[][] map);
    }

    public void setGridChangeListener(GridChangeListener listener) {
        this.gridChangeListener = listener;
    }

    public GridEnvironment(PositionPoint heroPosition, PositionPoint runePosition, Hall hall) {
        this.timeController = new TimeController(this);
        this.heroPosition = heroPosition;
        this.hall = hall;
        this.runePosition = hall.getRune().getPosition();

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

    public boolean moveEntity(Entity entity) {
        if (isPositionValid(entity.position)) {
            map[entity.position.x][entity.position.y] = entity; // Set entity in its position
            hall.placeEntity(entity); // Add to the hall's entity list

            notifyGridChange();
            System.out.println("Entity placed at: " + entity.position);
            return true;
        } else {
            System.out.println("Invalid position for entity: " + entity.position);
            return false;
        }
    }

    public boolean moveEntity(Entity entity, Direction direction) {
        if (checkMovement(entity, direction)) {
            PositionPoint newPosition = entity.position.move(direction.getDirectionEnum());

            map[entity.position.x][entity.position.y] = null; // Clear current position
            entity.position = newPosition; // Update entity position
            map[newPosition.x][newPosition.y] = entity; // Set entity in new position

            if (entity instanceof src.GameObjects.Hero) {
                heroPosition = newPosition;
                
            }

            notifyGridChange();
            System.out.println("Entity moved to: " + newPosition);
            return true;
        } else {
            System.out.println("Invalid movement. Position occupied or out of bounds.");
            return false;
        }
    }

    public boolean isRuneFound() {
        return isRuneFound;
    }

    public PositionPoint selectRandomLocation() {
        List<PositionPoint> availablePositions = new ArrayList<>();

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (map[x][y] == null) {
                    availablePositions.add(new PositionPoint(x, y));
                }
            }
        }

        if (availablePositions.isEmpty()) {
            System.out.println("No available positions found.");
            return null;
        }

        Random random = new Random();
        PositionPoint randomPosition = availablePositions.get(random.nextInt(availablePositions.size()));

        System.out.println("Random available position selected: " + randomPosition);
        return randomPosition;
    }

    public Hall getHall() {
        return this.hall;
    }

    public Entity[][] getMap() {
        return this.map;
    }

    private void notifyGridChange() {
        if (gridChangeListener != null) {
            gridChangeListener.onGridChanged(map);
        }
        System.out.println("Grid changed at: " + System.currentTimeMillis());

    }
}
