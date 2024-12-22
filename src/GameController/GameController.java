package src.GameController;

import src.Mechanics.Timer;

public class GameController {
    private Timer timer;
    private boolean isPaused;

    private double lastMonsterSpawnTime;
    private double lastEnchantmentTime;

    private final double monsterStartDelay = 6.0;
    private final double enchantmentStartDelay = 12.0;

    public GameController() {
        this.timer = new Timer();
        this.isPaused = false;
        this.lastMonsterSpawnTime = -monsterStartDelay;
        this.lastEnchantmentTime = -enchantmentStartDelay;
    }

    public void startGame() {
        timer.startTimer(30.0, this::checkMechanics, this::printStatus);
    }

    private void checkMechanics() {
        double elapsedTime = Math.floor(timer.getElapsedTime());
        System.out.printf("Checking Mechanics - Elapsed Time: %.0f\n", elapsedTime);

        if (elapsedTime >= monsterStartDelay && elapsedTime - lastMonsterSpawnTime >= 6.0) {
            lastMonsterSpawnTime = elapsedTime;
            System.out.println("A new monster has been spawned!");
        }

        if (elapsedTime >= enchantmentStartDelay && elapsedTime - lastEnchantmentTime >= 12.0) {
            lastEnchantmentTime = elapsedTime;
            System.out.println("An enchantment appeared!");
            timer.addTime(5.0);
            System.out.printf("Remaining Time After Enchantment: %d seconds\n", (int) Math.ceil(timer.getRemainingTime()));
        }
    }

    private void printStatus(int remainingTime) {
        double elapsedTime = Math.floor(timer.getElapsedTime());
        System.out.printf("Remaining Time: %d, Elapsed Time: %.0f\n", remainingTime, elapsedTime);
    }

    public void pressPauseButton() {
        if (isPaused) {
            timer.resumeTimer();
            System.out.println("Game Resumed!");
        } else {
            timer.pauseTimer();
            System.out.println("Game Paused!");
        }
        isPaused = !isPaused;
    }

    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startGame();

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
        }
    }
}
