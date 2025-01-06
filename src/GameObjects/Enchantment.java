package src.GameObjects;
import src.Mechanics.PositionPoint;
import java.util.Random;


public class Enchantment extends Entity{ 
    
    EnchantmentTypes type;
    boolean  isStorable;
    int duration;

    
    public Enchantment(PositionPoint position, Hall hall) {
        super(position, hall);
        this.type = selectRandomEnchantment();
        this.isStorable = false;
        this.duration = 6;
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
        // Collect the enchantment
    }
    // Abstract method applyEffect
    public void applyEffect(){
        // Apply the effect of the enchantment
    }
    public void removeEnchantment(){
        // Remove the enchantment
    }
    public void removeEnchantmentFromGrid(){
        // Remove the enchantment
    }
    public void addItem(Enchantment enchantment){
        // Add an item to the enchantment
    }
    public void decreaseItem(EnchantmentTypes enchantmentType){
        // Decrease the item
    }
    
}