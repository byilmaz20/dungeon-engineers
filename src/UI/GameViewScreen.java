package src.UI;
import javax.swing.text.html.parser.Entity;

import src.GameObjects.Enchantment;
import src.GameObjects.Inventory;
import src.Mechanics.PositionPoint;

public class GameViewScreen {
    PositionPoint playerPosition;
    Inventory inventory;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;

    //press on Pause pausecontroller 


    public void addObjectToScreen(Entity entity, PositionPoint position) {
    } 
    public void removeObjectFromScreen(Entity entity) {
    }
    public void moveObjectOnScreen(Entity entity, PositionPoint newPosition) {
    }

    public void displayVisualFeedback() {
    }
    public void notifyLifeLost() {
    }
    public void updatePlayerPosition() {
    }
    public void applyEnchantmentEffects(Enchantment enchantment) {
    }
    public void startEnchantmentTimer(Enchantment enchantment) {
    }
    public void openDoor() {
    }
    public void initializeNewHall() {
    }
    public void displayRuneFound() {
    }
    public void displayInventory() {
    }
}
