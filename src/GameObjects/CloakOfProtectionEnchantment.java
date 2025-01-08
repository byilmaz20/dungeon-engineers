package src.GameObjects;
import java.io.Serializable;

import src.GameController.CloakUseController;
import src.GameController.EnchantmentTimeController;
import src.GameObjects.EnchantmentTypes;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class CloakOfProtectionEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private CloakUseController cloakUseController;
    private Timer cloakUseTimer;
    private Timer enchantmentTimer;
    private GridEnvironment grid;
    public CloakOfProtectionEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT;
        this.isStorable = true;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        enchantmentTimer = enchantmentTimeController.getTimer();
        this.cloakUseController = new CloakUseController(grid);
    }
    
    public void applyEffect(){
        grid.getHero().activateProtection();
        System.out.println("Cloak of Protection effect has been applied.");
        cloakUseController.startTimeController();
        cloakUseTimer = cloakUseController.getTimer();
    }
    
    public void removeEffect(){
        // Remove the effect of the enchantment
    }
    
    public void die(){
        // Die
    }
    

}