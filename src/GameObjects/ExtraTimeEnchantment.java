package src.GameObjects;
import java.io.Serializable;

import src.GameController.EnchantmentTimeController;
import src.GameController.ITimeControllers;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
//import src.Mechanics.SoundManager;

public class ExtraTimeEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private GridEnvironment grid;
    //transient private SoundManager buttonCollectEnchantmentSound;

    public ExtraTimeEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.EXTRA_TIME_ENCHANTMENT;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
      //  buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");
    }
    public void applyEffect(){
        for (ITimeControllers timeController : this.grid.getTimeControllers()) {
        //    buttonCollectEnchantmentSound.playSound();
            timeController.applyTimeEchantment();
    }

}
}