package src.GameObjects;
import src.GameController.FighterTimeController;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class FighterMonster extends Monster {
    private FighterTimeController fighterTimeController;
    private Timer fighterTimer;
    private Direction lureDirection;
    public FighterMonster(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = MonsterTypes.FighterMonster;
        this.fighterTimeController = new FighterTimeController(grid, this);
        fighterTimeController.startTimeController();
        fighterTimer = fighterTimeController.getTimer();
        Direction lureDirection;
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

    public boolean fighterAttack(Hero hero){
        PositionPoint heroPosition = hero.getPosition(); 
        int monsterX = this.position.getX();
        int monsterY = this.position.getY();
    
        // Define the 3x3 area around the monster
        int minX = monsterX - 1;
        int maxX = monsterX + 1;
        int minY = monsterY - 1;
        int maxY = monsterY + 1;
    
        // Check if the hero is within the 3x3 square
        if (heroPosition.getX() >= minX && heroPosition.getX() <= maxX &&
            heroPosition.getY() >= minY && heroPosition.getY() <= maxY) {
            // Hero is within attack range
            hero.decreaseLifeCount();
            return true;
        } else {
            // Hero is out of range
            return false;
        }
    }



    public Direction getLureDirection() {
        return lureDirection;
    }

    public void setLureDirection(Direction lureDirection) {
        this.lureDirection = lureDirection;
    }
    
    // private void stabHero(Hero hero) {
    //     int damage = calculateDaggerDamage(); // Define how much damage the dagger deals
    //     hero.takeDamage(damage); // Assuming Hero has a takeDamage method
    //     System.out.println("The hero has been stabbed for " + damage + " damage!");
    // }


}