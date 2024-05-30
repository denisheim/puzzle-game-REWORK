package main.Windows;

import main.DrawPuzzle.Puzzle;
import main.Images.Pieces;
import main.Input.Mouse;
import main.Leaderboard.Leaderboard;
import main.Utilities.GameTimer;
import main.UI.UIManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import java.awt.BorderLayout;

/**
 * This class represents the main puzzle window of the application and provides functionality for running the game
 */
public class MainPuzzle extends JFrame implements Runnable {

    public static final int WIDTH = 550, HEIGHT = 620;
    private final Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private boolean win = false;
    private boolean winT = true;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 30;
    private double targettime = 1000000000 / FPS;
    private double delta = 0;
    private int averageFPS = FPS;

    private int diffi = 3;
    private Puzzle pz = new Puzzle(diffi);
    private JPanel jp;
    public int level = 0;
    private int contWin = 0;

    // Timer and leaderboard variables
    private GameTimer gameTimer = new GameTimer();
    private String username;
    private Leaderboard leaderboard = new Leaderboard();
    private UIManager uiManager = new UIManager();

    /**
     * Constructor
     */
    public MainPuzzle() {
        this.username = JOptionPane.showInputDialog(null, "Enter your username:");
        if (this.username == null || this.username.trim().isEmpty()) {
            this.username = "Unknown";
        }

        this.setSize(700, 650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Puzzle");
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        canvas.addMouseListener(new Mouse(pz));
        jp = uiManager.setupUI(createListeners());
        this.add(jp);
        this.add(canvas);
    }

    public static void main(String[] args) {
        new MainPuzzle().start();
    }

    /**
     * Create listeners for UI buttons
     */
    private ActionListener[] createListeners() {
        return new ActionListener[]{
                (ActionEvent e) -> resetGame(),
                (ActionEvent e) -> solvePuzzle(),
                (ActionEvent e) -> changeLevel(),
                (ActionEvent e) -> changeDifficulty(),
                (ActionEvent e) -> manageImages(),
                (ActionEvent e) -> showLeaderboard()
        };
    }

    void resetGame() {
        win = false;
        winT = true;
        pz.resetImage();
        gameTimer.start();
    }

    void solvePuzzle() {
        pz.solve();
        gameTimer.stop();
    }

    void changeLevel() {
        Object[] obj = new Object[Pieces.getNumImages()];
        for (int i = 0; i < Pieces.getNumImages(); i++)
            obj[i] = i;
        try {
            level = (int) JOptionPane.showInputDialog(null, "Select level", "Select level", JOptionPane.INFORMATION_MESSAGE, null, obj, level);
            pz.resetImage();
            win = false;
            winT = true;
            gameTimer.start();
        } catch (Exception ex) {
        }
    }

    void changeDifficulty() {
        try {
            diffi = (int) JOptionPane.showInputDialog(null, "Select difficulty", "Select difficulty", JOptionPane.INFORMATION_MESSAGE, null, new Object[]{3, 4, 5, 6, 7, 8}, diffi);
            pz.resetImage();
            win = false;
            winT = true;
            gameTimer.start();
        } catch (Exception ex) {
        }
        pz.setDifficulty(diffi);
        pz.resetImage();
    }

    private void manageImages() {
        MainManage ci = new MainManage(pz, this);
        ci.setVisible(true);
        this.setEnabled(false);
    }

    void showLeaderboard() {
        JDialog dialog = new JDialog(this, "Leaderboard", true);
        dialog.setLayout(new BorderLayout());

        JTextArea leaderboardArea = new JTextArea(leaderboard.getLeaderboard());
        leaderboardArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        JButton clearButton = new JButton("Clear Leaderboard");
        clearButton.addActionListener(e -> {
            leaderboard.clearLeaderboard();
            leaderboardArea.setText(leaderboard.getLeaderboard());
        });

        buttonPanel.add(okButton);
        buttonPanel.add(clearButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Updates the puzzle state and checks for a win condition
     */
    private void update() {
        pz.setLevel(level); //sets the level
        long elapsedTime = gameTimer.getElapsedTime(); // Update elapsed time
        if (!win)
            win = pz.isWin(); //checks if the puzzle is in a winning state
        else if (!winT && contWin > 0) {
            JOptionPane.showMessageDialog(null, "Congratulations! You finished the puzzle in " + elapsedTime / 1000 + " seconds");
            leaderboard.addEntry(username, elapsedTime); // Add entry to leaderboard
            contWin = 0;
        }
        /**
         * Handles post-win actions,
         * like updating the puzzle state variables
         */
        if (win && winT) {
            pz.noPiece = -1;
            contWin++;
            winT = false;
            gameTimer.stop(); // Stop the timer when the puzzle is solved
        }
    }

    /**
     * Renders the puzzle game on the canvas,
     * draws the puzzle pieces and updates the display.
     */
    private void draw() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        pz.drawPieces(g);

        // Draw elapsed time centered at the top with larger font
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String timeString = "Time: " + gameTimer.getElapsedTime() / 1000 + "s";
        int stringWidth = g2d.getFontMetrics().stringWidth(timeString);
        int x = (WIDTH - stringWidth) / 2;
        int y = g2d.getFontMetrics().getHeight();
        g2d.drawString(timeString, x, y);

        g.dispose();
        bs.show();
    }

    /**
     * Runs the game loop for continuous updating and rendering
     * and implements the Runnable interface.
     */
    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / targettime; //calculate the time difference since the last iteration
            time += (now - lastTime); //mount the time
            lastTime = now; //update the lastTime to current time

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                averageFPS = frames;
                frames = 0;
                time = 0;
            }
        }
        this.stop();
    }

    /**
     * Starts the game loop by creating a thread and calls the run method
     */
    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
        gameTimer.start(); // Initialize the start time
    }

    /**
     * Stops the game loop by setting "running" to false,
     * however it waits for the thread to finish execution
     */
    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // For testing purposes: Adding getter methods for testing
    boolean isWin() {
        return win;
    }

    boolean isWinT() {
        return winT;
    }

    boolean isTimerRunning() {
        return gameTimer.getElapsedTime() > 0;
    }
}
