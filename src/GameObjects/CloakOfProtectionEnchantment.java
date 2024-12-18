package src.GameObjects;
import src.GameObjects.EnchantmentTypes;
import src.Mechanics.PositionPoint;

public class CloakOfProtectionEnchantment extends Enchantment {
    
    public CloakOfProtectionEnchantment(int duration, PositionPoint position, Hall hall) {
        super(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT, true, 20, position, hall);
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