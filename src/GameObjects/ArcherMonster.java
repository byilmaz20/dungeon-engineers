package src.GameObjects;
import src.Mechanics.PositionPoint;


public class ArcherMonster extends Monster {
    public ArcherMonster(PositionPoint position){
        super(position);
    }
    public void shootArrow(PlayerObject player){
        // Shoot an arrow if the distance between the player and the archer monster is less than 4
        // check if the hero wears a cloak of protection, then hero will not lose life
        // If the hero is not wearing a cloak of protection, then the archer monster will shoot an arrow at the hero and update the hero's life count.
        }
}