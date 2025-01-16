package src.GameController;

import java.io.Serializable;

import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;

public class CloakUseTimeController implements ITimeControllers, Serializable {
    private Timer timer;
    private boolean isPaused;
    private double initialTime;
    private boolean isProtectionActive;
    private double remainingTimeLoaded;

    private final double CloakTime = 20.0;
    GridEnvironment grid;

    public CloakUseTimeController(GridEnvironment grid) {
        String mode = GameModeController.getInstance().getGameMode();
        this.timer = new Timer();
        this.isPaused = false;
        this.isProtectionActive = true;
        this.grid = grid;
        if (mode.equals("easy")) {
            this.initialTime = grid.getHall().getObstacles().size() * 5 * 1.2;
        } else if (mode.equals("hard")) {
            this.initialTime = grid.getHall().getObstacles().size() * 5;
        } else {
            throw new IllegalArgumentException("Invalid game mode");
        }
        
        grid.addTimeController(this);
    }

    public Timer getTimer() {
        return timer;
    }

    public void startTimeController() {
        timer.startTimer(initialTime, this::checkMechanics, this::printStatus);
    }

    private void checkMechanics() {
        double elapsedTime = Math.floor(timer.getElapsedTime());

        if (elapsedTime >= CloakTime && isProtectionActive) {
            grid.getHero().deactivateProtection();
            isProtectionActive = false;
            System.out.println("Cloak of Protection effect has ended.");

        }

    }

    private void printStatus(int remainingTime) { 
        double elapsedTime = Math.floor(timer.getElapsedTime());
        //System.out.printf("Remaining Time: %d, Elapsed Time: %.0f\n", remainingTime, elapsedTime);
    }


    public void pressPauseButton() {
        if (isPaused) {
            timer.resumeTimer();
        } else {
            timer.pauseTimer();
        }
        isPaused = !isPaused;
    }

    @Override
    public void applyTimeEchantment() {
        String mode = GameModeController.getInstance().getGameMode();
        if (mode.equals("easy")){
            timer.addTime(10.0);
            //System.out.printf("easy mode in enchantment controller: Remaining Time Increased by 10 seconds\n");
        } else if (mode.equals("hard")){
            timer.addTime(5.0);
            //System.out.printf("hard mode in enchantment controller: Remaining Time Increased by 5 seconds\n");
        } else {
            assert false : "Invalid mode";
        }
    }

    public void startTimeController(double remainingTimeLoaded) {
        this.timer = new Timer();
        this.isPaused = false;
        initialTime = remainingTimeLoaded;
        timer.startTimer(remainingTimeLoaded, this::checkMechanics, this::printStatus);
    }
    
    public double disposeTimer() {
        remainingTimeLoaded = timer.disposeTimer();
        this.timer = null;
        return remainingTimeLoaded;
    }
    public double getRemainingTimeLoaded() {
        return remainingTimeLoaded;
    }
    public void setGrid(GridEnvironment grid) {
        this.grid = grid;
    }
    
}