package src.GameObjects;
import src.Mechanics.Direction;
import src.Mechanics.PositionPoint;

public class LuringGemEnchantment extends Enchantment {
    public LuringGemEnchantment(EnchantmentTypes type, boolean isStorable, int duration, PositionPoint position){
        super(type, isStorable, duration, position);
    }
    public void applyEffect(Direction direction) {
        // Apply the effect specific to the luring gem in the given direction
        System.out.println("Applying Luring Gem effect in direction: " + direction);
    }
}