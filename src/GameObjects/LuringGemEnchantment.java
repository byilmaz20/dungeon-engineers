package src.GameObjects;
import src.Mechanics.Direction;
import src.Mechanics.PositionPoint;

public class LuringGemEnchantment extends Enchantment {
    public LuringGemEnchantment(PositionPoint position, Hall hall) {
        super(EnchantmentTypes.LURING_GEM_ENCHANTMENT, true, 100, position, hall);
    }//todo duration sacma oldu
    public void applyEffect(Direction direction) {
        // Apply the effect specific to the luring gem in the given direction
        System.out.println("Applying Luring Gem effect in direction: " + direction);
    }
}