package src.GameObjects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inventory {
        private final List<Enchantment> items;

    private final Map<EnchantmentTypes, Integer> enchantmentQuantities;
        private Runnable inventoryChangeListener; // Listener for inventory changes

    public Inventory() {
        this(0, 0, 0); 
    }

    public Inventory(int cloakCount, int revealCount, int luringGemCount) {
        items = new ArrayList<>();
        enchantmentQuantities = new HashMap<>();
        enchantmentQuantities.put(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT, cloakCount);
        enchantmentQuantities.put(EnchantmentTypes.REVEAL_ENCHANTMENT, revealCount);
        enchantmentQuantities.put(EnchantmentTypes.LURING_GEM_ENCHANTMENT, luringGemCount);

    }
    
    // public void addInventory(){

    // }
    // public void checkAvailability(enchantment){

    // }
    // public void fetchInventoryData(){

    // }
    public List<Enchantment> getItems() {
        return items;
    }
    public void add(Enchantment enchantment) {
        items.add(enchantment);

        EnchantmentTypes type = enchantment.getType(); 
        if (enchantmentQuantities.containsKey(type)) {
            enchantmentQuantities.put(type, enchantmentQuantities.get(type) + 1);
            System.out.println("Added " + type);
        }
                notifyChangeListener();

    }
    public void add(EnchantmentTypes type) {
        if (enchantmentQuantities.containsKey(type)) {
            enchantmentQuantities.put(type, enchantmentQuantities.get(type) + 1);
            System.out.println("Added " + type);
        }
                notifyChangeListener();

    }

    public void remove(Enchantment enchantment) {
        items.remove(enchantment);
        EnchantmentTypes type = enchantment.getType();
        if (enchantmentQuantities.containsKey(type)) {
            enchantmentQuantities.put(type, Math.max(0, enchantmentQuantities.get(type) - 1));
        }
                notifyChangeListener();

    }
    public void remove(EnchantmentTypes type) {
        if (enchantmentQuantities.containsKey(type)) {
            enchantmentQuantities.put(type, Math.max(0, enchantmentQuantities.get(type) - 1));
        }
                notifyChangeListener();

    }
    public boolean checkAvailability(EnchantmentTypes type) {
        return enchantmentQuantities.getOrDefault(type, 0) > 0;
    }

    public int getQuantity(EnchantmentTypes type) {
        return enchantmentQuantities.getOrDefault(type, 0);
    }
    public List<Integer> fetchInventoryData(){
        List<Integer> counts = new ArrayList<>();

        // order burdan belirlenecek.
        counts.add(enchantmentQuantities.get(EnchantmentTypes.CLOAK_OF_PROTECTION_ENCHANTMENT));
        counts.add(enchantmentQuantities.get(EnchantmentTypes.REVEAL_ENCHANTMENT));
        counts.add(enchantmentQuantities.get(EnchantmentTypes.LURING_GEM_ENCHANTMENT));

        return counts;
    }
    @Override
    public String toString() {
        return "Inventory: " + enchantmentQuantities;
    }
    public void setInventoryChangeListener(Runnable listener) {
        this.inventoryChangeListener = listener;
    }

    private void notifyChangeListener() {
        if (inventoryChangeListener != null) {
            inventoryChangeListener.run();
        }
    }
    




}
