package GameObjects;
class LuringGemEnchantment extends Enchantment {
    public LuringGemEnchantment(EnchantmentType type, bool isStorable, int duration, Point position){
        super(type, isStorable, duration, position);
    }
    public void applyEffect(monster: Monster, direction: Direction): void{
        // Apply the effect of the enchantment
    }
}