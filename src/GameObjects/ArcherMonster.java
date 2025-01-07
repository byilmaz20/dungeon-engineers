package src.GameObjects;
import src.Mechanics.PositionPoint;


public class ArcherMonster extends Monster {
    boolean hasAttacked = false;
    public ArcherMonster(PositionPoint position, Hall hall){
        super(position, hall);
        this.type = MonsterTypes.ArcherMonster;

    }

    
    public boolean shootArrow(Hero hero) {
        PositionPoint archerPosition = this.getPosition(); 
        PositionPoint heroPosition = hero.getPosition(); 

        // Calculate the distance between the archer monster and the hero
        double distance = archerPosition.distanceTo(heroPosition);

        // Check if the hero is within 4 squares and protection status
        if (distance < 4 && hero.checkProtection()==false && hasAttacked == false) {
            hero.decreaseLifeCount();
            //System.out.printf("Archer Monster shot an arrow at the hero! Hero's life count: %d\n", hero.getLives());
            hasAttacked = true;
            return true;
        
        } else {
            return false;
        }
    }
        
        
    private PositionPoint getPosition() {
        return this.position;
    }

   
}