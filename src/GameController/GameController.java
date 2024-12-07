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
    
}
/* 
currentHall: Hall
- selectedEnchantment: Enchantment
- selectedDirection: string
- isPaused: bool
0..
*
Coordinates with
- isGameRunning: bool
- monsters: List<Monster>
- player: Player
- currentHall: Hall
runGame()
verifyRandomLocation()
updateHall()
checkGameOver()
0..
*
0..
*
0..
*
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