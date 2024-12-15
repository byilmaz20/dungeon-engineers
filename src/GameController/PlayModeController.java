package src.GameController;

import src.GameObjects.Enchantment;
import src.GameObjects.Entity;
import src.GameObjects.Hero;
import src.GameObjects.Inventory;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;

public class PlayModeController {
    Hero hero;
    Inventory inventory;
    Enchantment activeEnchantment;
    boolean  isDoorOpen;
    GridEnvironment grid;
    public void addObjectToScreen(Entity entity, PositionPoint position) {
        grid.addEntity(entity, position);
    } 
    public void removeObjectFromScreen(Entity entity) {
        grid.removeEntity(entity);
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

    public void pauseGame() {
    }
    public void resumeGame() {
    }
    public void movePlayer() {
    }
    public void applyEnchantment() {
    }

    public void startEnchantmentTimer() {
    }
    public void displayRuneFound() {
    }
    public void displayInventory() {
    }

    public void initializeNewHall() {
    }
    public void addObjectToScreen() {
    }
    public void removeObjectFromScreen() {
    }
    public void moveObjectOnScreen() {
    }
    public void checkMovement() {
    }
    public void moveHero() {
    }
    public void isRuneFound() {
    }
    public void updateGameState() {
    }
    public void addEntity() {
    }
    public void removeEntity() {
    }
    public void moveEntity() {
    }
    public void addInventory() {
    }
    public void checkAvailability() {
    }
    public void fetchInventoryData() {
    }
}
