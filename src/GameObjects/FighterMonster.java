package src.GameObjects;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class FighterMonster extends Monster {
    public FighterMonster(PositionPoint position, Hall hall){
        super(position, hall);
    }


    public void moveRandomly(GridEnvironment grid) {
        // Select a random direction
        Direction.DirectionEnum randomDirection = PositionPoint.getRandomDirection();
        
        
        // Check if movement is valid and attempt to move
        if (grid.checkMovement(this, new Direction(randomDirection))) {
            
                grid.moveEntity(this, new Direction(randomDirection));
                System.out.println("FighterMonster moved randomly to: " + this.position);
        } 
        else {
            System.out.println("FighterMonster's random move was blocked.");
        }
    }

    public void stab(){
        // Stab player
    }
    public void attack(){
        // Attack player
    }
    public void die(){
        // Die
    }
    
}