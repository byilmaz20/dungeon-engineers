package src.GameObjects;
import src.Mechanics.PositionPoint;

public class RevealEnchantment extends Enchantment {
    RevealEnchantment(EnchantmentTypes type, boolean isStorable, int duration, PositionPoint position){
        super(type, isStorable, duration, position);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}