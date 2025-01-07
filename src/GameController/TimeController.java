package src.GameController;

import src.GameObjects.Hall;
import src.GameObjects.HallTypes;
import src.GameObjects.Hero;
import src.GameObjects.Monster;
import src.GameObjects.Obstacles;
import src.GameObjects.Obstacles.ObstacleType;
import src.GameObjects.Rune;
import src.Mechanics.GridEnvironment;
import src.Mechanics.PositionPoint;
import src.Mechanics.Timer;

/*
yeni hall olusunca grid gridini kullanarak 
timeController = new TimeController(grid) yapılacak
sonra timeController.startGame() yapılacak
sonra her sey otomatik calisacak
ekranda time gostermek icin de sonradan ekleyecez
ornek kullanım bunun maininde
 */

public class TimeController implements ITimeControllers {
    private Timer timer;
    private boolean isPaused;
    private double initialTime;

    private double lastMonsterSpawnTime;
    private double lastEnchantmentTime;

    private final double monsterStartDelay = 6.0;
    private final double enchantmentStartDelay = 12.0;
    GridEnvironment grid;
    SpawnMonsterController spawner;

    


    public TimeController(GridEnvironment grid) {
        this.timer = new Timer();
        this.isPaused = false;
        this.lastMonsterSpawnTime = -monsterStartDelay;
        this.lastEnchantmentTime = -enchantmentStartDelay;
        this.grid = grid;
        this.initialTime = grid.getHall().getObstacles().size() * 5;
        //todo monster için ayrıca girdi verebilsin
        
        spawner = new SpawnMonsterController(grid);
        grid.addTimeController(this);
    }

    public double getInitialTime() {
        return initialTime;
    }

    public Timer getTimer() {
        return timer;
    }

    public void startGame() {
        timer.startTimer(initialTime, this::checkMechanics, this::printStatus);
    }

    public void setInitialTime(double initialTime) {
        this.initialTime = initialTime;
    }

    private void checkMechanics() {
        double elapsedTime = Math.floor(timer.getElapsedTime());
        //System.out.printf("Checking Mechanics - Elapsed Time: %.0f\n", elapsedTime);

        if (elapsedTime >= monsterStartDelay && elapsedTime - lastMonsterSpawnTime >= 6.0) {
            SpawnMonsterController spawn = new SpawnMonsterController(grid);
            spawn.spawnMonster();
            lastMonsterSpawnTime = elapsedTime;
            
            //System.out.println("A new monster has been spawned!");
        }
        double remainingTime = timer.getRemainingTime();
        if (remainingTime <= 0) {
            System.out.println("Time finished Game Over!");
            GameFlowController.endGame("Time's up!");
            //System.exit(0);
        }

        if (elapsedTime >= enchantmentStartDelay && elapsedTime - lastEnchantmentTime >= 12.0) {
            lastEnchantmentTime = elapsedTime;
            SpawnEnchantmentController enchantmentSpawn = new SpawnEnchantmentController(grid);
            enchantmentSpawn.spawnEnchantment();
            System.out.printf("Remaining Time After Enchantment: %d seconds\n", (int) Math.ceil(timer.getRemainingTime()));
        }
    }

    private void printStatus(int remainingTime) { //todo bunu UI guncellemesi icin tickcallback olarak degistircez
        double elapsedTime = Math.floor(timer.getElapsedTime());
        //System.out.printf("Remaining Time: %d, Elapsed Time: %.0f\n", remainingTime, elapsedTime);
    }

    public void pressPauseButton() {
        if (isPaused) {
            timer.resumeTimer();
            //System.out.println("Game Resumed!");
            
        } else {
            timer.pauseTimer();
            //System.out.println("Game Paused!");
            
        }
        isPaused = !isPaused;
        
    }

    public void applyTimeEchantment(String mode) {
        timer.addTime(5.0);
        System.out.printf("Remaining Time Increased by 5 seconds\n");
    }

    public static void main(String[] args) {
        Hall hall = new Hall(HallTypes.EARTH);
        PositionPoint position = new PositionPoint(2, 4);
        PositionPoint position2  =new PositionPoint(5, 21);
        PositionPoint position3  =new PositionPoint(21, 14);
        PositionPoint position4  =new PositionPoint(21, 14);

        Monster monster = new Monster(position2, hall);
        Hero hero  = new Hero(position3, hall);
        Rune rune = new Rune(position, hall);
        Obstacles obstacle = new Obstacles(position4, hall, ObstacleType.CHEST);
        hall.placeEntity(rune);
        hall.placeEntity(hero);
        hall.placeEntity(monster);
        hall.placeEntity(obstacle);

        GridEnvironment grid = new GridEnvironment(hall);
        System.out.println(hall);

        TimeController controller = new TimeController(grid);
        controller.startGame();

/* 
        try {
            
            Thread.sleep(5000);
            controller.pressPauseButton();
            Thread.sleep(3000);
            controller.pressPauseButton();
            Thread.sleep(7000);
            controller.pressPauseButton();
            Thread.sleep(2000);
            controller.pressPauseButton();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void setInitializeTime(Timer remainingTime){
        this.initialTime = remainingTime.getRemainingTime();
    }
    public synchronized double getElapsedTime() {
        return timer.getElapsedTime();
    }
    public synchronized double getRemainingTime() {
        return timer.getRemainingTime();
    }
}
