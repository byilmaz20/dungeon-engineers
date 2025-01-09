package src.GameObjects;
import java.io.Serializable;

import src.GameController.EnchantmentTimeController;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class ExtraLifeEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    transient private Timer enchantmentTimer;
    private GridEnvironment grid;
    public ExtraLifeEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.EXTRA_LIFE_ENCHANTMENT;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        enchantmentTimer = enchantmentTimeController.getTimer();
    }    




    public void applyEffect(){
        grid.getHero().increaseLifeCount();
    }

}