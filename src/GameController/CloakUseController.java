package src.GameController;

import src.Mechanics.GridEnvironment;
import src.Mechanics.Timer;

public class CloakUseController implements ITimeControllers {
    private Timer timer;
    private boolean isPaused;
    private double intitialTime;
    private boolean isProtectionActive;

    private final double CloakTime = 20.0;
    GridEnvironment grid;

    public CloakUseController(GridEnvironment grid) {

        this.timer = new Timer();
        this.isPaused = false;
        this.isProtectionActive = true;
        this.grid = grid;
        this.intitialTime = grid.getHall().getObstacles().size() * 5;
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
    public void applyTimeEchantment(String mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyTimeEchantment'");
    }
}