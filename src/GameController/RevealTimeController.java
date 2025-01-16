package src.GameController;
import java.io.Serializable;

import src.GameObjects.Enchantment;
import src.GameObjects.RevealEnchantment;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;
import src.UI.PlayModeScreen;

public class RevealTimeController implements ITimeControllers,Serializable {
    private Timer timer;
    private boolean isPaused;
    private double initialTime;
    private double lastRuneCheck;
    private double remainingTimeLoaded;
    private final double RevealTime = 10.0;
    GridEnvironment grid;
    PlayModeScreen playModeScreen;
    PositionPoint topLeft;
    Boolean tenSecPassed;
    PositionPoint runePosition;

    
    public RevealTimeController(GridEnvironment grid, PlayModeScreen playModeScreen, PositionPoint topLeft) {
        this.timer = new Timer();
        this.isPaused = false;
        this.grid = grid;
        this.playModeScreen = playModeScreen;
        this.topLeft= topLeft;
        this.tenSecPassed = false;
        this.lastRuneCheck = 0.0;
        this.runePosition = grid.getRune().getPosition();
        this.initialTime = grid.getHall().getObstacles().size() * 5;
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
        if (elapsedTime - lastRuneCheck >= 0.5 && !tenSecPassed )  {
            lastRuneCheck = elapsedTime;
        }
        if (!grid.getRune().getPosition().equals(runePosition))  {
            playModeScreen.applyRedTint(topLeft, false);
            runePosition = grid.getRune().getPosition();
            PositionPoint topLeft = RevealEnchantment.getSquareTopLeft(grid);
            playModeScreen.applyRedTint(topLeft, true);
            lastRuneCheck = elapsedTime;
        }
        if (elapsedTime >= RevealTime  && !tenSecPassed) {
            playModeScreen.applyRedTint(topLeft, false);
            System.out.println("Reveal Enchantment effect has ended.");
            tenSecPassed = true;
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
        // TODO Auto-generated method stub;
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
}