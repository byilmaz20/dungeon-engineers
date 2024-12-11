package src.GameController;

public class GameController {
    Hall currentHall;
    Enchantment selectedEnchantment;
    String selectedDirection;
    boolean isPaused;
    public GameController() {
        currentHall = new Hall();
        selectedEnchantment = new Enchantment();
        selectedDirection = "";
        isPaused = false;
    }
    public void initiliazeBuildMode() {
        currentHall.initiliazeBuildMode();
    }
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
/* 
initiliazeBuildMode()
updateHall()
leftClick()
checkType()
pressArrowKey(direction)
pressKeyboard(enchantment)
pressPauseButton()
findRune()
clickObject()
clickInventoryBag()
initiliazeBuildModer()
 */