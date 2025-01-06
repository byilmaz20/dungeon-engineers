package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.GameObjects.WizardMonster;
import src.GameObjects.WizardMonsterBehavior.DisappearAction;
import src.GameObjects.WizardMonsterBehavior.IWizardBehavior;
import src.GameObjects.WizardMonsterBehavior.TeleportHeroAction;
import src.GameObjects.WizardMonsterBehavior.TeleportRuneAction;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

public class WizardTimeController implements ITimeControllers {
    private Timer timer;
    private boolean isPaused;
    private double intitialTime;
    private Rune rune;
    private WizardMonster wizard;
    private IWizardBehavior wizardBehavior;

    private double lastRuneSpawnTime;

    private final double RuneStartDelay = 5.0;
    GridEnvironment grid;

    public WizardTimeController(GridEnvironment grid, WizardMonster wizard) {
        this.wizard = wizard;
        this.timer = new Timer();
        this.isPaused = false;
        this.lastRuneSpawnTime = -RuneStartDelay;
        this.grid = grid;
        this.intitialTime = grid.getHall().getObstacles().size() * 5;
        this.rune = grid.getRune();
        grid.addTimeController(this);
    }

    public Timer getTimer() {
        return timer;
    }

    public void startTimeController() {
        timer.startTimer(intitialTime, this::checkMechanics, this::printStatus);
    }
    public void setWizardBehavior(IWizardBehavior wizardBehavior) {
        this.wizardBehavior = wizardBehavior;
    }

    private void checkMechanics() {
        //todo time kontrolleri genel time controllerda olsun
        double mainRemainingTime = Math.floor(grid.getMainTimeController().getTimer().getRemainingTime());
        double mainInitialTime = grid.getMainTimeController().getInitialTime();

        //System.out.printf("Checking Mechanics - Elapsed Time until wizard spawn: %.0f\n", elapsedTime);
        if (mainRemainingTime > mainInitialTime * 0.7){
            setWizardBehavior(new TeleportRuneAction());
            //System.out.println("Behavior set to Teleport Rune");
        }
        if (mainRemainingTime <= mainInitialTime * 0.7 && mainRemainingTime >= mainInitialTime * 0.3){
            setWizardBehavior(new DisappearAction());
            //System.out.println("Behavior set to Disappear");
        }
        if (mainRemainingTime < mainInitialTime * 0.3){
            setWizardBehavior(new TeleportHeroAction());
            //System.out.println("Behavior set to Teleport Hero, Remaining Time: " + mainRemainingTime + " Initial Time: " + mainInitialTime
            //+ "elapsed time: " + grid.getMainTimeController().getTimer().getRemainingTime());
        }
        wizardBehavior.takeAction(this);
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


        Monster monster = new Monster(position2, hall);
        WizardMonster wizard = new WizardMonster(position3, hall, grid);
        Hero hero  = new Hero(position3, hall);
        Rune rune = new Rune(position, hall);
        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(monster);
        hall.placeEntity(wizard);
        
        System.out.println(hall);

        TimeController controller = new TimeController(grid);
        controller.startGame();
    }

    public double getElapsedTime() {
        return timer.getElapsedTime();
    }

    public double getLastRuneSpawnTime() {
        return lastRuneSpawnTime;
    }

    public double getRuneStartDelay() {
        return RuneStartDelay;
    }

    public WizardMonster getWizard() {
        return wizard;
    }

    public GridEnvironment getGrid() {
        return grid;
    }
}
