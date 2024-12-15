package src.GameObjects;
import src.Mechanics.PositionPoint;

public class ExtraTimeEnchantment extends Enchantment {
    ExtraTimeEnchantment(PositionPoint position, Hall hall){
        super(EnchantmentTypes.EXTRA_TIME_ENCHANTMENT, false, 5, position, hall);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}