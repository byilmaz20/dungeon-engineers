package src.GameObjects;
import java.io.Serializable;

import src.Mechanics.Direction;
import src.Mechanics.PositionPoint;

public class LuringGemEnchantment extends Enchantment implements Serializable{
    public LuringGemEnchantment(PositionPoint position, Hall hall) {
        super(position, hall);
    }//todo duration sacma oldu
    public void applyEffect(Direction direction) {
        // Apply the effect specific to the luring gem in the given direction
        System.out.println("Applying Luring Gem effect in direction: " + direction);
    }
}