package src.GameObjects;
import java.io.Serializable;

import src.GameController.CloakUseTimeController;
import src.GameController.EnchantmentTimeController;
import src.GameObjects.EnchantmentTypes;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
//import src.Mechanics.SoundManager;

public class CloakOfProtectionEnchantment extends Enchantment implements Serializable{
    private EnchantmentTimeController enchantmentTimeController;
    private CloakUseTimeController cloakUseController;
    private GridEnvironment grid;
    //transient private SoundManager buttonCollectEnchantmentSound;


    public CloakOfProtectionEnchantment(PositionPoint position, Hall hall, GridEnvironment grid) {
        super(position, hall);
        this.type = EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT;
        this.isStorable = true;
        this.enchantmentTimeController = new EnchantmentTimeController(grid, this);
        this.grid = grid;
        enchantmentTimeController.startTimeController();
        this.cloakUseController = new CloakUseTimeController(grid);
       // buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");

    }
    
    public void applyEffect(){
        grid.getHero().activateProtection();
        //buttonCollectEnchantmentSound.playSound();
        System.out.println("Cloak of Protection effect has been applied.");
        cloakUseController.startTimeController();
    }
    
    public void removeEffect(){
        // Remove the effect of the enchantment
    }
    
    public void die(){
        // Die
    }
    

}