package src.GameObjects;
import java.io.Serializable;

import src.GameController.EnchantmentTimeController;
import src.GameController.LuringGemController;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class LuringGemEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private LuringGemController luringGemController;
    private Timer luringGemTimer;
    private Timer enchantmentTimer;
    private GridEnvironment grid;
    public LuringGemEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
    
        this.type = EnchantmentTypes.LURING_GEM_ENCHANTMENT;
        this.isStorable = false;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        enchantmentTimer = enchantmentTimeController.getTimer();
        this.luringGemController = new LuringGemController(grid);

    }    
    
    public void applyEffect(Direction direction) {
        grid.getHero().setLureDirection(direction);

        grid.getHero().activateFooling();
        luringGemController.startTimeController();
        luringGemTimer = luringGemController.getTimer();
        System.out.println("Applying Luring Gem effect in direction: " + direction);
    }
}