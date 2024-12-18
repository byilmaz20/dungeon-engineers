package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Enchantment;
import src.Mechanics.Timer;

public class GameController {
    Hall currentHall;
    Enchantment selectedEnchantment;
    String selectedDirection;
    boolean  isPaused;

    private Timer timer; // Zamanlayıcı nesnesi
    private int lastEnchantmentTime;
    private int lastMonsterSpawnTime;

    
    public GameController() {
        timer = new Timer();
        isPaused = false;
        lastEnchantmentTime = 0;
        lastMonsterSpawnTime = 0;
    }


    public void startGame(HallTypes hallType) {
        currentHall = new Hall(hallType);
        System.out.println("Starting Hall: " + hallType);
        int hallTime = getHallTime(hallType);
    
        // Sıra: mechanicsCallback -> tickCallback
        timer.startTimer(hallTime, this::checkMechanics, this::printRemainingTime);
    }
    
 
   
    private void checkMechanics() {
        int elapsedTime = timer.getElapsedTime();
    
        // 7 saniyede bir canavar spawn et
        if (elapsedTime - lastMonsterSpawnTime >= 7) {
            lastMonsterSpawnTime = elapsedTime;
            System.out.println("A new monster has been spawned!");
        }
    
        // 12 saniyede bir enchantment ekle ve 5 saniye zaman ekle
        if (elapsedTime - lastEnchantmentTime >= 12) {
            lastEnchantmentTime = elapsedTime;
            System.out.println("An enchantment appeared!");
            timer.addTime(5); // remainingTime'a doğrudan zaman eklenir
        }
    }
    

    private void printRemainingTime(int remainingTime) {
        System.out.println("Remaining Time: " + remainingTime);
    }

    /**
     * Oyunu duraklatır veya devam ettirir.
     */
    public void pressPauseButton() {
        if (isPaused) {
            timer.resumeTimer();
        } else {
            timer.pauseTimer();
        }
        isPaused = !isPaused;
    }

    private int getHallTime(HallTypes hallType) {
        switch (hallType) {
            case EARTH: return 30;
            case AIR: return 40;
            case WATER: return 50;
            case FIRE: return 60;
            default: return 0;
        }
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


    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startGame(HallTypes.EARTH);

        try {
            Thread.sleep(10000);
            controller.pressPauseButton(); // Pause
            Thread.sleep(5000);
            controller.pressPauseButton(); // Resume
            Thread.sleep(15000); // Oyunun devam etmesini gözlemle
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
   
    
}

