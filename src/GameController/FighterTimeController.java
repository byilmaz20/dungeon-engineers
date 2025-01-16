package src.GameController;

import java.io.Serializable;

import src.GameObjects.FighterMonster;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class FighterTimeController implements ITimeControllers, Serializable {
    private Timer timer;
    private boolean isPaused;
    private double initialTime;
    private FighterMonster fighter;

    private double lastFighterMoveTime;

    private final double FighterStartDelay = 1.0;
    GridEnvironment grid;

    public FighterTimeController(GridEnvironment grid, FighterMonster fighter) {
        this.fighter = fighter;
        this.timer = new Timer();
        this.isPaused = false;
        this.lastFighterMoveTime = -FighterStartDelay;
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
        //System.out.println("Checking Mechanics for fighter" + elapsedTime + lastFighterMoveTime);
        //System.out.printf("Checking Mechanics - Elapsed Time until fighter spawn: %.0f\n", elapsedTime);

        if (elapsedTime >= FighterStartDelay && elapsedTime - lastFighterMoveTime >= 1.0 && grid.getHero().checkFooling()==false) {
            fighter.moveRandomly(grid);
            lastFighterMoveTime = elapsedTime;
            //System.out.println("Fighter has been moved randomly!");
        }
        else if (elapsedTime >= FighterStartDelay && elapsedTime - lastFighterMoveTime >= 1.0 && grid.getHero().checkFooling() && grid.getHero().getLureDirection()!=null) {
            grid.moveEntity(fighter, grid.getHero().getLureDirection());
            lastFighterMoveTime = elapsedTime;
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
        this.timer = new Timer();
        lastFighterMoveTime = -FighterStartDelay;
        this.isPaused = false;
        initialTime = remainingTimeLoaded;
        timer.startTimer(remainingTimeLoaded, this::checkMechanics, this::printStatus);
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
    public void setGrid(GridEnvironment grid) {
        this.grid = grid;
    }
}
