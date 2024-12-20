package src.GameObjects;
import src.Mechanics.PositionPoint;


public abstract class Enchantment extends Entity{ 
    
    EnchantmentTypes type;
    boolean  isStorable;
    int duration;
    PositionPoint position;

    
    public Enchantment(EnchantmentTypes type, boolean  isStorable, int duration, PositionPoint position, Hall hall) {
        super(position, hall);
        this.type = type;
        this.isStorable = isStorable;
        this.duration = duration;
        this.position = position;
    }
    public EnchantmentTypes getType() {
        return type;
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
    public void addItem(Enchantment enchantment){
        // Add an item to the enchantment
    }
    public void decreaseItem(EnchantmentTypes enchantmentType){
        // Decrease the item
    }
    
}