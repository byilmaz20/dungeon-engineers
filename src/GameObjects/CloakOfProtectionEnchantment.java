package src.GameObjects;
import java.io.Serializable;

import src.GameObjects.EnchantmentTypes;
import src.Mechanics.PositionPoint;

public class CloakOfProtectionEnchantment extends Enchantment implements Serializable{
    
    public CloakOfProtectionEnchantment(int duration, PositionPoint position, Hall hall) {
        super(position, hall);
    }
    
    public void applyEffect(){
        // Apply the effect of the enchantment
    }
    
    public void removeEffect(){
        // Remove the effect of the enchantment
    }
    
    public void die(){
        // Die
    }
    

}