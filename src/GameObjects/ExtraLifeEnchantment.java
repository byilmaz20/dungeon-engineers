package src.GameObjects;
import java.io.Serializable;

import src.GameController.EnchantmentTimeController;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
//import src.Mechanics.SoundManager;

public class ExtraLifeEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private GridEnvironment grid;
    //transient private SoundManager buttonCollectEnchantmentSound;
    
    public ExtraLifeEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.EXTRA_LIFE_ENCHANTMENT;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
      //  buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");
    }    




    public void applyEffect(){
        //buttonCollectEnchantmentSound.playSound();
        grid.getHero().increaseLifeCount();
    }

}