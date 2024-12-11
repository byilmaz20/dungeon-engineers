package GameObjects;
class CloakOfProtectionEnchantment extends Enchantment {
    CloakOfProtectionEnchantment(EnchantmentType type, bool isStorable, int duration, Point position){
        super(type, isStorable, duration, position);
    }
    public void applyEffect(){
        // Apply the effect of the enchantment
    }
    public void removeEffect(){
        // Remove the effect of the enchantment
    }
    public void die(){
        // Die
    }

}