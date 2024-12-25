package src.Mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.GameController.ITimeControllers;
import src.GameController.TimeController;
import src.GameObjects.Entity;
import src.GameObjects.Hall;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.MonsterTypes;
import src.GameObjects.Obstacles;
import src.GameObjects.Rune;

public class GridEnvironment {
    public Hero hero;
    public Rune rune;
    Hall hall;
    public Entity[][] map; // Grid of entities
    int mapWidth = 25; // Fixed grid width
    int mapHeight = 25; // Fixed grid height
    TimeController timeController;
    private GridChangeListener gridChangeListener;
    private List<ITimeControllers> timeControllers;
    public Obstacles runeInObject;
    public interface GridChangeListener {
        void onGridChanged(Entity[][] map);
    }

    public Obstacles getRuneInObject(){
        return runeInObject;
    }
    public void setGridChangeListener(GridChangeListener listener) {
        this.gridChangeListener = listener;
    }

    public GridEnvironment(Hall hall) {
        this.map = new Entity[mapWidth][mapHeight];
        this.hall = hall;
        this.hero = new Hero(selectRandomLocation(), hall);
        this.rune = new Rune(getRandomPositionForRune(), hall);
        hall.entities.add(rune);
        hall.entities.add(hero);
        this.timeControllers = new ArrayList<>();

        for (Entity entity : hall.getEntitys()) { // Use the getter method
            System.out.println(entity.getClass().getSimpleName() + " at: " + entity.position);
            if (entity instanceof Rune) {
                // TODO1: varolan obstacleı silmeden rune u üstüne yapıştır.
                map[entity.position.x][entity.position.y] = entity; // Place the entity on the grid
                //System.out.println("Entity placed on grid at: " + entity.position);
            }else{
                if (isPositionValid(entity.position)) {
                    map[entity.position.x][entity.position.y] = entity; // Place the entity on the grid
                    //System.out.println("Entity placed on grid at: " + entity.position);
                } else {
                    //System.out.println("Invalid position for entity: " + entity.position);
                }
            }
        }
        this.timeController = new TimeController(this);

    }

    public void addTimeController(ITimeControllers timeController) {
        this.timeControllers.add(timeController);
    }
    public List<ITimeControllers> getTimeControllers() {
        return this.timeControllers;
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
            //TODO: alt satıra gerek var mı emin olamadım - Ceylin
            if (entity instanceof src.GameObjects.Hero) {
                hero.position = newPosition;
            }
            List<Monster> monsters = hall.getMonsters();
            for (Monster monster : monsters) {
                if (monster.getType() == MonsterTypes.FighterMonster) {
                    if (((src.GameObjects.FighterMonster) monster).fighterAttack(this.hero)){
                        System.out.println("Hero's remaining lifes = " +this.hero.getLives());
                    }
                } 
                else if ((monster.getType() == MonsterTypes.ArcherMonster)) {
                    if (((src.GameObjects.ArcherMonster) monster).shootArrow(this.hero)){
                        System.out.println("Hero's remaining lifes = " +this.hero.getLives());
                    }
                }
            }


            notifyGridChange();
            System.out.println("Entity moved to: " + newPosition);
            return true;
        } 
        else {
            System.out.println("Invalid movement. Position occupied or out of bounds.");
            return false;
        }
    }
    public boolean checkRuneFound() {
        boolean isAdjacent = false;
        if ((hero.position.x == rune.position.x && hero.position.y == rune.position.y - 1) ||  // Above
            (hero.position.x == rune.position.x && hero.position.y == rune.position.y + 1) ||  // Below
            (hero.position.y == rune.position.y && hero.position.x == rune.position.x - 1) ||  // Left
            (hero.position.y == rune.position.y && hero.position.x == rune.position.x + 1)) {  // Right
            
            isAdjacent = true;
        }
        if (isAdjacent) {
            rune.found();
            
            return true;
        }
        return false;
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

        //System.out.println("Random available position selected: " + randomPosition);
        return randomPosition;
    }

    public PositionPoint getRandomPositionForRune() {
        Random random = new Random();
        int randomNumber = random.nextInt(hall.getObstacles().size());
        PositionPoint runePosition = hall.getObstacles().get(randomNumber).position;
        runeInObject = hall.getObstacles().get(randomNumber);
        System.out.println("Random position for rune selected: " + runePosition.x + ", " + runePosition.y); 
        return runePosition;
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

    public Rune getRune() {
        return this.rune;
    }
    public Hero getHero() {
        return this.hero;
    }

}
