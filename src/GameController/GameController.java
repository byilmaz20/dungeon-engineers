package src.GameController;

import src.Mechanics.Timer;

public class GameController {
    private Timer timer;
    private double lastEnchantmentTime;
    private double lastMonsterSpawnTime;
    private boolean isPaused;

    public GameController() {
        this.timer = new Timer();
        this.lastEnchantmentTime = 0.0;
        this.lastMonsterSpawnTime = 0.0;
        this.isPaused = false;
    }

    public void startGame() {
        timer.startTimer(30.0, this::checkMechanics, this::printStatus);
    }

    private void checkMechanics() {
        double elapsedTime = timer.getElapsedTime();

        if (elapsedTime - lastMonsterSpawnTime >= 7.0) {
            lastMonsterSpawnTime = elapsedTime;
            System.out.println("A new monster has been spawned!");
        }

        if (elapsedTime - lastEnchantmentTime >= 12.0) {
            lastEnchantmentTime = elapsedTime;
            System.out.println("An enchantment appeared!");
            timer.addTime(5.0);
        }
    }

    private void printStatus(int remainingTime) {
        double elapsedTime = timer.getElapsedTime();
        System.out.printf("Remaining Time: %d, Elapsed Time: %.1f\n", remainingTime, elapsedTime);
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
            Thread.sleep(5000); // 5 saniye çalışsın
            controller.pressPauseButton(); // Pause
            Thread.sleep(3000); // 3 saniye bekle
            controller.pressPauseButton(); // Resume
            Thread.sleep(7000); // 7 saniye çalışsın
            controller.pressPauseButton(); // Pause
            Thread.sleep(2000); // 2 saniye bekle
            controller.pressPauseButton(); // Resume
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} 