package src.GameController;

import java.io.Serializable;

import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;

public class LuringGemTimeController implements ITimeControllers,Serializable {
    private Timer timer;
    private boolean isPaused;
    private double initialTime;
    private boolean isGemActive;


    private final double FoolingTime = 5.0;
    GridEnvironment grid;

    public LuringGemTimeController(GridEnvironment grid) {

        this.timer = new Timer();
        this.isPaused = false;
        this.isGemActive = true;
        this.grid = grid;
        String mode = GameModeController.getInstance().getGameMode();
        if (mode.equals("easy")) {
            this.initialTime = grid.getHall().getObstacles().size() * 5 * 1.2;
        } else if (mode.equals("hard")) {
            this.initialTime = grid.getHall().getObstacles().size() * 5;
        } else {
            assert false : "Invalid mode";
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

        if (elapsedTime >= FoolingTime && isGemActive) {
            grid.getHero().deactivateFooling();
            isGemActive = false;
            System.out.println("Luring gem controller has been deactivated.");

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
        } else if (mode.equals("hard")){
            timer.addTime(5.0);
        } else {
            assert false : "Invalid mode";
        }
    }
    public void startTimeController(double remainingTimeLoaded) {
        timer = new Timer();
        timer.startTimer(remainingTimeLoaded, this::checkMechanics, this::printStatus);
        initialTime = remainingTimeLoaded;
        this.isPaused = false;
    }
    private double remainingTimeLoaded;
    public double disposeTimer() {
        remainingTimeLoaded = timer.disposeTimer();
        this.timer = null;
        return remainingTimeLoaded;
    }
    public double getRemainingTimeLoaded() {
        return remainingTimeLoaded;
    }

}