package src.Mechanics;

import java.util.function.Consumer;

public class Timer {
    private int remainingTime; // Kullanıcının gördüğü zaman
    private int elapsedTime;   // İlerleyen zaman
    private boolean isPaused;
    private Thread timerThread;    // Zamanlayıcıyı kontrol eden thread




    public Timer() {
        this.isPaused = false;
    }

    public void startTimer(int initialTime, Runnable mechanicsCallback, Consumer<Integer> tickCallback) {
        this.remainingTime = initialTime;
        this.elapsedTime = 0;
        this.isPaused = false;

        timerThread = new Thread(() -> {
            mechanicsCallback.run();        // İlk mekanikleri çalıştır
            tickCallback.accept(remainingTime); // İlk kalan süreyi bildir

            while (remainingTime > 0) {
                try {
                    Thread.sleep(1000); // 1 saniye bekle
                    if (!isPaused) {
                        remainingTime--;    // Sadece remainingTime azaltılır
                        elapsedTime++;      // elapsedTime artar
                        mechanicsCallback.run();
                        tickCallback.accept(remainingTime);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Timer Finished!");
        });
        timerThread.start();
    }

    public void addTime(int seconds) {
        remainingTime += seconds; // remainingTime doğrudan güncellenir
        System.out.println("Remaining Time Increased by: " + seconds + " seconds");
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void pauseTimer() {
        isPaused = true;
        System.out.println("Timer Paused");
    }

    public void resumeTimer() {
        isPaused = false;
        System.out.println("Timer Resumed");
    }

    public void stopTimer() {
        if (timerThread != null) {
            timerThread.interrupt();
            System.out.println("Timer Stopped");
        }
    }
}