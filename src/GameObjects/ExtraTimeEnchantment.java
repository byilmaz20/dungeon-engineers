package src.GameObjects;
import src.Mechanics.PositionPoint;

public class ExtraTimeEnchantment extends Enchantment {
    ExtraTimeEnchantment(EnchantmentTypes type, boolean isStorable, int duration, PositionPoint position){
        super(type, isStorable, duration, position);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}