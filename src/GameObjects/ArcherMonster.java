package src.GameObjects;
import src.GameObjects.Obstacles.ObstacleType;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;


public class ArcherMonster extends Monster {
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
                    hero.updateLifeCount(-1);  
                    return true;
                
                } else {
                    hero.updateLifeCount(0); 
                    return false;
                }
            }
        
        
            private PositionPoint getPosition() {
                return this.position;
            }

   
}