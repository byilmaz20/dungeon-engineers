package GameObjects;
abstract class Enchantment {
    EnchantmentType type;
    boolean isStorable;
    int duration;
    Point position;
    Enchantment(EnchantmentType type, bool isStorable, int duration, Point position){
        this.type = type;
        this.isStorable = isStorable;
        this.duration = duration;
        this.position = position;
    }
    public void CollectEnchantment(){
        // Collect the enchantment
    }
    // Abstract method applyEffect
    public abstract void applyEffect();
    public void removeEnchantment(){
        // Remove the enchantment
    }
    public void addItem(Enchantment enchantment){
        // Add an item to the enchantment
    }
    public void decreaseItem(EnchantmentType enchantmentType){
        // Decrease the item
    }
}