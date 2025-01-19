package src.Mechanics;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Starts the timer with the specified initial time, periodically invoking the mechanics callback
 * and tick callback as the timer counts down.
 * 
 * Requires:
 * - `initialTime > 0`: The initial time must be a positive value greater than zero.
 * - `mechanicsCallback != null`: The mechanics callback must not be null and must be executable.
 * - `tickCallback != null`: The tick callback must not be null and must be executable.
 * 
 * Modifies:
 * - `remainingTime`: Sets this variable to `initialTime` and decrements it periodically as time progresses.
 * - `elapsedTime`: Tracks the total elapsed time since the timer started and updates it on every tick.
 * - `isPaused`: Sets to `false` at the start and can be toggled during the timer's lifecycle.
 * - `lastUpdateTime`: Updates to the current system time at each tick or whenever the timer's state changes (e.g., resume).
 * - `timerThread`: Initializes and starts a new thread to handle the timer's countdown logic.
 * 
 * Effects:
 * - Creates and starts a new thread (`timerThread`) to manage the timer.
 * - Periodically:
 *   - Decrements `remainingTime` and updates `elapsedTime`.
 *   - Invokes `mechanicsCallback` whenever `elapsedTime` progresses by one or more seconds.
 *   - Invokes `tickCallback` with the current `remainingTime` on each tick.
 * - Updates the `lastUpdateTime` to calculate accurate time deltas for time progression.
 * - Calls `notifyTimeChange()` to notify listeners of changes in `remainingTime`.
 * - Stops execution when `remainingTime` reaches 0 or if the thread is interrupted.
 * - Ensures thread safety with synchronized blocks to handle concurrent access to shared variables.
 * - Throws `IllegalArgumentException` if `initialTime <= 0` or if either callback is null.
 */


public class Timer implements Serializable{
    private double remainingTime;
    private double elapsedTime;
    private boolean isPaused;
    private long lastUpdateTime;
    transient private Thread timerThread;
    transient private TimeChangeListener timeChangeListener;

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
