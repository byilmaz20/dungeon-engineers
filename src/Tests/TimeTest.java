package src.Tests;

/**
 * Starts the timer with the specified initial time, periodically invoking the mechanics callback
 * and tick callback as the timer counts down.
 * 
 * Requires:
 * - `initialTime > 0`: The initial time must be a positive value.
 * - `mechanicsCallback != null`: The mechanics callback must not be null.
 * - `tickCallback != null`: The tick callback must not be null.
 * 
 * Modifies:
 * - `remainingTime`: Initializes it to `initialTime` and decrements it as time progresses.
 * - `elapsedTime`: Tracks the total elapsed time since the timer started.
 * - `isPaused`: Controls whether the timer is paused or running.
 * - `lastUpdateTime`: Updates to track the last system time when the timer was running.
 * - `timerThread`: Initializes and starts a new thread to manage the timer logic.
 * 
 * Effects:
 * - Starts a new thread that:
 *   - Periodically decrements `remainingTime` and updates `elapsedTime`.
 *   - Invokes the `mechanicsCallback` whenever the elapsed time progresses by one or more seconds.
 *   - Invokes the `tickCallback` with the current `remainingTime` at each tick.
 * - Notifies listeners of time changes using `notifyTimeChange()`.
 * - Ends execution when `remainingTime` reaches 0 or if interrupted.
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Mechanics.Timer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TimeTest {
    private Timer timer;

    @BeforeEach
    public void setup() {
        timer = new Timer();
    }

/**
 * Test: Verifies that the mechanics callback is invoked periodically and the remaining time decreases as expected.
**/
    @Test
    public void testMechanicsCallbackAndRemainingTime() throws InterruptedException {
        double initialTime = 5.0;
        AtomicInteger mechanicsCallCount = new AtomicInteger(0);
    
        timer.startTimer(initialTime,
                mechanicsCallCount::incrementAndGet, // Mechanics callback
                remaining -> {} // Empty tick callback
        );
    
        // Wait for 2 seconds
        Thread.sleep(2000);
        assertTrue(mechanicsCallCount.get() > 0, "Mechanics callback should be invoked while the timer is running."); //Expected Result:The mechanics callback (`mechanicsCallback`) is invoked at least once during the 2-second wait.
        assertTrue(timer.getRemainingTime() < 5.0, "Remaining time should decrease after 2 seconds."); // Expected Result: The The `remainingTime` decreases from its initial value (5.0 seconds) after 2 seconds of execution.
    }


/**
 * Test: Verifies that the timer correctly updates its remaining time when `addTime` is called during execution.
 **/
    @Test
    public void testAddTimeDuringExecution() throws InterruptedException {
        double initialTime = 5.0;
        AtomicInteger mechanicsCallCount = new AtomicInteger(0);
    
        timer.startTimer(initialTime,
                mechanicsCallCount::incrementAndGet, 
                remaining -> {} 
        );
    
        // Wait for 2 seconds
        Thread.sleep(2000);
        //it is important due to time enchantment.
        timer.addTime(5.0);
        // Wait for another 2 seconds
        Thread.sleep(2000);
        // Assert that remaining time is greater than the original initial time - elapsed time
        assertTrue(timer.getRemainingTime() > 5.0, "Remaining time should reflect the added time."); //Expected Result:After 2 seconds, the `remainingTime` decreases from the initial value (5.0 seconds).
        assertTrue(mechanicsCallCount.get() > 0, "Mechanics callback should still be invoked after adding time."); //Expected Result: Adding 5.0 seconds to the timer increases the `remainingTime` appropriately.
    }                                                                                                              //The `remainingTime` should reflect the additional time and still decrease after resumption.` appropriately.
                                                                                                                    //The mechanics callback (`mechanicsCallback`) should continue to be invoked after adding time.
    
/**
 * Test: Verifies that the timer correctly pauses and resumes, ensuring no time passes while paused
 * and the timer resumes decrementing when resumed.
 * */
@Test
public void testPauseAndResumeTimer() throws InterruptedException {
    double initialTime = 5.0;
    // Start the timer
    timer.startTimer(initialTime,
            () -> {}, 
            remaining -> {} 
    );
    // Wait for 1 second and then pause
    Thread.sleep(1000);
    timer.pauseTimer();
    double remainingTimeAfterPause = timer.getRemainingTime(); // Expected Result: After 1 second, the timer is paused, and the `remainingTime` is recorded.
    Thread.sleep(2000);
    // Assert that the remaining time does not change while paused
    assertEquals(remainingTimeAfterPause, timer.getRemainingTime(), 0.1, "Remaining time should not change while paused."); // Expected Result: While paused, the `remainingTime` should remain constant even after waiting for 2 seconds.
    // Resume the timer
    timer.resumeTimer();
    // Wait for another 1 second
    Thread.sleep(1000);
    // Assert that the remaining time has decremented after resuming
    assertTrue(timer.getRemainingTime() < remainingTimeAfterPause, "Remaining time should decrease after resuming."); // Expected Result: After resuming the timer, the `remainingTime` should continue decrementing as normal.
}


    

}
