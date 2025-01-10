package src.GameObjects;
import src.Mechanics.PositionPoint;

import java.io.Serializable;
import java.util.Random;
import src.Mechanics.SoundManager;



public class Enchantment extends Entity implements Serializable{ 
    
    EnchantmentTypes type;
    boolean  isStorable;
    int duration;
    private SoundManager buttonCollectEnchantmentSound;

    
    public Enchantment(PositionPoint position, Hall hall) {
        super(position, hall);
        this.type = selectRandomEnchantment();
        this.isStorable = false;
        this.duration = 6;
        buttonCollectEnchantmentSound = new SoundManager("src/voices/CollectEnchantment.wav");
    }

    public static EnchantmentTypes selectRandomEnchantment() {
        EnchantmentTypes[] enchantmentTypes = EnchantmentTypes.values(); // Get all enchantment types
        Random random = new Random();
        return enchantmentTypes[random.nextInt(enchantmentTypes.length)];
    }
    

    public EnchantmentTypes getType() {
        return this.type;
    }
    public void CollectEnchantment(){
        buttonCollectEnchantmentSound.playSound();
        // Collect the enchantment
    }
    // Abstract method applyEffect
    public void applyEffect(){
        buttonCollectEnchantmentSound.playSound();
        // Apply the effect of the enchantment
    }
    public void removeEnchantment(){
        // Remove the enchantment
        //buttonCollectEnchantmentSound.playSound();
    }

    public void removeEnchantmentFromGrid(){
        //buttonCollectEnchantmentSound.playSound();
        // Remove the enchantment
    }

    public void addItem(Enchantment enchantment){
        buttonCollectEnchantmentSound.playSound();
        // Add an item to the enchantment
    }
    public void decreaseItem(EnchantmentTypes enchantmentType){
        // Decrease the item
        buttonCollectEnchantmentSound.playSound();
    }
    
}