package src.GameObjects;
import src.GameObjects.EnchantmentTypes;
import src.Mechanics.PositionPoint;

public class CloakOfProtectionEnchantment extends Enchantment {
    
    public CloakOfProtectionEnchantment(boolean  isStorable, int duration, PositionPoint position){
        super(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT, isStorable, duration, position);
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