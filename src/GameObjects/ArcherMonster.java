package src.GameObjects;
import java.io.Serializable;

import src.Mechanics.PositionPoint;


public class ArcherMonster extends Monster implements Serializable{
    boolean hasAttacked = false;
    public ArcherMonster(PositionPoint position, Hall hall){
        super(position, hall);
        this.type = MonsterTypes.ArcherMonster;

    }

    /*
     * Method: shootArrow
     *
     * Requires:
     * - A valid Hero object is provided.
     * - The positions of the ArcherMonster and the Hero are initialized and valid.
     *
     * Modifies:
     * - The life count of the Hero if the Hero is within 4 squares and not protected.
     * - The `hasAttacked` field of the ArcherMonster to true if an attack is successful.
     *
     * Effects:
     * - Returns true if the ArcherMonster successfully shoots an arrow at the Hero.
     * - Returns false if the Hero is not within range, is protected, or the ArcherMonster has already attacked.
     */
    
    public boolean shootArrow(Hero hero) {
        PositionPoint archerPosition = this.getPosition(); 
        PositionPoint heroPosition = hero.getPosition(); 

        // Calculate the distance between the archer monster and the hero
        double distance = archerPosition.distanceTo(heroPosition);

        // Check if the hero is within 4 squares and protection status
        if (distance < 4 && hero.checkProtection()==false ) { //TODO && 
            hasAttacked == false
            hero.decreaseLifeCount();
            //System.out.printf("Archer Monster shot an arrow at the hero! Hero's life count: %d\n", hero.getLives());
            hasAttacked = true;
            return true;
        
        } else {
            return false;
        }
    }
        
        
    public PositionPoint getPosition() {
        return this.position;
    }

   
}