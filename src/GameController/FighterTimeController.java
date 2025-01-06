package src.GameController;

import src.GameObjects.FighterMonster;
import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class FighterTimeController implements ITimeControllers {
    private Timer timer;
    private boolean isPaused;
    private double intitialTime;
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
        //System.out.printf("Checking Mechanics - Elapsed Time until fighter spawn: %.0f\n", elapsedTime);

        if (elapsedTime >= FighterStartDelay && elapsedTime - lastFighterMoveTime >= 1.0 && grid.getHero().checkFooling()==false) {
            fighter.moveRandomly(grid);
            lastFighterMoveTime = elapsedTime;
            //System.out.println("Fighter has been moved randomly!");
        }
        else if (elapsedTime >= FighterStartDelay && elapsedTime - lastFighterMoveTime >= 1.0 && grid.getHero().checkFooling() && fighter.getLureDirection()!=null) {
            grid.moveEntity(fighter, fighter.getLureDirection());
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
        timer.addTime(5.0);
        System.out.printf("Remaining Time Increased by 5 seconds\n");
    }

    public static void main(String[] args) {
        Hall hall = new Hall(HallTypes.EARTH);
        PositionPoint position = new PositionPoint(2, 4);
        PositionPoint position2  =new PositionPoint(5, 21);
        PositionPoint position3  =new PositionPoint(21, 14);
        PositionPoint position4  =new PositionPoint(21, 14);
        PositionPoint position5  =new PositionPoint(21, 14);

        Obstacles obstacle = new Obstacles(position4, hall, ObstacleType.CHEST);
        Obstacles obstacle2 = new Obstacles(position5, hall, ObstacleType.CHEST);
        hall.placeEntity(obstacle);
        hall.placeEntity(obstacle2);

        GridEnvironment grid = new GridEnvironment(hall);

        WizardMonster wizard = new WizardMonster(position3, hall, grid);
        FighterMonster fighter = new FighterMonster(position3, hall, grid);
        Hero hero  = new Hero(position3, hall);
        Rune rune = new Rune(position, hall);
        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(wizard);
        hall.placeEntity(fighter);
        
        System.out.println(hall);

        grid.getMainTimeController().startGame();

        TimeController controller = new TimeController(grid);

        controller.startGame();
    }
}
