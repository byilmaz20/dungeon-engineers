package src.GameObjects;
import java.util.List;
import src.GameObjects.GameObject;
import src.GameObjects.Monster;
import src.GameObjects.Enchantment;
import src.GameObjects.Rune;


public class Hall {
    String name; //(Earth, Air, Water, Fire) //TODO: Enum
    List<GameObject> objects;
    List<Monster> monsters;
    List<Enchantment> enchantments;
    Rune rune;
    int minimumObjectsRequired;

    public void placeObject(GameObject object) {
        // Place object in hall
    }

    public void checkObjectRequirements() {
        // Check if all objects are placed
    }

    public void selectRandomLocation() {
        // Select random location in hall
    }

    public void addMonsterToHall() {
        // Add monster to hall
    }
    public void initiliazeBuildMode() {
        // Initialize build mode
    }
}