package src.Mechanics;

import java.io.Serializable;
import java.util.function.Consumer;

public class Timer implements Serializable{
    private double remainingTime;
    private double elapsedTime;
    private boolean isPaused;
    private long lastUpdateTime;
    private Thread timerThread;
    private TimeChangeListener timeChangeListener;

    public interface TimeChangeListener {
        void onTimeChanged(double remainingTime);
    }
    public synchronized void setTimeChangeListener(TimeChangeListener listener) {
        this.timeChangeListener = listener;
    }

    public Timer() {
        this.isPaused = false;
    }
    
    private void notifyTimeChange() {
        if (timeChangeListener != null) {
            timeChangeListener.onTimeChanged(remainingTime);
        }
        //System.out.println("Time changed at: " + System.currentTimeMillis());

    }


    public void startTimer(double initialTime, Runnable mechanicsCallback, Consumer<Integer> tickCallback) {
        this.remainingTime = initialTime;
        this.elapsedTime = 0.0;
        this.isPaused = false;
        this.lastUpdateTime = System.currentTimeMillis();
        tickCallback.accept((int) Math.ceil(remainingTime));
        //todo eger cagırılan fonksiyonun suresi uzadıgı icin visual guncelleme sıkıntısı cıkarsa kontrol et
        timerThread = new Thread(() -> {
            while (remainingTime > 0) {
                long loopStartTime = System.currentTimeMillis();
                try {
                    synchronized (this) {
                        if (!isPaused) {
                            long currentTime = System.currentTimeMillis();
                            double deltaTime = (currentTime - lastUpdateTime) / 1000.0;

                            remainingTime = Math.max(remainingTime - deltaTime, 0);
                            elapsedTime += deltaTime;
                            lastUpdateTime = currentTime;

                            if (Math.floor(elapsedTime) > Math.floor(elapsedTime - deltaTime)) {
                                mechanicsCallback.run();
                                tickCallback.accept((int) Math.ceil(remainingTime));
                            }
                        } else {
                            lastUpdateTime = System.currentTimeMillis();
                        }
                    }
                    long sleepDuration = Math.max(0, 1000 - (System.currentTimeMillis() - loopStartTime));
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                notifyTimeChange();
            }
            //System.out.println("Timer Finished!");
            //TODO burada aksiyon alınacak UI baglantısı icin
        });
        timerThread.start();
    }

    public synchronized boolean pauseTimer() {
        if (!isPaused) {
            isPaused = true;
            //System.out.printf("Timer Paused! Elapsed Time: %.3f\n", elapsedTime);
        }
        return isPaused;
    }

    public synchronized boolean resumeTimer() {
        if (isPaused) {
            isPaused = false;
            lastUpdateTime = System.currentTimeMillis();
            //System.out.printf("Timer Resumed! Elapsed Time: %.3f\n", elapsedTime);
        }
        return isPaused;
    }

    public synchronized void addTime(double seconds) {
        remainingTime += seconds;
        lastUpdateTime = System.currentTimeMillis();
        System.out.printf("Remaining Time Increased by: %.0f seconds\n", seconds);
    }

    public synchronized double getRemainingTime() {
        return remainingTime;
    }

    public synchronized double getElapsedTime() {
        return elapsedTime;
    }

    public synchronized void stopTimer() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public synchronized double disposeTimer() {
        double remainingTime = this.getRemainingTime();
        stopTimer();
        return remainingTime;
    }

    public long getlastUpdateTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastUpdateTime'");
    }

}
