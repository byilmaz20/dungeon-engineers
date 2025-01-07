package src.Mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import src.GameController.ITimeControllers;
import src.GameController.TimeController;
import src.GameObjects.Enchantment;
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
    TimeController mainTimeController;
    private GridChangeListener gridChangeListener;
    private List<ITimeControllers> timeControllers;
    public Obstacles runeInObject;

    // flags for easy mode monster spawning limitations
    public boolean isWizardMonsterSpawned = false;
    public boolean isArcherMonsterSpawned = false;

    public interface GridChangeListener {
        void onGridChanged(Entity[][] map, PositionPoint... changedPositions);
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
            //System.out.println(entity.getClass().getSimpleName() + " at: " + entity.position);
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
        this.mainTimeController = new TimeController(this);
    }
    public TimeController getMainTimeController() {
        return this.mainTimeController;
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
            map[entity.position.x][entity.position.y] = entity;
            hall.placeEntity(entity);

            List<Monster> monsters = hall.getMonsters();
            for (Monster monster : monsters) {
                if (monster.getType() == MonsterTypes.FighterMonster) {
                    if (((src.GameObjects.FighterMonster) monster).fighterAttack(this.hero)){
                        System.out.println("Hero's remaining lifes = " + this.hero.getLives());
                    }
                } 
                else if ((monster.getType() == MonsterTypes.ArcherMonster)) {
                    if (((src.GameObjects.ArcherMonster) monster).shootArrow(this.hero)){
                        System.out.println("Hero's remaining lifes = " + this.hero.getLives());
                    }
                }
            }

            notifyGridChange(entity.position);
            return true;
        } else {
            return false;
        }
    }
    

    public void removeEntity(Entity entity) {
        PositionPoint oldPosition = entity.position;
        map[oldPosition.x][oldPosition.y] = null;
        hall.removeEntity(entity);
        notifyGridChange(oldPosition);
    }
    
    public void moveEntityToNewPosition(Entity entity, PositionPoint newPosition) {
        if (isPositionValid(newPosition)) {
            PositionPoint oldPosition = entity.position;
            map[oldPosition.x][oldPosition.y] = null;
            entity.position = newPosition;
            map[newPosition.x][newPosition.y] = entity;
            hall.removeEntity(entity);
            hall.placeEntity(entity);
            notifyGridChange(oldPosition, newPosition);
        } else {
            //System.out.println("Invalid movement. Position occupied or out of bounds.");
        }
    }
    
    public boolean moveEntity(Entity entity, Direction direction) {
        if (checkMovement(entity, direction)) {
            PositionPoint oldPosition = entity.position;
            PositionPoint newPosition = entity.position.move(direction.getDirectionEnum());
    
            map[oldPosition.x][oldPosition.y] = null;
            entity.position = newPosition;
            map[newPosition.x][newPosition.y] = entity;
    
            if (entity instanceof src.GameObjects.Hero) {
                hero.position = newPosition;
            }
    
            List<Monster> monsters = hall.getMonsters();
            for (Monster monster : monsters) {
                if (monster.getType() == MonsterTypes.FighterMonster) {
                    if (((src.GameObjects.FighterMonster) monster).fighterAttack(this.hero)){
                        System.out.println("Hero's remaining lifes = " + this.hero.getLives());
                    }
                } 
                else if ((monster.getType() == MonsterTypes.ArcherMonster)) {
                    if (((src.GameObjects.ArcherMonster) monster).shootArrow(this.hero)){
                        System.out.println("Hero's remaining lifes = " + this.hero.getLives());
                    }
                }
            }
    
            notifyGridChange(oldPosition, newPosition);
            return true;
        } else {
            //System.out.println("Invalid movement. Position occupied or out of bounds.");
            return false;
        }
    }
    
    public void removeEnchantmentFromGrid(Enchantment enchantment) {
        // Remove from the grid
        PositionPoint position = enchantment.position;
        if (map[position.x][position.y] == enchantment) {
            map[position.x][position.y] = null;
        }
    
        System.out.println("Enchantment removed from grid and hall: " + enchantment.getType());
        notifyGridChange();
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

    private void notifyGridChange(PositionPoint... changedPositions) {
        if (gridChangeListener != null) {
            gridChangeListener.onGridChanged(map, changedPositions);
        }
    }

    public Rune getRune() {
        return this.rune;
    }
    public Hero getHero() {
        return this.hero;
    }

}
