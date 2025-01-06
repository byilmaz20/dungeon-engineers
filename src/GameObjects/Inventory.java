package src.GameObjects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Inventory implements Serializable{
    private final List<Enchantment> items;

    public Inventory() {
        items = new ArrayList<>();
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
    }
    public void remove(Enchantment enchantment) {
        items.remove(enchantment);
    }
}
