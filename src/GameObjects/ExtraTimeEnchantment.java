package src.GameObjects;
import src.GameController.EnchantmentTimeController;
import src.GameController.FighterTimeController;
import src.GameController.ITimeControllers;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class ExtraTimeEnchantment extends Enchantment {
    private EnchantmentTimeController enchantmentTimeController;
    private Timer enchantmentTimer;
    private GridEnvironment grid;
    public ExtraTimeEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.EXTRA_TIME_ENCHANTMENT;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        enchantmentTimer = enchantmentTimeController.getTimer();
    }    

    public void applyEffect(){
    for (ITimeControllers timeController : this.grid.getTimeControllers()) {
                timeController.applyTimeEchantment();
            }
    }

}