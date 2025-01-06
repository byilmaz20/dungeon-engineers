package src.GameObjects;
import java.io.Serializable;

import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;


public class ArcherMonster extends Monster implements Serializable{
    public ArcherMonster(PositionPoint position, Hall hall){
        super(position, hall);
        this.type = MonsterTypes.ArcherMonster;
    }

    
    public boolean shootArrow(Hero hero) {
        PositionPoint archerPosition = this.getPosition(); 
                PositionPoint heroPosition = hero.getPosition(); 
        
                // Calculate the distance between the archer monster and the hero
                double distance = archerPosition.distanceTo(heroPosition);
        
                // Check if the hero is within 4 squares
                if (distance < 4) {
                    hero.decreaseLifeCount();
                    System.out.printf("Archer Monster shot an arrow at the hero! Hero's life count: %d\n", hero.getLives());
                    return true;
                
                } else {
                    return false;
                }
            }
        
        
            private PositionPoint getPosition() {
                return this.position;
            }

   
}