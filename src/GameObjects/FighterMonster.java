package src.GameObjects;
import java.io.Serializable;

import src.GameController.FighterTimeController;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class FighterMonster extends Monster implements Serializable{
    private FighterTimeController fighterTimeController;
    transient private Timer fighterTimer;
    private double lastAttackTime = -4.0;
    
    public FighterMonster(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = MonsterTypes.FighterMonster;
        this.fighterTimeController = new FighterTimeController(grid, this);
        fighterTimeController.startTimeController();
        fighterTimer = fighterTimeController.getTimer();
    }
    public void moveRandomly(GridEnvironment grid) {
        // Select a random direction
        Direction.DirectionEnum randomDirection = PositionPoint.getRandomDirection();
        // Check if movement is valid and attempt to move
        if (grid.checkMovement(this, new Direction(randomDirection))) {
                grid.moveEntity(this, new Direction(randomDirection));
                //System.out.println("FighterMonster moved randomly to: " + this.position);
        } 
        else {
            //System.out.println("FighterMonster's random move was blocked.");
            moveRandomly(grid);
        }
    }
 /**
 * Checks if the hero is within a 3x3 attack range of the monster and decreases the hero's life count if attacked.
 *
 * Requires:
 * - `hero` is a valid and non-null Hero object.
 * - The `hero` object must have a valid `PositionPoint` associated with it.
 * - The monster's position (`this.position`) must be valid and initialized.
 *
 * Modifies:
 * - Decreases the life count of the `hero` if the hero is within the 3x3 attack range of the monster.
 *
 * Effects:
 * - If the hero's position lies within the 3x3 area around the monster's position (including diagonals), 
 *   the hero's life count is decreased by one.
 * - Returns `true` if the hero was attacked (i.e., within range), and `false` otherwise.
 */
    public boolean fighterAttack(Hero hero){
        PositionPoint heroPosition = hero.getPosition(); 
        int monsterX = this.position.getX();
        int monsterY = this.position.getY();
    
        // Define the 3x3 area around the monster
        int minX = monsterX - 1;
        int maxX = monsterX + 1;
        int minY = monsterY - 1;
        int maxY = monsterY + 1;

        double elapsedTime = fighterTimer.getElapsedTime();
        // Check if the hero is within the 3x3 square and if ith has been 3 seconds since the last attack
        if (heroPosition.getX() >= minX && heroPosition.getX() <= maxX &&
            heroPosition.getY() >= minY && heroPosition.getY() <= maxY
            && lastAttackTime + 3.0 < elapsedTime) {
            // Hero is within attack range
            hero.decreaseLifeCount();
            lastAttackTime = elapsedTime;
            return true;
        } else {
            // Hero is out of range
            return false;
        }
    }



    
    
    // private void stabHero(Hero hero) {
    //     int damage = calculateDaggerDamage(); // Define how much damage the dagger deals
    //     hero.takeDamage(damage); // Assuming Hero has a takeDamage method
    //     System.out.println("The hero has been stabbed for " + damage + " damage!");
    // }


}
