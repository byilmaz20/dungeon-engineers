package src.GameObjects;
import java.io.Serializable;

import src.Mechanics.PositionPoint;

public class RevealEnchantment extends Enchantment implements Serializable{
    RevealEnchantment(PositionPoint position, Hall hall){
        super(position, hall);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}