package main.Utilities;

public class GameTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    public GameTimer() {
        this.running = false;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.elapsedTime = System.currentTimeMillis() - startTime;
        this.running = false;
    }

    public long getElapsedTime() {
        return running ? System.currentTimeMillis() - startTime : elapsedTime;
    }
}
