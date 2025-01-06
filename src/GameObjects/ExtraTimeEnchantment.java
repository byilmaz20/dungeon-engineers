package src.GameObjects;
import java.io.Serializable;

import src.Mechanics.PositionPoint;

public class ExtraTimeEnchantment extends Enchantment implements Serializable{
    ExtraTimeEnchantment(PositionPoint position, Hall hall){
        super(position, hall);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}