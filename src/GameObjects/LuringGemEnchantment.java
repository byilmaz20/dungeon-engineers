package src.GameObjects;
import java.io.Serializable;

import src.GameController.EnchantmentTimeController;
import src.GameController.LuringGemTimeController;
import src.Mechanics.Direction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
//import src.Mechanics.SoundManager;

public class LuringGemEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private LuringGemTimeController luringGemController;
    private GridEnvironment grid;
    //transient private SoundManager buttonCollectEnchantmentSound;

    public LuringGemEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
      //  buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");
        this.type = EnchantmentTypes.LURING_GEM_ENCHANTMENT;
        this.isStorable = false;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        this.luringGemController = new LuringGemTimeController(grid);

    }    
    
    public void applyEffect(Direction direction) {
        //buttonCollectEnchantmentSound.playSound();
        grid.getHero().setLureDirection(direction);

        grid.getHero().activateFooling();
        luringGemController.startTimeController();
        System.out.println("Applying Luring Gem effect in direction: " + direction);
    }
}