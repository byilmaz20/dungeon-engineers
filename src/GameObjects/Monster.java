package src.GameObjects;

import java.io.Serializable;
import java.util.Random;
import src.Mechanics.Direction.DirectionEnum;
import src.Mechanics.PositionPoint;

public class Monster extends Entity implements Serializable{
    boolean isTriggered;
     MonsterTypes type;

    // Constructor with a random monster type
    public Monster(PositionPoint position, Hall hall) {
        super(position, hall); // Call the Entity constructor
        this.type = selectRandomMonster(); // Assign random type
        this.isTriggered = false;
    }
    

    // Constructor with a specific monster type
    public Monster(PositionPoint position, Hall hall, MonsterTypes type) {
        super(position, hall); // Call the Entity constructor
        this.type = type; // Assign specific type
        this.isTriggered = false;
    }

    // Update position based on a given direction
    public void updatePosition(DirectionEnum direction) {
        this.position = position.move(direction); // Update position using PositionPoint's move method
        System.out.println(type + " moved " + direction + " to position: " + position);
    }

    // Select a random monster type
    public static MonsterTypes selectRandomMonster() {
        MonsterTypes[] monsterTypes = MonsterTypes.values(); // Get all monster types
        Random random = new Random();
        return monsterTypes[random.nextInt(monsterTypes.length)];
    }

    // Placeholder for attackPlayer logic
    public void attackPlayer() {
        // Logic for attacking the player based on the monster type
    }
    public MonsterTypes getType() {
        return this.type;
    }
}
