package src.GameController;

import src.GameObjects.Enchantment;
import src.GameObjects.FighterMonster;
import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;

public class EnchantmentTimeController implements ITimeControllers {
    private Timer timer;
    private boolean isPaused;
    private double intitialTime;
    private Enchantment enchantment;


    private final double EnchantmentRemoveDelay = 6.0;
    GridEnvironment grid;


    public EnchantmentTimeController(GridEnvironment grid, Enchantment enchantment) {
        this.enchantment = enchantment;
        this.timer = new Timer();
        this.isPaused = false;
        this.grid = grid;
        this.intitialTime = 10.0;
        grid.addTimeController(this);

    }

    public Timer getTimer() {
        return timer;
    }

    public void startTimeController() {
        timer.startTimer(intitialTime, this::checkMechanics, this::printStatus);
    }


    private void checkMechanics() {
        double elapsedTime = Math.floor(timer.getElapsedTime());

        if (elapsedTime >= EnchantmentRemoveDelay ) {
            grid.removeEnchantmentFromGrid(enchantment);
            
            
        }
        
    }

    private void printStatus(int remainingTime) { 
        double elapsedTime = Math.floor(timer.getElapsedTime());
    }

    public void pressPauseButton() {
        if (isPaused) {
            timer.resumeTimer();
        } else {
            timer.pauseTimer();
        }
        isPaused = !isPaused;
    }
    
    public void applyTimeEchantment() {
        timer.addTime(5.0);
        System.out.printf("Remaining Time Increased by 5 seconds\n");
    }
}
