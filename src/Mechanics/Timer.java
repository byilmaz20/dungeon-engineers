package src.Mechanics;

import java.util.function.Consumer;

public class Timer {
    private double remainingTime;    // Kalan süre
    private double elapsedTime;      // Geçen süre
    private boolean isPaused;        // Duraklatıldı mı?
    private long lastUpdateTime;     // Son güncelleme zamanı
    private Thread timerThread;      // Timer için thread

    public Timer() {
        this.isPaused = false;
    }

    public void startTimer(double initialTime, Runnable mechanicsCallback, Consumer<Integer> tickCallback) {
        this.remainingTime = initialTime;
        this.elapsedTime = 0.0;
        this.isPaused = false;
        this.lastUpdateTime = System.currentTimeMillis();

        tickCallback.accept((int) Math.ceil(remainingTime)); // İlk durumu yazdır

        timerThread = new Thread(() -> {
            while (remainingTime > 0) {
                try {
                    Thread.sleep(100); // 0.1 saniyelik bir bekleme
                    synchronized (this) {
                        if (!isPaused) { // Eğer duraklatılmamışsa
                            long currentTime = System.currentTimeMillis();
                            double deltaTime = (currentTime - lastUpdateTime) / 1000.0;

                            // Zaman güncellemesi
                            remainingTime = Math.max(remainingTime - deltaTime, 0);
                            elapsedTime += deltaTime;
                            lastUpdateTime = currentTime;

                            mechanicsCallback.run();

                            // Her tam saniyede yazdır
                            if (Math.floor(elapsedTime) > Math.floor(elapsedTime - deltaTime)) {
                                tickCallback.accept((int) Math.ceil(remainingTime));
                            }
                        } else {
                            // Duraklatıldığında hiçbir şey hesaplama
                            lastUpdateTime = System.currentTimeMillis(); // Yeni referans zamanı kaydet
                        }
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

    public synchronized void pauseTimer() {
        if (!isPaused) {
            isPaused = true;
            System.out.println("Timer Paused!");
        }
    }

    public synchronized void resumeTimer() {
        if (isPaused) {
            isPaused = false;
            lastUpdateTime = System.currentTimeMillis(); // Duraklama sonrası referans zamanını güncelle
            System.out.println("Timer Resumed!");
        }
    }

    public synchronized void addTime(double seconds) {
        remainingTime += seconds;
        System.out.printf("Remaining Time Increased by: %.1f seconds\n", seconds);
    }

    public synchronized double getElapsedTime() {
        return elapsedTime;
    }
}
