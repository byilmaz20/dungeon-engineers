package src.GameObjects;
import src.Mechanics.PositionPoint;

public class RevealEnchantment extends Enchantment {
    RevealEnchantment(PositionPoint position, Hall hall){
        super(EnchantmentTypes.REVEAL_ENCHANTMENT, true, 10, position, hall);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }

}