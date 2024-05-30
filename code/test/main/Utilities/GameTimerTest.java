package main.Utilities;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTimerTest {
    private GameTimer timer;

    @Before
    public void setUp() {
        timer = new GameTimer();
    }

    @Test
    public void testStart() throws InterruptedException {
        timer.start();
        Thread.sleep(100);
        assertTrue(timer.getElapsedTime() >= 100);
    }

    @Test
    public void testStop() throws InterruptedException {
        timer.start();
        Thread.sleep(100);
        timer.stop();
        long elapsed = timer.getElapsedTime();
        Thread.sleep(50);
        assertEquals(elapsed, timer.getElapsedTime());
    }

    @Test
    public void testGetElapsedTime() throws InterruptedException {
        timer.start();
        Thread.sleep(100);
        assertTrue(timer.getElapsedTime() >= 100);
        timer.stop();
        long elapsed = timer.getElapsedTime();
        assertEquals(elapsed, timer.getElapsedTime());
    }
}
