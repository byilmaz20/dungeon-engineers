package src.Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.Mechanics.Timer;

public class CheckMechanicsTest {

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    @Test
    void testElapsedTime() {
        timer.startTimer(100.0, () -> {}, remainingTime -> {});
        timer.addTime(-10.0);
        assertEquals(10.0, timer.getElapsedTime(), "Elapsed time should be 10 seconds.");
    }

    @Test
    void testRemainingTime() {
        timer.startTimer(50.0, () -> {}, remainingTime -> {});
        timer.addTime(-20.0);
        assertEquals(30.0, timer.getRemainingTime(), "Remaining time should be 30 seconds.");
    }
}
