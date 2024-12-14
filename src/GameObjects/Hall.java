package src.GameObjects;
import java.util.List;
import src.GameObjects.GameObject;
import src.GameObjects.Monster;
import src.GameObjects.Enchantment;
import src.GameObjects.Rune;


public class Hall {
    HallTypes hallType;
    List<Entity> entities;
    Rune rune;
    int minimumObjectsRequired;

    public Hall(HallTypes hallType) {
        this.hallType = hallType;
        switch (hallType) {
            case EARTH:
                minimumObjectsRequired = 6;                
                break;
            case AIR:
                minimumObjectsRequired = 9;
                break;
            case WATER:
                minimumObjectsRequired = 13;
                break;
            case FIRE:
                minimumObjectsRequired = 17;
                break;
            default:
                break;
        }
    }

    public void placeEntity(Entity entity) {
        // Place object in hall
    }

    public boolean checkObjectRequirements() {
        if (entities.size() >= minimumObjectsRequired) {
            return true;
        }
        return false;
    }

    public void selectRandomLocation() {
        // Select random location in hall
    }

    public void addMonsterToHall() {
        // Add monster to hall
    }
}