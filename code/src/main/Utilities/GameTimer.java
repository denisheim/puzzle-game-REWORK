package main.Utilities;

/**
 * this class provides functionality to track the elapsed time
 * during a game session. It can be started, stopped, and queried for the
 * elapsed time
 */
public class GameTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    /**
     * constructor for the GameTimer class
     * initializes the timer in a non-running state
     */
    public GameTimer() {
        this.running = false;
    }

    /**
     * starts the timer and records the current system time as the start time
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * stops the timer and calculates and stores the elapsed time
     */
    public void stop() {
        this.elapsedTime = System.currentTimeMillis() - startTime;
        this.running = false;
    }

    /**
     * gets the elapsed time in milliseconds
     * if the timer is running, it calculates the time elapsed since it started
     * if the timer is not running, it returns the stored elapsed time
     */
    public long getElapsedTime() {
        return running ? System.currentTimeMillis() - startTime : elapsedTime;
    }
}
