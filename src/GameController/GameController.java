package src.GameController;
import src.GameObjects.Hall;
import src.GameObjects.Enchantment;

public class GameController {
    Hall currentHall;
    Enchantment selectedEnchantment;
    String selectedDirection;
    boolean  isPaused;
    public GameController() {
    }

    //for 10k t SPAWNCONTROLLER.spawnrandomMONter()

    public void updateHall() {
    }
    public void leftClick() {
    } //???
    public void checkType() {
    }
    public void pressArrowKey(String direction) {
        selectedDirection = direction;
    }
    public void pressKeyboard(Enchantment enchantment) {
        selectedEnchantment = enchantment;
    }
    public void pressPauseButton() {
        isPaused = !isPaused;
    }
    public void findRune() {
    }
    public void clickObject() {
    }
    public void clickInventoryBag() {
    }
    public void initiliazeBuildModer() {
    }
    public void hello(){
        
    }
}